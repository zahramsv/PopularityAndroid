package com.example.popularity.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.popularity.Utils.BaseFragment;
import com.example.popularity.R;

public class InstagramPopularityFragment extends BaseFragment {


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
        return view;
    }



}
