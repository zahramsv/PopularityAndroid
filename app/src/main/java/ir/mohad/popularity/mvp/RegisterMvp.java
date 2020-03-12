package ir.mohad.popularity.mvp;

import android.content.Context;

import ir.mohad.popularity.model.Login;

public interface RegisterMvp {

    interface View {
        Context getViewContext();
    }

    interface Presenter {

        Login getLoginInfo(String fullname, String username);

        void setUserSocialPrimary(String userSocialPrimary);

        void loginToServer(Login user);

        boolean userRegisterInformationValidation(String fullName, String userName);

    }
}
