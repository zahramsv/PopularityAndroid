package com.example.popularity.fragment;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.popularity.R;
import com.example.popularity.utils.ToolbarState;


public class PrivacyPolicyFragment extends BaseFragment {

    private ToolbarState toolbarState;
    public PrivacyPolicyFragment() {

    }

    public static PrivacyPolicyFragment newInstance() {
        PrivacyPolicyFragment fragment = new PrivacyPolicyFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        toolbarState=(ToolbarState)getContext();
        toolbarState.toolbarState(true,getResources().getString(R.string.privacy_policy_toolbar_txt));
        return inflater.inflate(R.layout.fragment_privacy_policy_fregment, container, false);
    }



}
