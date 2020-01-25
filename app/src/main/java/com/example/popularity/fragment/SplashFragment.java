package com.example.popularity.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.popularity.model.BaseResponse;
import com.example.popularity.model.User;
import com.example.popularity.myInterface.ApiServices;
import com.example.popularity.R;
import com.example.popularity.utils.RetrofitInstance;
import com.example.popularity.utils.ToolbarKind;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SplashFragment extends BaseFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Handler handler = new Handler();
        handler.postDelayed(() -> {
            // Do something after 2s = 2000ms
            getUserInfoFromServer();

        }, 2000);


    }

    private void getUserInfoFromServer() {
        SharedPreferences prefs = getActivity().getSharedPreferences("user_data", Context.MODE_PRIVATE);
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
                    baseListener.setMainUser(response.body().getData());
                    baseListener.openFragment(new HomeFragment(), false, null);

                }


                @Override
                public void onFailure(Call<BaseResponse<User>> call, Throwable t) {

                }
            });


        } else {
            baseListener.openFragment(new LoginFragment(), false, null);
        }
        Log.i("app_tag", token + "");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        baseListener.changeToolbar(ToolbarKind.EMPTY, "");
        return inflater.inflate(R.layout.fragment_splash, container, false);
    }




}
