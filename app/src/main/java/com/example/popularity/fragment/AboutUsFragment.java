package com.example.popularity.fragment;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.popularity.R;
import com.example.popularity.utils.ToolbarKind;

public class AboutUsFragment extends BaseFragment {



    public static AboutUsFragment newInstance() {
        AboutUsFragment fragment = new AboutUsFragment();
        return fragment;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if(!hidden)
            baseListener.changeToolbar(ToolbarKind.BACK, getString(R.string.about_us_toolbar_txt));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        baseListener.changeToolbar(ToolbarKind.BACK, getString(R.string.about_us_toolbar_txt));
        return inflater.inflate(R.layout.fragment_about_us, container, false);
    }

}
