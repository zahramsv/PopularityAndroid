package com.example.popularity.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.popularity.adapter.FriendsListAdapter;
import com.example.popularity.adapter.RateListAdapter;
import com.example.popularity.model.Friend;
import com.example.popularity.model.Rate;
import com.example.popularity.utils.BaseFragment;
import com.example.popularity.R;

import java.util.ArrayList;
import java.util.List;

public class InstagramPopularityFragment extends BaseFragment {


    private RecyclerView favorites_recycler_view,friends_recycler_view;

    public InstagramPopularityFragment() {
        // Required empty public constructor
    }


    public static InstagramPopularityFragment newInstance() {
        InstagramPopularityFragment fragment = new InstagramPopularityFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_instageam_popularity, container, false);


        friends_recycler_view=view.findViewById(R.id.friends_recycler_view);
        LinearLayoutManager layoutManager1=new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        friends_recycler_view.setLayoutManager(layoutManager1);

        List<Friend> friends=new ArrayList<>();
        friends.add(new Friend("Sara",false));
        friends.add(new Friend("Ali",false));
        friends.add(new Friend("Samane",false));
        friends.add(new Friend("Nava",false));
        friends.add(new Friend("Hani",false));
        friends.add(new Friend("Fereshte",false));
        FriendsListAdapter friendsListAdapter=new FriendsListAdapter(friends,getActivity());
        friends_recycler_view.setAdapter(friendsListAdapter);



        favorites_recycler_view=view.findViewById(R.id.favorites_recycler_view);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        favorites_recycler_view.setLayoutManager(layoutManager);
        List<Rate> rates=new ArrayList<>();
        rates.add(new Rate("Personality",3));
        rates.add(new Rate("Beauty",2));
        rates.add(new Rate("Style",5));
        rates.add(new Rate("Good Content!",3));
        RateListAdapter rateListAdapter=new RateListAdapter(rates,getActivity());
        favorites_recycler_view.setAdapter(rateListAdapter);


        return view;
    }



}
