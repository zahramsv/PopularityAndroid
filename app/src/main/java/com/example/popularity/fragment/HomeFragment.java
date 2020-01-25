package com.example.popularity.fragment;

import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Bundle;
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

import com.example.popularity.R;
import com.example.popularity.adapter.FriendsListAdapter;
import com.example.popularity.adapter.RateListAdapter;
import com.example.popularity.logic.HomePresenter;
import com.example.popularity.model.Friend;
import com.example.popularity.model.Rate;
import com.example.popularity.model.User;
import com.example.popularity.model.UserPopularity;
import com.example.popularity.repository.UserFriendsRepository;
import com.example.popularity.utils.ToolbarKind;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends BaseFragment {


    private RecyclerView favorites_recycler_view, friends_recycler_view;
    List<Rate> rates = new ArrayList<>();
    private List<Friend> phoneContacts = new ArrayList<>();
    private HomePresenter homePresenter;
    public static final int REQUEST_READ_CONTACTS = 79;


    @Override
    public void onHiddenChanged(boolean hidden) {
        if (!hidden)
            baseListener.changeToolbar(ToolbarKind.HOME, getString(R.string.home_toolbar_txt));
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        homePresenter = new HomePresenter(getContext());
        //phoneContacts = homePresenter.getContact();
        baseListener.changeToolbar(ToolbarKind.HOME, "");
        if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.READ_CONTACTS)
                == PackageManager.PERMISSION_GRANTED) {
            homePresenter.getFriends();
        } else {
            requestPermission();
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        init(view);
        User user = baseListener.getMainUser();
        if (user != null) {
            UserPopularity userPopularity = user.getRates_summary_sum();
            UserFriendsRepository userFriendsRepository = new UserFriendsRepository();
            // friendList=userFriendsRepository.getUserFriendsMock(user.getSocial_primary());
            FriendsListAdapter friendsListAdapter = new FriendsListAdapter(phoneContacts, getActivity());
            friends_recycler_view.setAdapter(friendsListAdapter);
            // List<Friend> finalFriendList = friendList;
            friendsListAdapter.setOnItemClickListener(pos -> {
                Friend friend = phoneContacts.get(pos);
                Bundle bundle1 = new Bundle();
                bundle1.putSerializable("Friend", friend);
                bundle1.putSerializable("User", user);
                baseListener.openFragment(new RateFragment(), true, bundle1);
                Log.i("app_tag", "tt");

            });


            rates.add(new Rate("Look", Float.parseFloat(userPopularity.getRate_look())));
            rates.add(new Rate("Fitness", Float.parseFloat(userPopularity.getRate_fitness())));
            rates.add(new Rate("Style", Float.parseFloat(userPopularity.getRate_style())));
            rates.add(new Rate("Personality", Float.parseFloat(userPopularity.getRate_personality())));
            rates.add(new Rate("Trustworthy", Float.parseFloat(userPopularity.getRate_trustworthy())));
            rates.add(new Rate("Popularity", Float.parseFloat(userPopularity.getRate_popularity())));
            RateListAdapter rateListAdapter = new RateListAdapter(rates, getActivity());
            rateListAdapter.setOnItemClickListener(new RateListAdapter.ClickListener() {
                @Override
                public void onItemClick(int position, View v) {
                    Toast.makeText(getContext(), "test", Toast.LENGTH_LONG).show();
                }
            });
            favorites_recycler_view.setAdapter(rateListAdapter);

        }

        return view;
    }

    public void init(View view) {

        baseListener.changeToolbar(ToolbarKind.HOME, getString(R.string.home_toolbar_txt));
        friends_recycler_view = view.findViewById(R.id.friends_recycler_view);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        friends_recycler_view.setLayoutManager(layoutManager1);
        favorites_recycler_view = view.findViewById(R.id.favorites_recycler_view);
        @SuppressLint("WrongConstant") LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        favorites_recycler_view.setLayoutManager(layoutManager);


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_READ_CONTACTS: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    homePresenter.getFriends();
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


}
