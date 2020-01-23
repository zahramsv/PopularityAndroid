package com.example.popularity.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;

import com.example.popularity.myInterface.GetLoginDataService;
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


    @Override
    public void onHiddenChanged(boolean hidden) {
       if(!hidden)
           baseListener.changeToolbar(ToolbarKind.HOME,getString(R.string.login_toolbar_txt));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        baseListener.changeToolbar(ToolbarKind.HOME,getString(R.string.login_toolbar_txt));
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        Button loginButton = view.findViewById(R.id.login_api_button);
        loginButton.setOnClickListener(view1 -> {
            baseListener.showLoadingBar(true);
            loginToServer();
        });

        //getUserFriends(); khodet bezan yekam bebinam :D dasht khabam mibord jedan
        return view;

    }

    private void loginToServer() {

        RetrofitInstance retrofitInstance = new RetrofitInstance();
        Retrofit retrofit = retrofitInstance.getRetrofitInstance();
        SocialLoginLogic socialLoginLogic = new SocialLoginLogic();
        socialLoginLogic.GetFirstUserLoginData();

        GetLoginDataService getLoginDataService = retrofit.create(GetLoginDataService.class);


        getLoginDataService.getLoginData(socialLoginLogic.GetFirstUserLoginData()).enqueue(new Callback<SocialRootModel>() {
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


    /*private SmsHandler smsHandler;
    private void loginByMobile(View view){
        smsHandler = new SmsHandler(this);
        Button loginByMobile = view.findViewById(R.id.loginByMobile);
        loginByMobile.setOnClickListener(v->{
            smsHandler.requestSendSms(USER_PHONE_NUMBER);
        });
    }

    @Override
    public void onSmsSendingResult(Boolean isSuccess, String message) {
        baseListener.showMessage(message);
    }*/

}
