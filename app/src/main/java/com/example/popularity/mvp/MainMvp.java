package com.example.popularity.mvp;

import com.example.popularity.model.User;
import com.example.popularity.utils.LoginKind;

public interface MainMvp {

    interface Presenter {
        User getMainUser();
        void setMainUser(User user);
        void setLoginKind(LoginKind kind);
        LoginKind getLoginKind();
    }

    interface View {

    }

}
