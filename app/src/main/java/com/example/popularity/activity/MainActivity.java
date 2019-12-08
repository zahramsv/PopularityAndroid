package com.example.popularity.activity;

import android.app.Dialog;
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
import com.example.popularity.utils.ToolbarState;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONObject;


public class MainActivity extends AppCompatActivity implements
        MenuDrawerFragment.OnSlidingMenuFragmentListener,ToolbarState
{

    private TextInputEditText username, password;
    private String usernameTxt, passwordTxt;

    DrawerLayout drawerLayout;
    MenuDrawerFragment slidingMenuFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout    = findViewById(R.id.drawer_layout);
        slidingMenuFragment = (MenuDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_nd);

        openFragment(new SplashFragment(),false);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 2s = 2000ms
                openFragment(new LoginFragment(),false);
            }
        }, 2000);

        ImageView menuDrawer = findViewById(R.id.menuDrawer);
        menuDrawer.setOnClickListener(v->{
            openDrawer();
        });

    }

    public void setTitle(String s)
    {
        TextView textView=findViewById(R.id.txtToolbar);
        textView.setText(s);
    }

    private void openFragment(Fragment fragment, Boolean addStack){


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

    public void loginBtnClick(View view) {
        switch (view.getId()) {
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
                loginToInstagram(usernameTxt, passwordTxt);

                dialog.dismiss();
                openFragment(new HomeFragment(),true);

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

   /* @Override
    public void onBtn1Clicked(String str) {
        setTitle(str);
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile"));

    }*/

     @Override
   public void onBtn1Clicked(Fragment fragment) {
       openFragment(fragment,true);
       closeDrawer();

   }


   //GraphRequest - ApiRequest
    // in nabaiad inja bashe baiad dakhel loginFragment to inSuccess bezar
   private void getFacebookData(){
       AccessToken accessToken = AccessToken.getCurrentAccessToken();
       boolean isLoggedIn = accessToken != null && !accessToken.isExpired();
       if(isLoggedIn){
           GraphRequest request = GraphRequest.newMeRequest(
                   accessToken,
                   new GraphRequest.GraphJSONObjectCallback() {
                       @Override
                       public void onCompleted(JSONObject object, GraphResponse response) {
                           Log.i("app_tag",response.toString());
                           Log.i("app_tag",object.toString());

                           //Call loginBySocial Mahad
                           //parse Recived Data
                       }
                   });

           Bundle parameters = new Bundle();
           parameters.putString("fields", "id,name");
           request.setParameters(parameters);
           request.executeAsync();
       }
   }


    @Override
    public void onBtn2Clicked(Fragment fragment)
    {

        openFragment(fragment,true);
        closeDrawer();
    }

    @Override
    public void onTestFaceBook() {
        getFacebookData();
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
             getWindow().getDecorView().setSystemUiVisibility(View.VISIBLE);
             findViewById(R.id.toolbar).setVisibility(View.VISIBLE);
         }
    }
}



