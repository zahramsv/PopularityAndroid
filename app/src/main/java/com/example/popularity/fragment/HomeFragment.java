package com.example.popularity.fragment;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;

import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.popularity.adapter.FriendsListAdapter;
import com.example.popularity.adapter.RateListAdapter;
import com.example.popularity.model.Friend;
import com.example.popularity.model.Rate;
import com.example.popularity.model.User;
import com.example.popularity.model.UserPopularity;
import com.example.popularity.repository.UserFriendsRepository;
import com.example.popularity.R;
import com.example.popularity.utils.ToolbarKind;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HomeFragment extends BaseFragment {


    private RecyclerView favorites_recycler_view,friends_recycler_view;
    private  View view;
    List<Friend> friendList;
    List<Rate> rates=new ArrayList<>();
    private List<Friend> phoneContacts = new ArrayList<>();
    private String id;
    private String name;
    private String phoneNo;
    public static final int REQUEST_READ_CONTACTS = 79;


    @Override
    public void onHiddenChanged(boolean hidden) {
        if(!hidden)
            baseListener.changeToolbar(ToolbarKind.HOME, getString(R.string.home_toolbar_txt));
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        baseListener.changeToolbar(ToolbarKind.HOME, "");
        if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.READ_CONTACTS)
                == PackageManager.PERMISSION_GRANTED) {
            getAllContacts();
        } else {
            requestPermission();
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_home, container, false);
        init();
        Bundle bundle=getArguments();
        User user= (User) bundle.getSerializable("User");
        if(user!=null)
        {
            UserPopularity userPopularity=user.getRates_summary_sum();
            UserFriendsRepository userFriendsRepository=new UserFriendsRepository();
           // friendList=userFriendsRepository.getUserFriendsMock(user.getSocial_primary());
            FriendsListAdapter friendsListAdapter=new FriendsListAdapter(phoneContacts,getActivity());
            friends_recycler_view.setAdapter(friendsListAdapter);
           // List<Friend> finalFriendList = friendList;
            friendsListAdapter.setOnItemClickListener(pos -> {
                Friend friend= phoneContacts.get(pos);
                Bundle bundle1 =new Bundle();
                bundle1.putSerializable("Friend",friend);
                bundle1.putSerializable("User",user);
                baseListener.openFragment(new RateFragment(), true, bundle1);
                Log.i("app_tag","tt");

            });


            rates.add(new Rate("Look",Float.parseFloat(userPopularity.getRate_look())));
            rates.add(new Rate("Fitness",Float.parseFloat(userPopularity.getRate_fitness())));
            rates.add(new Rate("Style",Float.parseFloat(userPopularity.getRate_style())));
            rates.add(new Rate("Personality",Float.parseFloat(userPopularity.getRate_personality())));
            rates.add(new Rate("Trustworthy",Float.parseFloat(userPopularity.getRate_trustworthy())));
            rates.add(new Rate("Popularity",Float.parseFloat(userPopularity.getRate_popularity())));
            RateListAdapter rateListAdapter=new RateListAdapter(rates,getActivity());
            rateListAdapter.setOnItemClickListener(new RateListAdapter.ClickListener() {
                @Override
                public void onItemClick(int position, View v) {
                    Toast.makeText(getContext(),"test",Toast.LENGTH_LONG).show();
                }
            });
            favorites_recycler_view.setAdapter(rateListAdapter);

        }

        return view;
    }

    public void init() {
        baseListener.changeToolbar(ToolbarKind.HOME, getString(R.string.home_toolbar_txt));
        friends_recycler_view=view.findViewById(R.id.friends_recycler_view);
        LinearLayoutManager layoutManager1=new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        friends_recycler_view.setLayoutManager(layoutManager1);
        favorites_recycler_view=view.findViewById(R.id.favorites_recycler_view);
        @SuppressLint("WrongConstant") LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        favorites_recycler_view.setLayoutManager(layoutManager);

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_READ_CONTACTS: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getAllContacts();
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

    private List<Friend> getAllContacts() {
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

                    phoneNo = phoneNo.replace("+98", "0");
                    phoneNo = phoneNo.replace(" ", "");
                    phoneNo = phoneNo.trim();
                    if (id != null && name != null && IsValidMobileNumber(phoneNo)) {
                        phoneContacts.add(new Friend("",name,false,phoneNo,Integer.parseInt(id)));
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
        Pattern pattern = Pattern.compile("^09[0|1|2|3][0-9]{8}$");
        Matcher matcher = pattern.matcher(input);
        if (matcher.matches()) {
            return true;
        } else
            return false;
    }
}
