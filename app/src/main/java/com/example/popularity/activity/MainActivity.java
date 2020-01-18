package com.example.popularity.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

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
import com.example.popularity.myInterface.UserTransaction;
import com.example.popularity.utils.RetrofitInstance;
import com.example.popularity.utils.SavePref;
import com.example.popularity.utils.ToolbarState;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONObject;

import java.io.Serializable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class MainActivity extends AppCompatActivity implements
        MenuDrawerFragment.OnSlidingMenuFragmentListener,ToolbarState, MenuDrawerFragment.OpenMenuFragments
        , UserTransaction
{

    private TextInputEditText username, password;
    private String usernameTxt, passwordTxt;
    private User userInfo=new User();
    private DrawerLayout drawerLayout;
    private MenuDrawerFragment slidingMenuFragment;
    private getUserDataSplash getUserDataSplash;

    private User mainUser;



    @Override
    public User getMainUser() {
        return mainUser;
    }

    @Override
    public void setMainUser(User mainUser) {
        this.mainUser = mainUser;


      //  Log.d("app_tag", "user data: "+mainUser.getFull_name());
        final Handler handler = new Handler();

        handler.postDelayed(() -> {
            // Do something after 2s = 2000ms
            if (mainUser==null)
            {
                OpenFragment(new LoginFragment(),false,null);
            }
            else {


                OpenFragment (new HomeFragment(),false,mainUser);
            }

        }, 2000);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        drawerLayout    = findViewById(R.id.drawer_layout);
        slidingMenuFragment = (MenuDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_nd);

        OpenFragment(new SplashFragment(this),false,null);



        ImageView menuDrawer = findViewById(R.id.menuDrawer);
        menuDrawer.setOnClickListener(v->{
            openDrawer();
        });

    }




    private void OpenFragment(Fragment fragment, Boolean addStack,Object object){

        if (object!=null)
        {
            Bundle bundle=new Bundle();
            bundle.putSerializable("User", (Serializable) object);
            fragment.setArguments(bundle);
        }

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
        if (!addStack)
        {
            transaction.replace(R.id.your_placeholder, fragment);
        }
       else
        {
            transaction.add(R.id.your_placeholder,fragment);
            transaction.addToBackStack(fragment.getClass().getName());
        }
        transaction.commit();
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
        final Dialog dialog = new Dialog(this);
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
                        if((response.isSuccessful())){
                            SocialRootModel obr = response.body();


                            User data = obr.getData();
                            UserPopularity userPopularity=obr.getData().getRates_summary_sum();
                            SavePref savePref=new SavePref();
                            data.setSocial_primary((socialLoginLogic.GetFirstUserLoginData().getSocial_primary())+"");
                            savePref.SaveUser(MainActivity.this,data,userPopularity);
                            Bundle bundle=new Bundle();
                            bundle.putSerializable("User",data);
                            HomeFragment homeFragment=new HomeFragment();
                            homeFragment.setArguments(bundle);
                            OpenFragment(homeFragment,true,null);
                            Log.i("app_tag", "info: "+obr.getCode());


                        }else{
                            Log.i("app_tag", "error");
                        }
                    }

                    @Override
                    public void onFailure(Call<SocialRootModel> call, Throwable t) {
                        Log.i("app_tag", t.getMessage().toString());
                    }
                });
                dialog.dismiss();
                OpenFragment(new HomeFragment(),true,null);

            }
        });


    }

    private void loginToInstagram(String username, String password) {




        /* instagram = Instagram4Android.builder().username(username).password(password).build();
        instagram.setup();
        new AsyncCaller().execute();*/


    }

    private String TAG = "TAG";

    @Override
    public void onBackPressed() {

        int count=getSupportFragmentManager().getBackStackEntryCount();
        if (count==0)
        super.onBackPressed();
        else
            getSupportFragmentManager().popBackStack();
    }

     @Override
   public void onBtn1Clicked(Fragment fragment) {
         OpenFragment(fragment,true,null);
         closeDrawer();
   }




    @Override
    public void onBtn2Clicked(Fragment fragment)
    {

        OpenFragment(fragment,true,null);
        closeDrawer();
    }




    private void openDrawer(){
        drawerLayout.openDrawer(GravityCompat.START);
    }

    private void closeDrawer(){
        drawerLayout.closeDrawer(GravityCompat.START);
    }

    @Override
    public void toolbarState(Boolean flag) {
         if (!flag)
         {


             findViewById(R.id.toolbar).setVisibility(View.INVISIBLE);
             getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
         }
         else if (flag)
         {
            // TextView tt=findViewById(R.id.txtToolbar);
            // tt.setText("Setting");
             getWindow().getDecorView().setSystemUiVisibility(View.VISIBLE);
             findViewById(R.id.toolbar).setVisibility(View.VISIBLE);
         }
    }


    @Override
    public void Open(Fragment fragment) {
        OpenFragment(fragment,true,null);
        closeDrawer();

    }


    public interface getUserDataSplash
    {
        User GetUserSplash();
    }


}



