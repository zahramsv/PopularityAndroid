package com.example.popularity.fragment;

import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.app.ActivityCompat;

import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.popularity.R;
import com.example.popularity.model.Friend;
import com.example.popularity.model.Login;
import com.example.popularity.model.PhoneContact;
import com.example.popularity.model.SocialRootModel;
import com.example.popularity.model.User;
import com.example.popularity.model.UserPopularity;
import com.example.popularity.myInterface.GetLoginDataService;
import com.example.popularity.utils.RetrofitInstance;
import com.example.popularity.utils.SavePref;
import com.example.popularity.utils.ToolbarKind;
import com.example.popularity.utils.sms.SmsHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class MobileLoginFragment extends BaseFragment implements
        SmsHandler.SmsHandlerListener {



    private SmsHandler smsHandler;
    private AppCompatEditText edt_phone_number, edt_verify_code;
    private String userMobile;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MobileLoginFragment() {

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if (!hidden) {
            baseListener.changeToolbar(ToolbarKind.HOME, "");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public static MobileLoginFragment newInstance() {
        MobileLoginFragment fragment = new MobileLoginFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mobile_login, container, false);
        edt_phone_number = view.findViewById(R.id.edt_phone_number);
        edt_verify_code = view.findViewById(R.id.edt_verify_code);

        smsHandler = new SmsHandler(this);
        view.findViewById(R.id.btn_receive_code_btn).setOnClickListener(view1 -> {
            smsHandler.requestSendSms(edt_phone_number.getText().toString());

        });

        view.findViewById(R.id.btn_verify_code).setOnClickListener(view1 -> {
            if (smsHandler.isVerifyCodeValid(edt_verify_code.getText().toString())) {
                loginToServer();

            } else {
                baseListener.showMessage("it is not valid.");
            }
        });
        return view;
    }


    private void loginToServer() {

        RetrofitInstance retrofitInstance = new RetrofitInstance();
        Retrofit retrofit = retrofitInstance.getRetrofitInstance();

        GetLoginDataService getLoginDataService = retrofit.create(GetLoginDataService.class);

        getLoginDataService.getLoginData(getLoginInfo()).enqueue(new Callback<SocialRootModel>() {
            @Override
            public void onResponse(Call<SocialRootModel> call, Response<SocialRootModel> response) {
                baseListener.showLoadingBar(false);

                Log.i("app_tag", response.toString());
                if ((response.isSuccessful())) {
                    SocialRootModel obr = response.body();
                    User data = obr.getData();
                    UserPopularity userPopularity = obr.getData().getRates_summary_sum();
                    SavePref savePref = new SavePref();
                    data.setSocial_primary(userMobile + "");
                    savePref.SaveUser(getContext(), data, userPopularity);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("User", data);
                    baseListener.openFragment(new HomeFragment(), true, bundle);
                    Log.i("app_tag", "info: " + obr.getCode());


                } else {
                    baseListener.showMessage(getString(R.string.error_api_call));
                    Log.i("app_tag", "error");
                }
            }

            @Override
            public void onFailure(Call<SocialRootModel> call, Throwable t) {
                baseListener.showMessage(getString(R.string.error_api_call));
                baseListener.showLoadingBar(false);

                Log.i("app_tag", t.getMessage().toString());
            }
        });
    }

    private Login getLoginInfo() {
        Login user = new Login();
        user.setAvatar_url("myavatar.jpg");
        user.setFull_name("zahra hadi");
        user.setSocial_primary(userMobile);
        user.setUsername("z.hadi");
        user.setSocial_type(0);
        return user;
    }



    @Override
    public void onSmsSendingResult(Boolean isSuccess, String message) {

        userMobile = edt_phone_number.getText().toString();
        baseListener.showMessage(message);
    }
}
