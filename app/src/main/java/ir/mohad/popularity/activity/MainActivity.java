package ir.mohad.popularity.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import ir.mohad.popularity.R;
import ir.mohad.popularity.fragment.BaseFragment;
import ir.mohad.popularity.fragment.IntroFragment;
import ir.mohad.popularity.fragment.MenuDrawerFragment;
import ir.mohad.popularity.fragment.SplashFragment;
import ir.mohad.popularity.model.repository.SharedPrefsRepository;
import ir.mohad.popularity.mvp.MainMvp;
import ir.mohad.popularity.myInterface.MainActivityTransaction;
import ir.mohad.popularity.presenter.MainPresenter;
import ir.mohad.popularity.utils.ConnectivityReceiver;
import ir.mohad.popularity.utils.MyApp;
import ir.mohad.popularity.utils.ShowMessageType;
import ir.mohad.popularity.utils.ToolBarIconKind;
import ir.mohad.popularity.utils.ToolbarKind;
import com.google.android.material.snackbar.Snackbar;

import java.util.Locale;


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
    private SharedPrefsRepository sharedPrefsRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPrefsRepository = new SharedPrefsRepository(this);
        setAppLang(sharedPrefsRepository.getApplicationLanguage());
        setContentView(R.layout.activity_main);
        presenter = new MainPresenter();
        init();
        setAppLang(sharedPrefsRepository.getApplicationLanguage());
        initNavigationDrawer();
        Intent intent = getIntent();
        SharedPrefsRepository sharedPrefsRepository = new SharedPrefsRepository(this);
        boolean isFirstTime = sharedPrefsRepository.isFirstTimeLaunch();
        Log.d("app_tag", "isFirstLaunch: " + isFirstTime);
        if (isFirstTime) {
            openFragment(new IntroFragment(), false, null);
        } else {
            openFragment(new SplashFragment(), false, null);
        }
    }


    public void setAppLang(String lang) {
        if (lang != "") {
            Locale myLocale;
            if (lang.equalsIgnoreCase(""))
                return;
            myLocale = new Locale(lang);
            Locale.setDefault(myLocale);
            android.content.res.Configuration config = new android.content.res.Configuration();
            config.locale = myLocale;
            getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());

        }


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
    public void closeKeyboard() {
        if (getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    @Override
    public void openFragment(BaseFragment fragment, Boolean addStack, Bundle bundle) {

        if (bundle != null) {
            fragment.setArguments(bundle);
        }

        fragment.attachFragment(this);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (!addStack) {

            transaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
            // sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
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


    @SuppressLint("ResourceAsColor")
    @Override
    public void changeToolbar(ToolbarKind kind, String title) {

        toolbarTitle.setText(title);

        switch (kind) {
            case EMPTY:
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                findViewById(R.id.imgToolbarIcon).setVisibility(View.GONE);
                findViewById(R.id.imageToolbarAppIcon).setVisibility(View.GONE);
                findViewById(R.id.toolbar).setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));

                break;

            case HOME:
                findViewById(R.id.toolbar).setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                findViewById(R.id.imgToolbarIcon).setVisibility(View.VISIBLE);
                findViewById(R.id.imageToolbarAppIcon).setVisibility(View.VISIBLE);
                toolbarIcon.setImageResource(R.drawable.ic_menu);
                toolbarIcon.setOnClickListener(v -> {
                    openDrawer();
                });
                getWindow().getDecorView().setSystemUiVisibility(View.VISIBLE);
                break;

            case BACK:
                findViewById(R.id.toolbar).setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                findViewById(R.id.imgToolbarIcon).setVisibility(View.VISIBLE);
                findViewById(R.id.imageToolbarAppIcon).setVisibility(View.VISIBLE);
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
            ft.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
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
        sharedPrefsRepository = new SharedPrefsRepository(this);
    }

    private void initNavigationDrawer() {
        MenuDrawerFragment slidingMenuFragment = (MenuDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_nd);
        assert slidingMenuFragment != null;
        slidingMenuFragment.attachFragment(this);
    }


}



