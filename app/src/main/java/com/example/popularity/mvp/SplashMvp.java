package com.example.popularity.mvp;

import android.content.Context;
import android.os.Bundle;

import com.example.popularity.fragment.BaseFragment;
import com.example.popularity.model.User;

public interface SplashMvp {

    interface View {
        Context getViewContext();
    }

    interface Presenter {
        void getUserInfoAfterWait();
    }

}
