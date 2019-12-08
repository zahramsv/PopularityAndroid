package com.example.popularity.fragment;

import android.content.Context;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.popularity.adapter.FriendsListAdapter;
import com.example.popularity.adapter.RateListAdapter;
import com.example.popularity.model.Friend;
import com.example.popularity.model.Rate;
import com.example.popularity.utils.BaseFragment;
import com.example.popularity.R;
import com.example.popularity.utils.ToolbarState;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends BaseFragment {


    private ToolbarState toolbarState;
    private RecyclerView favorites_recycler_view,friends_recycler_view;

    public HomeFragment() {
        // Required empty public constructor
    }


    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        toolbarState.toolbarState(true);
        View view = inflater.inflate(R.layout.fragment_instageam_popularity, container, false);
        friends_recycler_view=view.findViewById(R.id.friends_recycler_view);
        LinearLayoutManager layoutManager1=new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        friends_recycler_view.setLayoutManager(layoutManager1);


        //for test
        List<Friend> friends=new ArrayList<>();
        friends.add(new Friend("Sara",false));
        friends.add(new Friend("Ali",false));
        friends.add(new Friend("Samane",false));
        friends.add(new Friend("Nava",false));
        friends.add(new Friend("Hani",false));
        friends.add(new Friend("Fereshte",false));
        FriendsListAdapter friendsListAdapter=new FriendsListAdapter(friends,getActivity());
        friends_recycler_view.setAdapter(friendsListAdapter);


        //Vertical
        favorites_recycler_view=view.findViewById(R.id.favorites_recycler_view);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        favorites_recycler_view.setLayoutManager(layoutManager);
        List<Rate> rates=new ArrayList<>();
        rates.add(new Rate("Personality",3));
        rates.add(new Rate("Beauty",2));
        rates.add(new Rate("Style",5));
        rates.add(new Rate("Good Content!",3));
        rates.add(new Rate("Empathetic",3));
        rates.add(new Rate("Intuitive",5));
        rates.add(new Rate("Creative",4));
        RateListAdapter rateListAdapter=new RateListAdapter(rates,getActivity());
        favorites_recycler_view.setAdapter(rateListAdapter);

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof MenuDrawerFragment.OnSlidingMenuFragmentListener) {
            toolbarState = (ToolbarState) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnAccountingMainFragmentInteractionListener");
        }
    }
}
