package com.example.popularity.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.example.popularity.R;
import com.example.popularity.fragment.BaseFragment;
import com.example.popularity.fragment.MenuDrawerFragment;
import com.example.popularity.fragment.SplashFragment;
import com.example.popularity.mvp.MainMvp;
import com.example.popularity.myInterface.MainActivityTransaction;
import com.example.popularity.presenter.MainPresenter;
import com.example.popularity.utils.ConnectivityReceiver;
import com.example.popularity.utils.MyApp;
import com.example.popularity.utils.ShowMessageType;
import com.example.popularity.utils.ToolBarIconKind;
import com.example.popularity.utils.ToolbarKind;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity implements
        MainActivityTransaction.Components
        , ConnectivityReceiver.ConnectivityReceiverListener
        , MainMvp.View {
    private TextView toolbarTitle;
    private AppCompatImageView imageToolbarAppIcon;
    private DrawerLayout drawerLayout;
    private ProgressBar loadingBar;
    private ImageView toolbarIcon;
    private View parentView;
    private MainMvp.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        presenter = new MainPresenter();
        init();
        initNavigationDrawer();
        openFragment(new SplashFragment(), false, null);
    }

    @Override
    public void showMessage(ShowMessageType messageType, String message) {
        switch (messageType) {
            case SNACK:
                Snackbar snackbar = Snackbar.make(parentView, message, Snackbar.LENGTH_LONG);
                snackbar.setAction(getString(R.string.okay), view -> {
                    snackbar.dismiss();
                });
                snackbar.show();
                break;

            case TOAST:
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
                break;
        }
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
            transaction.replace(R.id.frmPlaceholder, fragment);
        } else {
            Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.frmPlaceholder);
            if (currentFragment != null) {
                transaction.hide(currentFragment);
                transaction.commit();
            }
            transaction = getSupportFragmentManager().beginTransaction();
            transaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
            transaction.add(R.id.frmPlaceholder, fragment);
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
                toolbarIcon.setImageResource(R.drawable.ic_menu);
                toolbarIcon.setOnClickListener(v -> {
                    openDrawer();
                });
                getWindow().getDecorView().setSystemUiVisibility(View.VISIBLE);
                break;

            case BACK:
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                findViewById(R.id.toolbar).setVisibility(View.VISIBLE);
                toolbarIcon.setImageResource(R.drawable.ic_back);
                toolbarIcon.setOnClickListener(v -> {
                    onBackPressed();
                });
                getWindow().getDecorView().setSystemUiVisibility(View.VISIBLE);
                break;

            default:
                break;
        }
    }

    @Override
    public void showToolbarIcon(ToolBarIconKind iconKind) {
        switch (iconKind) {
            case VISIBLEL:
                imageToolbarAppIcon.setVisibility(View.VISIBLE);
                break;

            case INVISIBLE:
                imageToolbarAppIcon.setVisibility(View.INVISIBLE);
                break;

            default:
                break;
        }
    }

    int count = 0;

    @Override
    public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();
        if (fm.getBackStackEntryCount() == 0) {
            if (count < 1) {
                showMessage(ShowMessageType.TOAST, "are you sure?");
                count++;
            } else
                this.finish();
        } else {
            super.onBackPressed();
            Fragment fragment = fm.findFragmentById(R.id.frmPlaceholder);
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
    protected void onResume() {
        super.onResume();
        MyApp.getInstance().setConnectivityListener(this);
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        if (!isConnected)
            showMessage(ShowMessageType.SNACK, getString(R.string.error_disconnected));

    }

    private void init() {
        parentView = findViewById(R.id.ctlParentView);
        drawerLayout = findViewById(R.id.drawerLayout);
        toolbarIcon = findViewById(R.id.imgToolbarIcon);
        toolbarTitle = findViewById(R.id.txtToolbar);
        imageToolbarAppIcon = findViewById(R.id.imageToolbarAppIcon);
        loadingBar = findViewById(R.id.loadingBar);
    }

    private void initNavigationDrawer() {
        MenuDrawerFragment slidingMenuFragment = (MenuDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_nd);
        assert slidingMenuFragment != null;
        slidingMenuFragment.attachFragment(this);
    }


}



