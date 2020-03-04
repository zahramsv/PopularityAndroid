package com.example.popularity.mvp;

import android.content.Context;

import com.example.popularity.model.Login;
import com.example.popularity.model.User;

public interface MobileLoginMvp {

    interface View {
        Context getViewContext();
    }

    interface Presenter {

        void verifyCode(String verifyCode);

        void sendSMS(String mobile);

        void loginToServer(Login user);

        Login getLoginInfo();

        boolean phoneNumberValidation(String phoneNumber);

        boolean verifyCodeValidation(String code);


    }
}
