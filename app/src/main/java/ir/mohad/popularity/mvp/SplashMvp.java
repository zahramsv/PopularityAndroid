package ir.mohad.popularity.mvp;

import android.content.Context;

public interface SplashMvp {

    interface View {
        Context getViewContext();
    }

    interface Presenter {
        void getUserInfoAfterWait();
    }

}
