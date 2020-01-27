package com.example.popularity.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.AppCompatEditText;

import com.example.popularity.R;
import com.example.popularity.model.BaseResponse;
import com.example.popularity.model.Login;
import com.example.popularity.model.User;
import com.example.popularity.model.UserPopularity;
import com.example.popularity.model.VerifySmsResponseData;
import com.example.popularity.myInterface.ApiServices;
import com.example.popularity.repository.UserRepository;
import com.example.popularity.utils.RetrofitInstance;
import com.example.popularity.utils.SavePref;
import com.example.popularity.utils.ToolbarKind;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MobileLoginFragment extends BaseFragment implements
        UserRepository.UserRepoListener
{

    private AppCompatEditText edtMobile, edtVerifyCode;
    private String userMobile;
    private BaseResponse sendSmsResult;
    private RetrofitInstance retrofitInstance;
    private Retrofit retrofit;
    private ApiServices apiServices;

    @Override
    public void onHiddenChanged(boolean hidden) {
        if (!hidden) {
            baseListener.changeToolbar(ToolbarKind.HOME, getString(R.string.login_with_mobile));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_mobile_login, container, false);
        init(view);
        view.findViewById(R.id.btnReceiveCode).setOnClickListener(view1 -> {
            sendSms();
        });

        view.findViewById(R.id.btnVerifyCode).setOnClickListener(view1 -> {
            verifyCode();
        });
        return view;
    }

    private void verifyCode() {

        if (baseListener.checkNetwork()) {
            if (sendSmsResult != null) {
                if (sendSmsResult.getCode() == 200) {

                    userMobile = edtMobile.getText().toString();
                    String verifyCode = edtVerifyCode.getText().toString();

                    apiServices.verifySms(userMobile, verifyCode).enqueue(new Callback<BaseResponse<VerifySmsResponseData>>() {
                        @Override
                        public void onResponse(Call<BaseResponse<VerifySmsResponseData>> call, Response<BaseResponse<VerifySmsResponseData>> response) {

                            if (response.body().getData().isRegistered) {
                                loginToServer();
                            } else {
                                baseListener.showSnackBar("Some Problem");
                            }
                        }

                        @Override
                        public void onFailure(Call<BaseResponse<VerifySmsResponseData>> call, Throwable t) {
                            baseListener.showMessage(t.getMessage());
                        }
                    });
                } else {
                    baseListener.showSnackBar("Code Do Not Receive");
                }
            } else {
                baseListener.showSnackBar("Enter Your Number");
            }

        } else {
            baseListener.showSnackBar("Please check your connection");
        }
    }

    private void sendSms() {
        if (baseListener.checkNetwork()) {
            apiServices.sendSms(edtMobile.getText().toString()).enqueue(new Callback<BaseResponse>() {
                @Override
                public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {

                    sendSmsResult = response.body();

                }

                @Override
                public void onFailure(Call<BaseResponse> call, Throwable t) {

                }
            });
        } else {
            baseListener.showSnackBar("Please check your connection");
        }
    }

    private void  loginToServer() {
        baseListener.showLoadingBar(true);

        UserRepository userRepository = new UserRepository();
        userRepository.loginToServer(getLoginInfo(), this);
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

    private void init(View view) {
        baseListener.changeToolbar(ToolbarKind.HOME, getString(R.string.login_with_mobile));
        retrofitInstance = new RetrofitInstance();
        retrofit = retrofitInstance.getRetrofitInstance();
        apiServices = retrofit.create(ApiServices.class);
        edtVerifyCode = view.findViewById(R.id.edtVerifyCode);
        edtMobile = view.findViewById(R.id.edtMobile);
    }

    @Override
    public void onDone(User user) {
        baseListener.showLoadingBar(false);

        UserPopularity userPopularity = user.getRates_summary_sum();
        SavePref savePref = new SavePref();
        user.setSocial_primary(userMobile + "");
        savePref.SaveUser(getContext(), user, userPopularity);
        baseListener.setMainUser(user);
        baseListener.openFragment(new HomeFragment(), true, null);
    }

    @Override
    public void onFailure(String message) {
        baseListener.showLoadingBar(false);
        baseListener.showMessage(message);
    }
}
