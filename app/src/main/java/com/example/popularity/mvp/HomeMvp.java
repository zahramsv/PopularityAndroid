package com.example.popularity.mvp;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import com.example.popularity.model.Friend;
import com.example.popularity.model.User;
import com.example.popularity.utils.LoginKind;

import java.util.List;

public interface HomeMvp {


    interface View {

    }

    interface Presenter {
        List<Friend> getFriends(Context context);
        User getUser();
    }
}
