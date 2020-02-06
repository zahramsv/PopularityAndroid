package com.example.popularity.presenter;

import com.example.popularity.mvp.MainMvp;
import com.example.popularity.utils.LoginKind;

public class MainPresenter implements MainMvp.Presenter {

    private LoginKind loginKind = LoginKind.MOCK;

    @Override
    public void setLoginKind(LoginKind kind) {
        loginKind = kind;
    }

    @Override
    public LoginKind getLoginKind() {
        return loginKind;
    }

}
