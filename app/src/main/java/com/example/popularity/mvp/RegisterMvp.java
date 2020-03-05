package com.example.popularity.mvp;

import android.content.Context;

import com.example.popularity.model.Login;

public interface RegisterMvp {

    interface View {
        Context getViewContext();
    }

    interface Presenter {

        Login getLoginInfo(String fullname, String username);

        void setUserSocialPrimary(String userSocialPrimary);

        void loginToServer(Login user);

    }
}
