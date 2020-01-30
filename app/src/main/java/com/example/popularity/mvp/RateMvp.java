package com.example.popularity.mvp;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.popularity.model.Friend;
import com.example.popularity.model.User;
import com.example.popularity.utils.ShowMessageType;

public interface RateMvp {

    interface RateView {
        FragmentManager returnFmManager();
        Fragment returnFragment();
        Context getViewContext();
        void showMessage(ShowMessageType messageType, String message);
        User getUserData();
        Friend getFriend();
        View getCurrentView();
    }

    interface Presenter {
        void SubmitRate();

    }
}

