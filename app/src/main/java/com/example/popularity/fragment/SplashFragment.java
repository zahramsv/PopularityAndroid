package com.example.popularity.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.popularity.R;
import com.example.popularity.logic.SplashPresenter;
import com.example.popularity.model.User;
import com.example.popularity.mvp.SplashMvp;
import com.example.popularity.utils.ToolbarKind;

public class SplashFragment extends BaseFragment implements SplashMvp.View {

    private SplashMvp.Presenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = new SplashPresenter(this);
        presenter.getUserInfoAfterWait();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        baseListener.changeToolbar(ToolbarKind.EMPTY, "");
        return inflater.inflate(R.layout.fragment_splash, container, false);
    }

    @Override
    public Context getViewContext() {
        return getActivity();
    }

    @Override
    public void setUserData(User user) {
        baseListener.setMainUser(user);
    }

    @Override
    public void openFragment(BaseFragment fragment, Boolean addStack, Bundle bundle) {
        baseListener.openFragment(fragment, addStack, bundle);
    }

}
