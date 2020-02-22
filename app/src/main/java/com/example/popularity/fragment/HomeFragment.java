package com.example.popularity.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.popularity.R;
import com.example.popularity.adapter.FriendsListAdapter;
import com.example.popularity.adapter.RateListAdapter;
import com.example.popularity.mvp.HomeMvp;
import com.example.popularity.presenter.HomePresenter;
import com.example.popularity.model.Friend;
import com.example.popularity.model.Rate;
import com.example.popularity.model.User;
import com.example.popularity.model.UserPopularity;
import com.example.popularity.utils.ToolBarIconKind;
import com.example.popularity.utils.ToolbarKind;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import static com.example.popularity.utils.Configs.BUNDLE_FRIEND;

public class HomeFragment extends BaseFragment implements
        HomeMvp.View {

    private RecyclerView favoritesRecyclerView, friendsRecyclerView;
    private List<Rate> rates = new ArrayList<>();
    private List<Friend> friendsList = new ArrayList<>();
    private HomePresenter homePresenter;
    private FloatingActionButton btnShare;
    private FriendsListAdapter friendsListAdapter;


    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if (!hidden)
            baseListener.changeToolbar(ToolbarKind.HOME, getString(R.string.app_name));
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homePresenter = new HomePresenter(this, getContext(), baseListener);

        baseListener.changeToolbar(ToolbarKind.HOME, "");
        homePresenter.provideFriends();

        homePresenter.getObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(getObserver());

        //friendsList = homePresenter.getFriends(getContext());


    }

    private Observer<List<Friend>> getObserver() {
        return new Observer<List<Friend>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull List<Friend> friends) {
                friendsList = friends;
                friendsListAdapter.addAllItems(friendsList);
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_home, container, false);
        init(view);
        User user = homePresenter.getUser();
        if (user != null) {
            UserPopularity userPopularity = user.getRates_summary_sum();
            friendsListAdapter = new FriendsListAdapter(friendsList, getActivity());
            friendsRecyclerView.setAdapter(friendsListAdapter);
            // List<Friend> finalFriendList = friendList;
            friendsListAdapter.setOnItemClickListener(pos -> {
                Friend friend = friendsList.get(pos);
                Bundle bundle1 = new Bundle();
                bundle1.putSerializable(BUNDLE_FRIEND, friend);
                baseListener.openFragment(new RateFragment(), true, bundle1);
            });


            rates.add(new Rate("Look", Integer.parseInt(userPopularity.getRate_look())));
            rates.add(new Rate("Fitness", Integer.parseInt(userPopularity.getRate_fitness())));
            rates.add(new Rate("Style", Integer.parseInt(userPopularity.getRate_style())));
            rates.add(new Rate("Personality", Integer.parseInt(userPopularity.getRate_personality())));
            rates.add(new Rate("Trustworthy", Integer.parseInt(userPopularity.getRate_trustworthy())));
            rates.add(new Rate("Popularity", Integer.parseInt(userPopularity.getRate_popularity())));
            RateListAdapter rateListAdapter = new RateListAdapter(rates, getActivity());
            rateListAdapter.setOnItemClickListener(new RateListAdapter.ClickListener() {
                @Override
                public void onItemClick(int position, View v) {
                    Toast.makeText(getContext(), "test", Toast.LENGTH_LONG).show();
                }
            });
            favoritesRecyclerView.setAdapter(rateListAdapter);

        }

        btnShare.setOnClickListener(view1 -> {
            View view2 = getActivity().getWindow().getDecorView().getRootView().findViewById(R.id.cardUserRates);
            homePresenter.takeScreenShot(view2);
        });
        return view;
    }

    public void init(View view) {

        btnShare = view.findViewById(R.id.btnShare);
        baseListener.changeToolbar(ToolbarKind.HOME, getString(R.string.app_name));
        baseListener.showToolbarIcon(ToolBarIconKind.VISIBLEL);
        friendsRecyclerView = view.findViewById(R.id.rvFriends);
        GridLayoutManager layoutManager1 = new GridLayoutManager(getActivity(), 2, RecyclerView.HORIZONTAL, false);
        friendsRecyclerView.setLayoutManager(layoutManager1);
        favoritesRecyclerView = view.findViewById(R.id.rvFavorites);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        favoritesRecyclerView.setLayoutManager(layoutManager);


    }

    @Override
    public void ShareScreenShot(Uri uri) {

        if (uri != null) {
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); // temp permission for receiving app to read this file
            shareIntent.setDataAndType(uri, getActivity().getContentResolver().getType(uri));
            shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
            startActivity(Intent.createChooser(shareIntent, "Choose an app"));
        }


    }

    @Override
    public Context getViewContext() {
        return getContext();
    }

    @Override
    public void setFriendsList(List<Friend> friends) {
        friendsListAdapter.addAllItems(friends);
    }
}
