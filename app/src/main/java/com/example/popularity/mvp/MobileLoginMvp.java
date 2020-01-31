package com.example.popularity.mvp;
import android.content.Context;
import com.example.popularity.model.Login;
import com.example.popularity.repository.UserRepository;
import com.example.popularity.utils.ShowMessageType;

public interface MobileLoginMvp {

    interface View{
        void showMessage(ShowMessageType messageType, String message);
        Context getViewContext();
        void showLoadingBar(boolean isShow);
        UserRepository.UserRepoListener setUserRepo();
    }

    interface Presenter
    {
        void verifyCode(String verifyCode);
        void sendSMS(String mobile);
        void loginToServer();
        Login getLoginInfo();
    }
}
