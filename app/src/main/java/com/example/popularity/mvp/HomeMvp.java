package com.example.popularity.mvp;

import android.content.Context;

import com.example.popularity.model.Friend;
import com.example.popularity.utils.LoginKind;

import java.util.List;

public interface HomeMvp {


    interface View{

    }

    interface Presenter
    {
         List<Friend> getFriends(LoginKind loginKind, String userId);
         void requestPermission(Context context);
    }
}
