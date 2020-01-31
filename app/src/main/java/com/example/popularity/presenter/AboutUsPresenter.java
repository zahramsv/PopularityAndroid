package com.example.popularity.presenter;

import com.example.popularity.mvp.AboutUsMvp;

public class AboutUsPresenter implements AboutUsMvp.Presenter {

    private AboutUsMvp.View view;

    public AboutUsPresenter(AboutUsMvp.View view) {
        this.view = view;
    }
}
