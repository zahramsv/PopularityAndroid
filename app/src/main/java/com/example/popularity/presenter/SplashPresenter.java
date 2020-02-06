package com.example.popularity.presenter;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.util.Log;

import com.example.popularity.fragment.HomeFragment;
import com.example.popularity.fragment.LoginFragment;
import com.example.popularity.model.BaseResponse;
import com.example.popularity.model.User;
import com.example.popularity.model.repository.LoginHandler;
import com.example.popularity.model.repository.UserRepository;
import com.example.popularity.mvp.SplashMvp;
import com.example.popularity.myInterface.ApiServices;
import com.example.popularity.myInterface.MainActivityTransaction;
import com.example.popularity.utils.MyApp;
import com.example.popularity.utils.RetrofitInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SplashPresenter implements SplashMvp.Presenter {

    private SplashMvp.View view;
    private MainActivityTransaction.Components baseComponent;
    private UserRepository userRepository;
    private LoginHandler loginHandler;

    public SplashPresenter(SplashMvp.View view, MainActivityTransaction.Components baseComponent) {
        this.view = view;
        this.baseComponent = baseComponent;
        this.loginHandler = MyApp.getInstance().getBaseComponent().provideLoginHandler();
        this.userRepository = MyApp.getInstance().getBaseComponent().provideUserRepository();
    }

    @Override
    public void getUserInfoAfterWait() {
        final Handler handler = new Handler();
        handler.postDelayed(() -> {
            getUserInfoFromServer();
        }, 2000);
    }


    private void getUserInfoFromServer() {
        SharedPreferences prefs = view.getViewContext().getSharedPreferences("user_data", Context.MODE_PRIVATE);
        String token = prefs.getString("token", null);
        String social_primary = prefs.getString("social_primary", null);

        if (token != null && social_primary != null) {
            RetrofitInstance retrofitInstance = new RetrofitInstance();
            Retrofit retrofit = retrofitInstance.getRetrofitInstance();

            ApiServices apiServices = retrofit.create(ApiServices.class);
            apiServices.getUserInfo(token, social_primary).enqueue(new Callback<BaseResponse<User>>() {
                @Override
                public void onResponse(Call<BaseResponse<User>> call, Response<BaseResponse<User>> response) {

                    assert response.body() != null;
                    BaseResponse<User> result = response.body();
                    if (result.getCode() == 200) {
                        User user = result.getData();
                        userRepository.setCurrentUser(user);
                        loginHandler.saveLoginInfo(user, user.getRates_summary_sum());
                        baseComponent.openFragment(new HomeFragment(), false, null);
                    } else {
                        baseComponent.openFragment(new LoginFragment(), false, null);
                    }
                }


                @Override
                public void onFailure(Call<BaseResponse<User>> call, Throwable t) {

                }
            });


        } else {
            baseComponent.openFragment(new LoginFragment(), false, null);
        }
        Log.i("app_tag", token + "");
    }

}
