package com.example.popularity.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;

import com.example.popularity.myInterface.ApiServices;
import com.example.popularity.logic.SocialLoginLogic;
import com.example.popularity.model.User;
import com.example.popularity.model.SocialRootModel;
import com.example.popularity.model.UserPopularity;
import com.example.popularity.R;
import com.example.popularity.utils.RetrofitInstance;
import com.example.popularity.utils.SavePref;
import com.example.popularity.utils.ToolbarKind;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginFragment extends BaseFragment {


    private Button loginWithPhoneNumber, loginWithMockData;
    private View view;

    @Override
    public void onHiddenChanged(boolean hidden) {
        if (!hidden)
            baseListener.changeToolbar(ToolbarKind.HOME, getString(R.string.login_toolbar_txt));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        baseListener.changeToolbar(ToolbarKind.HOME, getString(R.string.login_toolbar_txt));
        view = inflater.inflate(R.layout.fragment_login, container, false);
        init(view);

        loginWithPhoneNumber.setOnClickListener(view -> {
            baseListener.openFragment(MobileLoginFragment.newInstance(),true,null);
        });
        loginWithMockData.setOnClickListener(view -> {
            baseListener.showLoadingBar(true);
            loginToServer();
        });


        return view;

    }


    public void init(View view) {
        loginWithMockData = view.findViewById(R.id.login_api_button);
        loginWithPhoneNumber = view.findViewById(R.id.login_with_phone_number);
    }


    private void loginToServer() {

        RetrofitInstance retrofitInstance = new RetrofitInstance();
        Retrofit retrofit = retrofitInstance.getRetrofitInstance();
        SocialLoginLogic socialLoginLogic = new SocialLoginLogic();
        socialLoginLogic.GetFirstUserLoginData();

        ApiServices apiServices = retrofit.create(ApiServices.class);


        apiServices.getLoginData(socialLoginLogic.GetFirstUserLoginData()).enqueue(new Callback<SocialRootModel>() {
            @Override
            public void onResponse(Call<SocialRootModel> call, Response<SocialRootModel> response) {
                baseListener.showLoadingBar(false);

                Log.i("app_tag", response.toString());
                if ((response.isSuccessful())) {
                    SocialRootModel obr = response.body();


                    User data = obr.getData();
                    UserPopularity userPopularity = obr.getData().getRates_summary_sum();
                    SavePref savePref = new SavePref();
                    data.setSocial_primary((socialLoginLogic.GetFirstUserLoginData().getSocial_primary()) + "");
                    savePref.SaveUser(getContext(), data, userPopularity);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("User", data);
                    baseListener.openFragment(new HomeFragment(), true, bundle);
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

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        return super.onCreateAnimation(transit, enter, nextAnim);
    }

}
