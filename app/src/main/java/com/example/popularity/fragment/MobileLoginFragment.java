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


    private List<PhoneContact> phoneContacts = new ArrayList<>();
    private String id;
    private String name;
    private String phoneNo;
    private SmsHandler smsHandler;
    private AppCompatEditText edt_phone_number, edt_verify_code;
    private String userMobile;

    public static final int REQUEST_READ_CONTACTS = 79;
    List<PhoneContact> mobileArray;

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
        baseListener.changeToolbar(ToolbarKind.HOME, "");
        if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.READ_CONTACTS)
                == PackageManager.PERMISSION_GRANTED) {
            mobileArray = getAllContacts();
        } else {
            requestPermission();
        }
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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_READ_CONTACTS: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mobileArray = getAllContacts();
                } else {
                    // permission denied,Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
        }
    }

    private void requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), android.Manifest.permission.READ_CONTACTS)) {
            // show UI part if you want here to show some rationale !!!
        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.READ_CONTACTS}, REQUEST_READ_CONTACTS);
        }
        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), android.Manifest.permission.READ_CONTACTS)) {
        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.READ_CONTACTS},
                    REQUEST_READ_CONTACTS);
        }
    }

    private List<PhoneContact> getAllContacts() {
        ArrayList<String> nameList = new ArrayList<>();
        ContentResolver cr = getContext().getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);
        if ((cur != null ? cur.getCount() : 0) > 0) {
            while (cur != null & cur.moveToNext()) {
                id = cur.getString(
                        cur.getColumnIndex(ContactsContract.Contacts._ID));
                name = cur.getString(cur.getColumnIndex(
                        ContactsContract.Contacts.DISPLAY_NAME));
                nameList.add(name);
                if (cur.getInt(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                    Cursor pCur = cr.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{id}, null);
                    while (pCur.moveToNext()) {
                        phoneNo = pCur.getString(pCur.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.NUMBER));

                    }

                    if (id != null && name != null && IsValidMobileNumber(phoneNo)) {
                        phoneContacts.add(new PhoneContact(id, name, phoneNo));
                    }
                    pCur.close();
                }
            }
        }
        if (cur != null) {
            cur.close();
        }
        if (nameList != null) {
            Log.i("app_tag", "correct");
        }
        return phoneContacts;
    }

    public static boolean IsValidMobileNumber(String input) {

        //(0|\+98)?([ ]|,|-|[()]){0,2}9[1|2|3|4]([ ]|,|-|[()]){0,2}(?:[0-9]([ ]|,|-|[()]){0,2}){8}
        input = input.replace("+98", "0");
        input = input.replace(" ", "");
        input = input.trim();
        Pattern pattern = Pattern.compile("^09[0|1|2|3][0-9]{8}$");
        Matcher matcher = pattern.matcher(input);
        if (matcher.matches()) {
            return true;
        } else
            return false;
    }

    @Override
    public void onSmsSendingResult(Boolean isSuccess, String message) {

        userMobile = edt_phone_number.getText().toString();
        baseListener.showMessage(message);
    }
}
