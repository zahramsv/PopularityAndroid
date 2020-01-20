package com.example.popularity.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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

public class HomeFragment extends BaseFragment {


    private RecyclerView favorites_recycler_view,friends_recycler_view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if(!hidden)
            baseListener.changeToolbar(ToolbarKind.HOME, getString(R.string.home_toolbar_txt));
    }

    public static HomeFragment newInstance()
    {
        HomeFragment homeFragment=new HomeFragment();
        return homeFragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle bundle=getArguments();
        User user= (User) bundle.getSerializable("User");
        UserPopularity userPopularity=user.getRates_summary_sum();

        baseListener.changeToolbar(ToolbarKind.HOME, getString(R.string.home_toolbar_txt));

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        friends_recycler_view=view.findViewById(R.id.friends_recycler_view);
        LinearLayoutManager layoutManager1=new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        friends_recycler_view.setLayoutManager(layoutManager1);


        //for test
        UserFriendsRepository userFriendsRepository=new UserFriendsRepository();
        List<Friend> friendList=userFriendsRepository.getUserFriendsMock(Integer.parseInt(user.getSocial_primary()));

        FriendsListAdapter friendsListAdapter=new FriendsListAdapter(friendList,getActivity());
        friends_recycler_view.setAdapter(friendsListAdapter);

       friendsListAdapter.setOnItemClickListener(pos -> {

           Friend friend= friendList.get(pos);
           Bundle bundle1 =new Bundle();
           bundle1.putSerializable("Friend",friend);
           bundle1.putSerializable("User",user);

           baseListener.openFragment(new RateFragment(), true, bundle1);
           Log.i("app_tag","tt");

       });
        //Vertical
        favorites_recycler_view=view.findViewById(R.id.favorites_recycler_view);
        @SuppressLint("WrongConstant") LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        favorites_recycler_view.setLayoutManager(layoutManager);
        List<Rate> rates=new ArrayList<>();

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

        return view;
    }

}
