package com.example.popularity.mvp;

import android.content.Context;
import android.os.Bundle;

import com.example.popularity.fragment.BaseFragment;
import com.example.popularity.model.Login;
import com.example.popularity.model.User;
import com.example.popularity.utils.ShowMessageType;

public interface MobileLoginMvp {

    interface View {
        Context getViewContext();
    }

    interface Presenter {
        void verifyCode(String verifyCode);

        void sendSMS(String mobile);

        void loginToServer();

        Login getLoginInfo();
    }
}
