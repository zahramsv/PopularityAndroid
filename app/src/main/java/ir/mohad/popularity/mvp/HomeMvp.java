package ir.mohad.popularity.mvp;

import android.content.Context;
import android.net.Uri;

import androidx.appcompat.app.AppCompatActivity;

import ir.mohad.popularity.model.Friend;
import ir.mohad.popularity.model.User;

import java.io.File;
import java.util.List;

import io.reactivex.rxjava3.core.Observable;

public interface HomeMvp {


    interface View {
        void ShareScreenShot(Uri uri);
        Context getViewContext();
        void setFriendsList(List<Friend> friends);
        AppCompatActivity getFragActivity();
    }

    interface Presenter {
        Observable<List<Friend>> getObservable();
        void provideFriends();
        User getUser();
        void takeScreenShot(android.view.View view);
        void openScreenShot(File file);

    }
}
