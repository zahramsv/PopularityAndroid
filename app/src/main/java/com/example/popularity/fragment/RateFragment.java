package com.example.popularity.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.popularity.R;
import com.example.popularity.model.Friend;
import com.example.popularity.model.RateSendDataModel;
import com.example.popularity.model.SubmitRate;
import com.example.popularity.model.User;
import com.example.popularity.myInterface.FriendsRate;
import com.example.popularity.myInterface.GetLoginDataService;
import com.example.popularity.utils.RetrofitInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class RateFragment extends Fragment {

    private TextView userName;
    private Button save_btn;
    public RateFragment() {
        // Required empty public constructor
    }

    public static RateFragment newInstance(String param1, String param2) {
        RateFragment fragment = new RateFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle bundle=getArguments();
        Friend friend= (Friend) bundle.getSerializable("Friend");
        User user= (User) bundle.getSerializable("User");



        View view= inflater.inflate(R.layout.fragment_rate, container, false);
        AppCompatRatingBar look=view.findViewById(R.id.look_rate);
        AppCompatRatingBar style=view.findViewById(R.id.style_rate);
        AppCompatRatingBar popularity=view.findViewById(R.id.popularity_rate);
        AppCompatRatingBar fitness=view.findViewById(R.id.fitness_rate);
        AppCompatRatingBar trustworthy=view.findViewById(R.id.trustworthy_rate);
        AppCompatRatingBar personality=view.findViewById(R.id.personality_rate);


        int social_primary=Integer.parseInt(user.getSocial_primary());

        SubmitRate submitRate=new SubmitRate(user.getToken(), social_primary,friend.getId()
               ,friend.getName(),friend.getName(),
                user.getAvatar_url(),user.getSocial_type(),look.getRating(),
                fitness.getRating(),style.getRating(),personality.getRating(),trustworthy.getRating(),
                popularity.getRating());

        userName= view.findViewById(R.id.username);
        userName.setText(friend.getName() + " - "+friend.getUserId()+" - "+user.getFull_name());
        view.findViewById(R.id.save_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                RetrofitInstance retrofitInstance=new RetrofitInstance();
                Retrofit retrofit=retrofitInstance.getRetrofitInstance();
                FriendsRate friendsRate=retrofit.create(FriendsRate.class);
                friendsRate.SubmitRateToFriend(submitRate).enqueue(new Callback<Integer>() {
                    @Override
                    public void onResponse(Call<Integer> call, Response<Integer> response) {

                    }

                    @Override
                    public void onFailure(Call<Integer> call, Throwable t) {

                    }
                });


            }
        });
        return view;
    }



}
