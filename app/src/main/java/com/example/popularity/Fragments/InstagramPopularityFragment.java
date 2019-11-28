package com.example.popularity.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.popularity.Help.BaseFragment;
import com.example.popularity.R;

public class InstagramPopularityFragment extends BaseFragment {


    public InstagramPopularityFragment() {
        // Required empty public constructor
    }



    // TODO: Rename and change types and number of parameters
    public static InstagramPopularityFragment newInstance(String param1, String param2) {
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
        return inflater.inflate(R.layout.fragment_instageam_popularity, container, false);
    }

    public boolean onBackPressed() {
        return false;
    }

}
