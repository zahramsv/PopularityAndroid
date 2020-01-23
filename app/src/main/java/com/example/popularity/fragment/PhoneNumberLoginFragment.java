package com.example.popularity.fragment;

import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.popularity.R;
import com.example.popularity.model.PhoneContact;
import com.example.popularity.utils.ToolbarKind;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class PhoneNumberLoginFragment extends BaseFragment {


    private List<PhoneContact> phoneContacts=new ArrayList<>();
    private String id;
    private String name;
    private String phoneNo;

    public static final int REQUEST_READ_CONTACTS = 79;
    List<PhoneContact> mobileArray;
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public PhoneNumberLoginFragment() {

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if (!hidden)
        {
            baseListener.changeToolbar(ToolbarKind.BACK,"");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseListener.changeToolbar(ToolbarKind.HOME, "");
        if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.READ_CONTACTS)
                == PackageManager.PERMISSION_GRANTED) {
            mobileArray= getAllContacts();
        } else {
            requestPermission();
        }
    }

    public static PhoneNumberLoginFragment newInstance() {
        PhoneNumberLoginFragment fragment = new PhoneNumberLoginFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_phone_number_login, container, false);
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
        if ((cur!= null ? cur.getCount() : 0) > 0) {
            while (cur != null & cur.moveToNext()) {
                id = cur.getString(
                        cur.getColumnIndex(ContactsContract.Contacts._ID));
                name = cur.getString(cur.getColumnIndex(
                        ContactsContract.Contacts.DISPLAY_NAME));
                nameList.add(name);
                if (cur.getInt(cur.getColumnIndex( ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                    Cursor pCur = cr.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{id}, null);
                    while (pCur.moveToNext()) {
                        phoneNo = pCur.getString(pCur.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.NUMBER));

                    }

                    if (id!=null && name!=null && IsValidMobileNumber(phoneNo))
                    {
                        phoneContacts.add(new PhoneContact(id,name,phoneNo));
                    }
                    pCur.close();
                }
            }
        }
        if (cur!=null) {
            cur.close();
        }
        if (nameList!=null)
        {
            Log.i("app_tag","correct");
        }
        return phoneContacts;
    }

    public static boolean IsValidMobileNumber( String input) {

        //(0|\+98)?([ ]|,|-|[()]){0,2}9[1|2|3|4]([ ]|,|-|[()]){0,2}(?:[0-9]([ ]|,|-|[()]){0,2}){8}
        input = input.replace("+98", "0");
        input = input.replace(" ", "");
        input=input.trim();
        Pattern pattern = Pattern.compile("^09[0|1|2|3][0-9]{8}$");
        Matcher matcher = pattern.matcher(input);
        if (matcher.matches())
        {
            return true;
        }
        else
            return false;
    }

}
