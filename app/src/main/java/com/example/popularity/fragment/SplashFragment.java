package com.example.popularity.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import android.service.autofill.UserData;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.popularity.activity.MainActivity;
import com.example.popularity.model.BaseResponse;
import com.example.popularity.model.User;
import com.example.popularity.myInterface.ApiServices;
import com.example.popularity.myInterface.UserTransaction;
import com.example.popularity.utils.BaseFragment;
import com.example.popularity.R;
import com.example.popularity.utils.RetrofitInstance;
import com.example.popularity.utils.SavePref;
import com.example.popularity.utils.ToolbarState;
import com.example.popularity.utils.URLS;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class SplashFragment extends BaseFragment {

    private ToolbarState toolbarState;

    private UserTransaction userTransaction;
    public SplashFragment(UserTransaction userTransaction) {
        this.userTransaction = userTransaction;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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



        }else{
            userTransaction.setMainUser(null);
        }
        Log.i("app_tag", token + "");


    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        if (toolbarState != null) {
            toolbarState.toolbarState(false);
        }


        return inflater.inflate(R.layout.fragment_splash, container, false);
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof MenuDrawerFragment.OnSlidingMenuFragmentListener) {
            toolbarState = (ToolbarState) context;
           // getUserInfo = (MainActivity.getUserDataSplash) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnAccountingMainFragmentInteractionListener");
        }
    }

}
