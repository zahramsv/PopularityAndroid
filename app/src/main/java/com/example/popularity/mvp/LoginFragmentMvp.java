package com.example.popularity.mvp;

import android.content.Context;
import android.os.Bundle;

import com.example.popularity.fragment.BaseFragment;
import com.example.popularity.model.User;
import com.example.popularity.utils.LoginKind;
import com.example.popularity.utils.ShowMessageType;
import com.example.popularity.utils.ToolbarKind;

public interface LoginFragmentMvp {

    interface View{

        void openFragment(BaseFragment fragment, Boolean addStack, Bundle bundle);
        void changeToolbar(ToolbarKind kind,String title);
        void showLoadingBar(boolean isShow);
        void showMessage(ShowMessageType messageType, String message);
        Context getViewContext();
        void setMainUser(User user);
        void setLoginKind(LoginKind kind);
    }


    interface Presenter
    {
        void loginToServer();

    }
}
