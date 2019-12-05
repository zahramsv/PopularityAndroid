package com.example.popularity.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
import com.example.popularity.Utils.BaseFragment;
import com.example.popularity.R;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.util.Arrays;

public class LoginFragment extends BaseFragment {



    Button instagram_btn;

    public LoginFragment() {
        // Required empty public constructor
    }


    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        return fragment;
    }

    CallbackManager callbackManager = CallbackManager.Factory.create();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        clickEvents(view);

        String EMAIL = "email";

        LoginButton loginButton = view.findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList(EMAIL));
        loginButton.setFragment(this);

        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.i("app_tag","onSuccess: "+loginResult.getAccessToken());

            }

            @Override
            public void onCancel() {
                Log.i("app_tag","onCancel");
            }

            @Override
            public void onError(FacebookException exception) {
                Log.i("app_tag","onError: "+exception.getMessage());
            }
        });


        return view;

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }




    //Fragment Button Click
    public void clickEvents(View v) {

        switch (v.getId())
        {
            case R.id.instagram_btn:
                break;
        }

    }



    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        return super.onCreateAnimation(transit, enter, nextAnim);
    }


}
