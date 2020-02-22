package com.example.popularity.presenter;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
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
import com.example.popularity.myInterface.MainActivityTransaction;
import com.example.popularity.utils.MyApp;
import com.example.popularity.utils.PermissionStatus;
import com.example.popularity.utils.ShowMessageType;
import com.tedpark.tedpermission.rx2.TedRx2Permission;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;

import static com.example.popularity.utils.Configs.REQUEST_READ_CONTACTS;

public class HomePresenter extends FileProvider implements HomeMvp.Presenter {
    private FriendRepository friendRepository;
    private Context context;
    private LoginHandler loginHandler;
    private UserRepository userRepository;
    private FileOutputStream outputStream2;
    private HomeMvp.View view;
    private MainActivityTransaction.Components baseListener;
    private List<Friend> friendsList = new ArrayList<>();
    private Observable<List<Friend>> friendObservableList = new Observable<List<Friend>>() {
        @Override
        protected void subscribeActual(@NonNull Observer<? super List<Friend>> observer) {

        }
    } ;

    public HomePresenter(
            HomeMvp.View view,
            Context context,
            MainActivityTransaction.Components baseListener
    ) {
        this.baseListener = baseListener;
        this.view = view;
        this.context = context;
        friendRepository = new FriendRepository();

        loginHandler = MyApp.getInstance().getBaseComponent().provideLoginHandler();
        userRepository = MyApp.getInstance().getBaseComponent().provideUserRepository();

        this.context = MyApp.getInstance().getBaseContext().getApplicationContext();
    }

    @Override
    public List<Friend> getFriends(Context context) {
        return friendsList;
    }

    @Override
    public Observable<List<Friend>> getObservable(){
        return friendObservableList;
    }

    @Override
    public void provideFriends() {
        switch (loginHandler.getLoginKind()) {
            case MOCK:
                friendsList = friendRepository.getFriendsFromMock(userRepository.getCurrentUser().getSocial_primary());
                friendObservableList = Observable.just(friendsList);
                break;
            case SMS: {
                getPermission(Manifest.permission.READ_CONTACTS);
            }
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

    @SuppressLint("CheckResult")
    private void getPermission(String permission) {
        TedRx2Permission.with(view.getViewContext())
                .setRationaleTitle(R.string.hint_get_permissions)
                //.setRationaleMessage(R.string.rationale_message) // "we need permission for read contact and find your location"
                .setPermissions(permission)
                .request()
                .subscribe(tedPermissionResult -> {
                    if (tedPermissionResult.isGranted()) {

                        Log.d("app_tag", "granted");

                        friendsList = friendRepository.getFriendsFromPhoneContacts(view.getViewContext());
                        friendObservableList = Observable.just(friendsList);
                    } else {

                        Log.d("app_tag", "not granted");
                        baseListener.showMessage(ShowMessageType.TOAST, view.getViewContext().getString(R.string.hint_you_should_confirm_permissions));
                    }
                }, throwable -> {
                    Log.d("app_tag", "throw error");

                });
    }

}
