package com.example.popularity.repository;

import android.util.Log;

import com.example.popularity.R;
import com.example.popularity.model.BaseResponse;
import com.example.popularity.model.Login;
import com.example.popularity.model.User;
import com.example.popularity.model.UserPopularity;
import com.example.popularity.myInterface.ApiServices;
import com.example.popularity.utils.MyApp;
import com.example.popularity.utils.RetrofitInstance;
import com.example.popularity.utils.SavePref;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UserRepository {

    public void loginToServer(Login loginBody, UserRepoListener listener) {

        RetrofitInstance retrofitInstance = new RetrofitInstance();
        Retrofit retrofit = retrofitInstance.getRetrofitInstance();

        ApiServices apiServices = retrofit.create(ApiServices.class);

        apiServices.getLoginData(loginBody).enqueue(new Callback<BaseResponse<User>>() {
            @Override
            public void onResponse(Call<BaseResponse<User>> call, Response<BaseResponse<User>> response) {


                Log.i("app_tag", response.toString());

                if ((response.isSuccessful())) {
                    BaseResponse obr = response.body();
                    User user = (User) obr.getData();
                    listener.onDone(user);

                } else {
                    listener.onFailure(MyApp.getInstance().getApplicationContext().getString(R.string.error_api_call));
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<User>> call, Throwable t) {
                listener.onFailure(MyApp.getInstance().getApplicationContext().getString(R.string.error_api_call));
                Log.i("app_tag", Objects.requireNonNull(t.getMessage()));

            }
        });
    }


    public interface UserRepoListener {
        void onDone(User user);
        void onFailure(String message);
    }

}
