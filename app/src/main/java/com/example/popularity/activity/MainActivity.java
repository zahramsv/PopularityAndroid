package com.example.popularity.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.popularity.R;
import com.example.popularity.fragment.BaseFragment;
import com.example.popularity.fragment.MenuDrawerFragment;
import com.example.popularity.fragment.SplashFragment;
import com.example.popularity.model.User;
import com.example.popularity.myInterface.MainActivityTransaction;
import com.example.popularity.utils.ConnectivityReceiver;
import com.example.popularity.utils.MyApp;
import com.example.popularity.utils.ToolbarKind;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;


public class MainActivity extends AppCompatActivity implements
        MainActivityTransaction
        , ConnectivityReceiver.ConnectivityReceiverListener {
    private TextView toolbarTitle;
    private DrawerLayout drawerLayout;
    private ProgressBar loadingBar;
    private User mainUser;
    private ImageView toolbar_icon;
    private View parent_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        initNavigationDrawer();

        openFragment(new SplashFragment(), false, null);
    }

    @Override
    public User getMainUser() {
        return mainUser;
    }

    @Override
    public void setMainUser(User mainUser) {
        this.mainUser = mainUser;
    }

    @Override
    public void openFragment(BaseFragment fragment, Boolean addStack, Bundle bundle) {

        if (bundle != null) {
            fragment.setArguments(bundle);
        }

        fragment.attachFragment(this);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
        if (!addStack) {
            transaction.replace(R.id.your_placeholder, fragment);
        } else {
            Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.your_placeholder);
            if (currentFragment != null) {
                transaction.hide(currentFragment);
                transaction.commit();
            }
            transaction = getSupportFragmentManager().beginTransaction();
            transaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
            transaction.add(R.id.your_placeholder, fragment);
            transaction.addToBackStack(fragment.getClass().getName());
        }
        transaction.commit();
        closeDrawer();


    }

    @Override
    public void changeToolbar(ToolbarKind kind, String title) {

        toolbarTitle.setText(title);

        switch (kind) {
            case EMPTY:
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                findViewById(R.id.toolbar).setVisibility(View.GONE);
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
                break;

            case HOME:
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                findViewById(R.id.toolbar).setVisibility(View.VISIBLE);
                toolbar_icon.setImageResource(R.drawable.ic_menu);
                toolbar_icon.setOnClickListener(v -> {
                    openDrawer();
                });
                getWindow().getDecorView().setSystemUiVisibility(View.VISIBLE);
                break;

            case BACK:
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                findViewById(R.id.toolbar).setVisibility(View.VISIBLE);
                toolbar_icon.setImageResource(R.drawable.ic_back);
                toolbar_icon.setOnClickListener(v -> {
                    onBackPressed();
                });
                getWindow().getDecorView().setSystemUiVisibility(View.VISIBLE);
                break;

            default:
                break;
        }
    }

    @Override
    public void showSnackBar(String message) {
        Snackbar snackbar = Snackbar.make(parent_view, message, Snackbar.LENGTH_LONG);
        snackbar.setAction(getString(R.string.okay), view -> {
            snackbar.dismiss();
            //Snackbar.make(parent_view, "UNDO CLICKED!", Snackbar.LENGTH_SHORT).show();
        });
        snackbar.show();
    }

    private void showCustomDialog() {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_sign_in);
        dialog.setCancelable(true);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.show();
        dialog.getWindow().setAttributes(lp);

        TextInputEditText username, password;
        username = dialog.findViewById(R.id.username);
        Button btn = dialog.findViewById(R.id.signIn_btn);
        password = dialog.findViewById(R.id.password);
        btn.setOnClickListener(v -> {
            //fixme: login();
        });


    }

    int count = 0;

    @Override
    public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();
        if (fm.getBackStackEntryCount() == 0) {
            if (count < 1) {
                showMessage("are you sure?");
                count++;
            } else
                this.finish();
        } else {
            super.onBackPressed();
            Fragment fragment = fm.findFragmentById(R.id.your_placeholder);
            FragmentTransaction ft = fm.beginTransaction();
            ft.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
            if (fragment != null) {
                ft.show(fragment);
                ft.commit();
            }
        }

    }

    private void openDrawer() {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    private void closeDrawer() {
        drawerLayout.closeDrawer(GravityCompat.START);
    }

    @Override
    public void showLoadingBar(boolean isShow) {
        if (isShow) {
            loadingBar.setVisibility(View.VISIBLE);
        } else {
            loadingBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // register connection status listener
        MyApp.getInstance().setConnectivityListener(this);
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        if (!isConnected)
            showSnackBar(getString(R.string.error_disconnected));
    }

    private void init() {
        parent_view = findViewById(R.id.parent_view);
        drawerLayout = findViewById(R.id.drawer_layout);
        toolbar_icon = findViewById(R.id.toolbar_icon);
        toolbarTitle = findViewById(R.id.txtToolbar);
        loadingBar = findViewById(R.id.loadingBar);
    }

    private void initNavigationDrawer() {
        MenuDrawerFragment slidingMenuFragment = (MenuDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_nd);
        assert slidingMenuFragment != null;
        slidingMenuFragment.attachFragment(this);
    }



}



