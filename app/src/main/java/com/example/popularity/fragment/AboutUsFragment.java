package com.example.popularity.fragment;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.example.popularity.R;
import com.example.popularity.logic.AboutUsPresenter;
import com.example.popularity.mvp.AboutUsMvp;
import com.example.popularity.utils.ToolbarKind;

public class AboutUsFragment extends BaseFragment implements AboutUsMvp.View {


    private View view;
    private AboutUsMvp.Presenter presenter;



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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter=new AboutUsPresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        changeToolbar(ToolbarKind.BACK,getString(R.string.about_us_toolbar_txt));
        view= inflater.inflate(R.layout.fragment_about_us, container, false);
        return view;
    }

    @Override
    public void changeToolbar(ToolbarKind kind, String title) {
        baseListener.changeToolbar(kind,title);
    }

    @Override
    public void setDescriptionAboutUs(String desc) {

    }
}
