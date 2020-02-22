package com.example.popularity.mvp;

import android.content.Context;

import androidx.appcompat.widget.SwitchCompat;

public interface SettingMvp {

    interface View{
        Context getViewContext();
    }

    interface Presenter
    {
         void logout(int btnId, SwitchCompat switchCompat);
    }
}
