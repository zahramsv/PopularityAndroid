package com.example.popularity.fragment;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.popularity.R;
import com.example.popularity.utils.ToolbarKind;


public class PrivacyPolicyFragment extends BaseFragment {


    @Override
    public void onHiddenChanged(boolean hidden) {
        if (!hidden)
            baseListener.changeToolbar(ToolbarKind.BACK, getString(R.string.privacy_policy_toolbar_txt));
    }

    public static PrivacyPolicyFragment newInstance() {
        PrivacyPolicyFragment fragment = new PrivacyPolicyFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        baseListener.changeToolbar(ToolbarKind.BACK, getString(R.string.privacy_policy_toolbar_txt));
        View view = inflater.inflate(R.layout.fragment_privacy_policy_fregment, container, false);
        return view;
    }

}
