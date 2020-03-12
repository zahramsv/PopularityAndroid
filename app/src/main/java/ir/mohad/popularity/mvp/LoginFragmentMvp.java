package ir.mohad.popularity.mvp;

import android.content.Context;
import ir.mohad.popularity.utils.LoginKind;

public interface LoginFragmentMvp {

    interface View {
        Context getViewContext();
    }

    interface Presenter {
        void setLoginKind(LoginKind kind);
        void loginUser();
    }
}
