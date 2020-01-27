package com.example.popularity.fragment;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.popularity.R;
import com.example.popularity.model.BaseResponse;
import com.example.popularity.model.Login;
import com.example.popularity.model.SocialRootModel;
import com.example.popularity.model.User;
import com.example.popularity.model.UserPopularity;
import com.example.popularity.model.VerifySmsResponseData;
import com.example.popularity.myInterface.ApiServices;
import com.example.popularity.utils.RetrofitInstance;
import com.example.popularity.utils.SavePref;
import com.example.popularity.utils.ToolbarKind;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class MobileLoginFragment extends BaseFragment {

    private AppCompatEditText edtMobile, edtVerifyCode;
    private String userMobile;
    private SocialRootModel socialRootModel;
    private RetrofitInstance retrofitInstance;
    private Retrofit retrofit;
    private ApiServices apiServices;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MobileLoginFragment() {

    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        if (!hidden) {
            baseListener.changeToolbar(ToolbarKind.HOME, getString(R.string.login_with_mobile));
        }
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    public static MobileLoginFragment newInstance() {
        MobileLoginFragment fragment = new MobileLoginFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_mobile_login, container, false);
        init(view);
        view.findViewById(R.id.btnReceiveCode).setOnClickListener(view1 -> {

            if (baseListener.checkNetwork()) {
                apiServices.sendSms(edtMobile.getText().toString()).enqueue(new Callback<SocialRootModel>() {
                    @Override
                    public void onResponse(Call<SocialRootModel> call, Response<SocialRootModel> response) {

                        socialRootModel = response.body();

                    }

                    @Override
                    public void onFailure(Call<SocialRootModel> call, Throwable t) {

                    }
                });
            } else {
                baseListener.showSnackBar("Please check your connection");
            }


        });

        view.findViewById(R.id.btnVerifyCode).setOnClickListener(view1 -> {

            if (baseListener.checkNetwork()) {
                if (socialRootModel!=null)
                {
                    if (socialRootModel.getCode() == 200)
                    {

                        String d1=edtMobile.getText().toString();
                        String d2=edtVerifyCode.getText().toString();
                        apiServices.verifySms(d1,d2).enqueue(new Callback<BaseResponse<VerifySmsResponseData>>() {
                            @Override
                            public void onResponse(Call<BaseResponse<VerifySmsResponseData>> call, Response<BaseResponse<VerifySmsResponseData>> response) {

                                //JsonObject jsonObject=response.body().getData();
                                if (response.body().getData().isRegistered)
                                {
                                    loginToServer();
                                }
                                else {
                                    baseListener.showSnackBar("Some Problem");
                                }
                            }

                            @Override
                            public void onFailure(Call<BaseResponse<VerifySmsResponseData>> call, Throwable t) {
                                baseListener.showMessage(t.getMessage());
                            }
                        });
                      /*  apiServices.verifySms(edtMobile.getText().toString(), edtVerifyCode.getText().toString()).enqueue(new Callback<SocialRootModel>() {
                            @Override
                            public void onResponse(Call<SocialRootModel> call, Response<SocialRootModel> response) {

                                SocialRootModel socialRootModel = response.body();
                                if (socialRootModel.getCode() == 200) {
                                    loginToServer();
                                } else {
                                    baseListener.showSnackBar("it is not valid");
                                    baseListener.showMessage("it is not valid");
                                }
                            }

                            @Override
                            public void onFailure(Call<SocialRootModel> call, Throwable t) {
                                baseListener.showMessage(t.getMessage());
                            }
                        });*/
                    } else {
                    baseListener.showSnackBar("Code Do Not Receive");
                    baseListener.showMessage("Code Do Not Receive");
                }
                }
                    else {
                        baseListener.showSnackBar("Enter Your Number");
                }

            } else {
                baseListener.showSnackBar("Please check your connection");
            }
        });
        return view;
    }

    private void loginToServer() {

        RetrofitInstance retrofitInstance = new RetrofitInstance();
        Retrofit retrofit = retrofitInstance.getRetrofitInstance();

        ApiServices apiServices = retrofit.create(ApiServices.class);

        apiServices.getLoginData(getLoginInfo()).enqueue(new Callback<SocialRootModel>() {
            @Override
            public void onResponse(Call<SocialRootModel> call, Response<SocialRootModel> response) {
                baseListener.showLoadingBar(false);

                Log.i("app_tag", response.toString());
                if ((response.isSuccessful())) {
                    SocialRootModel obr = response.body();
                    User data = obr.getData();
                    UserPopularity userPopularity = obr.getData().getRates_summary_sum();
                    SavePref savePref = new SavePref();
                    data.setSocial_primary(userMobile + "");
                    savePref.SaveUser(getContext(), data, userPopularity);

                    baseListener.setMainUser(data);
                    baseListener.openFragment(new HomeFragment(), true, null);
                    Log.i("app_tag", "info: " + obr.getCode());


                } else {
                    baseListener.showMessage(getString(R.string.error_api_call));
                    Log.i("app_tag", "error");
                }
            }

            @Override
            public void onFailure(Call<SocialRootModel> call, Throwable t) {
                baseListener.showMessage(getString(R.string.error_api_call));
                baseListener.showLoadingBar(false);

                Log.i("app_tag", t.getMessage().toString());
            }
        });
    }


    private Login getLoginInfo() {
        Login user = new Login();
        user.setAvatar_url("myavatar.jpg");
        user.setFull_name("zahra hadi");
        user.setSocial_primary(userMobile);
        user.setUsername("z.hadi");
        user.setSocial_type(0);
        return user;
    }


    public void init(View view) {
        baseListener.changeToolbar(ToolbarKind.HOME, getString(R.string.login_with_mobile));
        retrofitInstance = new RetrofitInstance();
        retrofit = retrofitInstance.getRetrofitInstance();
        apiServices = retrofit.create(ApiServices.class);
        edtVerifyCode = view.findViewById(R.id.edtVerifyCode);
        edtMobile = view.findViewById(R.id.edtMobile);
    }
}
