package com.example.popularity.presenter;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.popularity.model.Friend;
import com.example.popularity.model.User;
import com.example.popularity.model.repository.FriendRepository;
import com.example.popularity.model.repository.LoginHandler;
import com.example.popularity.model.repository.UserRepository;
import com.example.popularity.mvp.HomeMvp;
import com.example.popularity.utils.MyApp;

import java.util.List;

import static com.example.popularity.utils.Configs.REQUEST_READ_CONTACTS;

public class HomePresenter implements HomeMvp.Presenter {
    private FriendRepository friendRepository;
    private Context context;
    private LoginHandler loginHandler;
    private UserRepository userRepository;

    public HomePresenter(Context context) {
        this.context = context;
        friendRepository = new FriendRepository();

        loginHandler = MyApp.getInstance().getBaseComponent().provideLoginHandler();
        userRepository = MyApp.getInstance().getBaseComponent().provideUserRepository();
    }

    @Override
    public List<Friend> getFriends(Context context) {

        switch (loginHandler.getLoginKind()) {
            case MOCK:
                return friendRepository.getFriendsFromMock(userRepository.getCurrentUser().getSocial_primary());

            case SMS: {
                if (ActivityCompat.checkSelfPermission(MyApp.getInstance(), android.Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
                    return friendRepository.getFriendsFromPhoneContacts(context);
                } else {
                    // fixme:
                    requestPermission(context);
                    return null;
                }
            }

            default:
                return null;
        }
    }

    @Override
    public User getUser() {
        return userRepository.getCurrentUser();
    }

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
