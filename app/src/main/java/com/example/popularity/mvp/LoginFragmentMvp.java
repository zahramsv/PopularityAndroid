package com.example.popularity.mvp;

import android.content.Context;
import com.example.popularity.utils.LoginKind;

public interface LoginFragmentMvp {

    interface View {
        Context getViewContext();
    }

    interface Presenter {
        void setLoginKind(LoginKind kind);
        void loginUser();
    }
}
