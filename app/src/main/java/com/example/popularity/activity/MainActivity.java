package com.example.popularity.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.popularity.fragment.BaseFragment;
import com.example.popularity.fragment.HomeFragment;
import com.example.popularity.fragment.LoginFragment;
import com.example.popularity.fragment.MenuDrawerFragment;
import com.example.popularity.fragment.SplashFragment;
import com.example.popularity.R;
import com.example.popularity.logic.SocialLoginLogic;
import com.example.popularity.model.SocialRootModel;
import com.example.popularity.model.User;
import com.example.popularity.model.UserPopularity;
import com.example.popularity.myInterface.GetLoginDataService;
import com.example.popularity.myInterface.MainActivityTransaction;
import com.example.popularity.myInterface.UserTransaction;
import com.example.popularity.utils.RetrofitInstance;
import com.example.popularity.utils.SavePref;
import com.example.popularity.utils.ToolbarState;
import com.google.android.material.textfield.TextInputEditText;

import java.io.Serializable;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class MainActivity extends AppCompatActivity implements
          ToolbarState
        , UserTransaction
        , MainActivityTransaction
{

    private TextInputEditText username, password;
    private String usernameTxt, passwordTxt;
    private User userInfo = new User();
    private DrawerLayout drawerLayout;
    private MenuDrawerFragment slidingMenuFragment;
    private getUserDataSplash getUserDataSplash;
    private ProgressBar loadingBar;
    Dialog dialog;
    private User mainUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawer_layout);
        slidingMenuFragment = (MenuDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_nd);
        assert slidingMenuFragment != null;
        slidingMenuFragment.attachFragment(this);
        loadingBar = findViewById(R.id.loadingBar);
        openFragment(new SplashFragment(this), false, null);

        ImageView menuDrawer = findViewById(R.id.menuDrawer);
        menuDrawer.setOnClickListener(v -> {
            openDrawer();
        });

    }


    @Override
    public User getMainUser() {
        return mainUser;
    }

    @Override
    public void setMainUser(User mainUser) {
        this.mainUser = mainUser;
        if (mainUser == null) {
            openFragment(new LoginFragment(), false, null);
        } else {
            Bundle bundle = new Bundle();
            bundle.putSerializable("User", mainUser);
            openFragment(new HomeFragment(), false, bundle);
        }
    }

    @Override
    public void openFragment(BaseFragment fragment, Boolean addStack, Bundle bundle) {

        if(bundle!=null){
            fragment.setArguments(bundle);
        }

        fragment.attachFragment(this);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
        if (!addStack) {
            transaction.replace(R.id.your_placeholder, fragment);
        } else {
            Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.your_placeholder);
            if(currentFragment!=null){
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void loginBtnClick(View view) {
        switch (view.getId()) {


            case R.id.login_api_button:

                break;

            case R.id.instagram_btn:


                showCustomDialog();
                break;


            case R.id.signIn_btn:


                break;
        }


    }

    private void showCustomDialog() {
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.sign_in_dialog);
        dialog.setCancelable(true);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.show();
        dialog.getWindow().setAttributes(lp);

        username = dialog.findViewById(R.id.username);
        Button btn = dialog.findViewById(R.id.signIn_btn);
        password = dialog.findViewById(R.id.password);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });


    }

    public void login()
    {
        usernameTxt = username.getText().toString();
        passwordTxt = password.getText().toString();
        //  loginToInstagram(usernameTxt, passwordTxt);

        RetrofitInstance retrofitInstance = new RetrofitInstance();
        Retrofit retrofit = retrofitInstance.getRetrofitInstance();
        SocialLoginLogic socialLoginLogic = new SocialLoginLogic();
        socialLoginLogic.GetFirstUserLoginData();

        GetLoginDataService getLoginDataService = retrofit.create(GetLoginDataService.class);


        getLoginDataService.getLoginData(socialLoginLogic.GetFirstUserLoginData()).enqueue(new Callback<SocialRootModel>() {
            @Override
            public void onResponse(Call<SocialRootModel> call, Response<SocialRootModel> response) {

                Log.i("app_tag", response.toString());
                if ((response.isSuccessful())) {
                    SocialRootModel obr = response.body();


                    User data = obr.getData();
                    UserPopularity userPopularity = obr.getData().getRates_summary_sum();
                    SavePref savePref = new SavePref();
                    data.setSocial_primary((socialLoginLogic.GetFirstUserLoginData().getSocial_primary()) + "");
                    savePref.SaveUser(MainActivity.this, data, userPopularity);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("User", data);
                    openFragment(new HomeFragment(), false, bundle);
                    Log.i("app_tag", "info: " + obr.getCode());


                } else {
                    Log.i("app_tag", "error");
                }
            }

            @Override
            public void onFailure(Call<SocialRootModel> call, Throwable t) {
                Log.i("app_tag", t.getMessage().toString());
            }
        });
        dialog.dismiss();
    }
    private void loginToInstagram(String username, String password) {




        /* instagram = Instagram4Android.builder().username(username).password(password).build();
        instagram.setup();
        new AsyncCaller().execute();*/


    }

    private String TAG = "TAG";


    int count = 0;
    @Override
    public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();
        if(fm.getBackStackEntryCount() == 0){
            if(count<1) {
                showMessage("are you sure?");
                count++;
            }
            else
                this.finish();
        }else{
            super.onBackPressed();

            Fragment fragment = fm.findFragmentById(R.id.your_placeholder);
            FragmentTransaction ft = fm.beginTransaction();
            if(fragment!=null){
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
    public void toolbarState(Boolean flag, String toolbarTitle) {
        if (!flag) {
            findViewById(R.id.toolbar).setVisibility(View.INVISIBLE);
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        } else if (flag) {
            TextView tt = findViewById(R.id.txtToolbar);
            tt.setText(toolbarTitle);
            getWindow().getDecorView().setSystemUiVisibility(View.VISIBLE);
            findViewById(R.id.toolbar).setVisibility(View.VISIBLE);
        }
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


    public interface getUserDataSplash {
        User GetUserSplash();
    }


}



