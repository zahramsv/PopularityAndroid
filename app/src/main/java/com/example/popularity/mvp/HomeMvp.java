package com.example.popularity.mvp;

import android.content.Context;
import android.net.Uri;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.popularity.model.Friend;
import com.example.popularity.model.User;
import com.example.popularity.utils.LoginKind;

import java.io.File;
import java.net.URI;
import java.util.List;

public interface HomeMvp {


    interface View {
        void ShareScreenShot(Uri uri);
    }

    interface Presenter {
        List<Friend> getFriends(Context context);
        User getUser();
        void takeScreenShot(android.view.View view);
        void openScreenShot(File file);

    }
}
