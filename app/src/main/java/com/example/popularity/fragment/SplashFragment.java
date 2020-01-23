package com.example.popularity.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import android.os.Handler;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.popularity.model.BaseResponse;
import com.example.popularity.model.User;
import com.example.popularity.myInterface.ApiServices;
import com.example.popularity.myInterface.UserTransaction;
import com.example.popularity.R;
import com.example.popularity.utils.RetrofitInstance;
import com.example.popularity.utils.ToolbarKind;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SplashFragment extends BaseFragment {


    private UserTransaction userTransaction;

    public SplashFragment(UserTransaction userTransaction) {
        this.userTransaction = userTransaction;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getUserPhoneNumber();
        //isSimAvailable(getContext());
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
            apiServices.GetUserInfo(token, social_primary).enqueue(new Callback<BaseResponse<User>>() {
                @Override
                public void onResponse(Call<BaseResponse<User>> call, Response<BaseResponse<User>> response) {

                    BaseResponse<User> object = response.body();
                    assert response.body() != null;
                    userTransaction.setMainUser(response.body().getData());
                }


                @Override
                public void onFailure(Call<BaseResponse<User>> call, Throwable t) {

                }
            });


        } else {
            userTransaction.setMainUser(null);
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
