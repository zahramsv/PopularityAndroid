package com.example.popularity.fragment;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.popularity.R;
import com.example.popularity.utils.ToolbarState;

public class AboutUsFragment extends BaseFragment {


    private ToolbarState toolbarState;

    public AboutUsFragment() {
        // Required empty public constructor
    }

    public static AboutUsFragment newInstance() {
        AboutUsFragment fragment = new AboutUsFragment();
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
        // Inflate the layout for this fragment

        toolbarState=(ToolbarState)getContext();
        toolbarState.toolbarState(true,getResources().getString(R.string.about_us_toolbar_txt));
        return inflater.inflate(R.layout.fragment_about_us, container, false);
    }

}
