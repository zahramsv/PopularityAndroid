package com.example.popularity.presenter;

import com.example.popularity.mvp.AboutUsMvp;
import com.example.popularity.myInterface.MainActivityTransaction;

public class AboutUsPresenter implements AboutUsMvp.Presenter {

    private AboutUsMvp.View view;
    private MainActivityTransaction.Components baseComponents;
    public AboutUsPresenter(AboutUsMvp.View view, MainActivityTransaction.Components baseComponents) {
        this.view = view;
        this.baseComponents = baseComponents;
    }
}
