package com.example.popularity.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
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
import com.example.popularity.model.repository.UserRepository;
import com.example.popularity.presenter.HomePresenter;
import com.example.popularity.model.Friend;
import com.example.popularity.model.Rate;
import com.example.popularity.model.User;
import com.example.popularity.model.UserPopularity;
import com.example.popularity.utils.LoginKind;
import com.example.popularity.utils.MyApp;
import com.example.popularity.utils.ToolbarKind;
import java.util.ArrayList;
import java.util.List;

import static com.example.popularity.utils.Configs.BUNDLE_FRIEND;
import static com.example.popularity.utils.Configs.REQUEST_READ_CONTACTS;

public class HomeFragment extends BaseFragment {

    private RecyclerView favoritesRecyclerView, friendsRecyclerView;
    private List<Rate> rates = new ArrayList<>();
    private List<Friend> friendsList = new ArrayList<>();
    private HomePresenter homePresenter;
    private UserRepository userRepository;

    @Override
    public void onHiddenChanged(boolean hidden) {
        if (!hidden)
            baseListener.changeToolbar(ToolbarKind.HOME, getString(R.string.home_toolbar_txt));
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homePresenter = new HomePresenter(getContext());

        userRepository = MyApp.getInstance().getBaseComponent().provideUserRepository();

        baseListener.changeToolbar(ToolbarKind.HOME, "");
        if(baseListener.getLoginKind()== LoginKind.SMS)
        {
            if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
                friendsList= homePresenter.getFriends(LoginKind.SMS, "");
            } else {
                homePresenter.requestPermission(getActivity());
            }
        }
        if (baseListener.getLoginKind()==LoginKind.MOCK)
        {
            friendsList = homePresenter.getFriends(LoginKind.MOCK, userRepository.getCurrentUser().getSocial_primary());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        init(view);
        User user = userRepository.getCurrentUser();
        if (user != null) {
            UserPopularity userPopularity = user.getRates_summary_sum();
            FriendsListAdapter friendsListAdapter = new FriendsListAdapter(friendsList, getActivity());
            friendsRecyclerView.setAdapter(friendsListAdapter);
            // List<Friend> finalFriendList = friendList;
            friendsListAdapter.setOnItemClickListener(pos -> {
                Friend friend = friendsList.get(pos);
                Bundle bundle1 = new Bundle();
                bundle1.putSerializable(BUNDLE_FRIEND, friend);
                baseListener.openFragment(new RateFragment(), true, bundle1);
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
            favoritesRecyclerView.setAdapter(rateListAdapter);

        }

        return view;
    }

    public void init(View view) {

        baseListener.changeToolbar(ToolbarKind.HOME, getString(R.string.home_toolbar_txt));
        friendsRecyclerView = view.findViewById(R.id.rvFriends);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        friendsRecyclerView.setLayoutManager(layoutManager1);
        favoritesRecyclerView = view.findViewById(R.id.rvFavorites);
        @SuppressLint("WrongConstant") LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        favoritesRecyclerView.setLayoutManager(layoutManager);


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_READ_CONTACTS: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    friendsList = homePresenter.getFriends(LoginKind.SMS, "");
                } else {
                    // permission denied,Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
        }
    }




}
