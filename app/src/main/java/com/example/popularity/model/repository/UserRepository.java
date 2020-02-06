package com.example.popularity.model.repository;

import android.util.Log;

import com.example.popularity.R;
import com.example.popularity.model.BaseResponse;
import com.example.popularity.model.Login;
import com.example.popularity.model.User;
import com.example.popularity.myInterface.ApiServices;
import com.example.popularity.utils.MyApp;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRepository {

    private User currentUser;

    private ApiServices api;

    public UserRepository(ApiServices api){
        this.api = api;
    }

    public void loginToServer(Login loginBody, UserRepoListener listener) {

        api.loginToSocial(loginBody).enqueue(new Callback<BaseResponse<User>>() {
            @Override
            public void onResponse(Call<BaseResponse<User>> call, Response<BaseResponse<User>> response) {

                Log.i("app_tag", response.toString());
                if ((response.isSuccessful())) {
                    BaseResponse obr = response.body();
                    User user = (User) obr.getData();
                    listener.onLoginDone(user);
                    user.setSocial_primary(loginBody.getSocial_primary());
                    setCurrentUser(user);

                } else {
                    listener.onLoginFailure(MyApp.getInstance().getApplicationContext().getString(R.string.error_api_call));
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<User>> call, Throwable t) {
                listener.onLoginFailure(MyApp.getInstance().getApplicationContext().getString(R.string.error_api_call));
                Log.i("app_tag", Objects.requireNonNull(t.getMessage()));

            }
        });
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public interface UserRepoListener {
        void onLoginDone(User user);
        void onLoginFailure(String message);
    }

}
