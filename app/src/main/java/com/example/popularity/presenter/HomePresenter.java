package com.example.popularity.presenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import com.example.popularity.R;
import com.example.popularity.activity.MainActivity;
import com.example.popularity.fragment.HomeFragment;
import com.example.popularity.model.Friend;
import com.example.popularity.model.User;
import com.example.popularity.model.repository.FriendRepository;
import com.example.popularity.model.repository.LoginHandler;
import com.example.popularity.model.repository.UserRepository;
import com.example.popularity.mvp.HomeMvp;
import com.example.popularity.utils.MyApp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.Date;
import java.util.List;

import static com.example.popularity.utils.Configs.REQUEST_READ_CONTACTS;

public class HomePresenter extends FileProvider implements HomeMvp.Presenter {
    private FriendRepository friendRepository;
    private Context context;
    private LoginHandler loginHandler;
    private UserRepository userRepository;
    private FileOutputStream outputStream2;
    private HomeMvp.View view;

    public HomePresenter(HomeMvp.View view, Context context) {
        this.view = view;
        this.context = context;
        friendRepository = new FriendRepository();

        loginHandler = MyApp.getInstance().getBaseComponent().provideLoginHandler();
        userRepository = MyApp.getInstance().getBaseComponent().provideUserRepository();

        this.context = MyApp.getInstance().getBaseContext().getApplicationContext();
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

    @Override
    public void takeScreenShot(View view) {


        try {
            // image naming and path  to include sd card  appending name you choose for file
            view.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
            view.setDrawingCacheEnabled(false);

            outputStream2 = MyApp.getInstance().getBaseComponent().provideOutputSteam();
            int quality = 100;
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream2);
            outputStream2.flush();
            outputStream2.close();
            openScreenShot(MyApp.getInstance().getBaseComponent().provideFile());
        } catch (Throwable e) {
            // Several error may come out with file handling or DOM
            e.printStackTrace();

        }
    }

    @Override
    public void openScreenShot(File imageFile) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        Uri uri = FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".provider", imageFile);
        view.ShareScreenShot(uri);
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
