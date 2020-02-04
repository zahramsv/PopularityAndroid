package com.example.popularity.presenter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.app.ActivityCompat;

import com.example.popularity.model.Friend;
import com.example.popularity.model.repository.FriendRepository;
import com.example.popularity.mvp.HomeMvp;
import com.example.popularity.utils.LoginKind;

import java.util.List;

import static com.example.popularity.utils.Configs.REQUEST_READ_CONTACTS;

public class HomePresenter implements HomeMvp.Presenter {
    private FriendRepository friendRepository;
    private Context context;

    public HomePresenter(Context context) {
        this.context = context;
        friendRepository = new FriendRepository();
    }

    @Override
    public List<Friend> getFriends(LoginKind loginKind, String userId) {
        switch (loginKind){
            case MOCK:
                return friendRepository.getFriendsFromMock(userId);

            case SMS:
                return friendRepository.getFriendsFromPhoneContacts(context);

            default:
                return null;
        }
    }



    @Override
    public void requestPermission(Context context) {

        if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, android.Manifest.permission.READ_CONTACTS)) {
            // show UI part if you want here to show some rationale !!!
        } else {
            ActivityCompat.requestPermissions((Activity) context, new String[]{android.Manifest.permission.READ_CONTACTS}, REQUEST_READ_CONTACTS);
        }
        if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, android.Manifest.permission.READ_CONTACTS)) {
        } else {
            ActivityCompat.requestPermissions((Activity) context, new String[]{android.Manifest.permission.READ_CONTACTS},
                    REQUEST_READ_CONTACTS);
        }
    }

}
