package com.example.popularity.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;

import androidx.annotation.Nullable;

import com.example.popularity.R;
import com.example.popularity.model.repository.LoginHandler;
import com.example.popularity.model.repository.SharedPrefsRepository;
import com.example.popularity.mvp.LoginFragmentMvp;
import com.example.popularity.presenter.LoginPresenter;
import com.example.popularity.utils.LoginKind;
import com.example.popularity.utils.ToolBarIconKind;
import com.example.popularity.utils.ToolbarKind;

public class LoginFragment extends BaseFragment implements
        LoginFragmentMvp.View
{


    private Button loginWithPhoneNumber, loginWithMockData;
    private View view;
    private LoginFragmentMvp.Presenter presenter;


    public static LoginFragment newInstance()
    {
        LoginFragment loginFragment=new LoginFragment();
        return loginFragment;
    }
    @Override
    public void onHiddenChanged(boolean hidden) {
        if (!hidden)
            baseListener.changeToolbar(ToolbarKind.HOME, getString(R.string.empty));
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new LoginPresenter(this, baseListener);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        view = inflater.inflate(R.layout.fragment_login, container, false);
        init(view);

        loginWithPhoneNumber.setOnClickListener(view -> {
            presenter.setLoginKind(LoginKind.SMS);
            baseListener.openFragment(new MobileLoginFragment(), true, null);

            //SharedPrefsRepository sharedPrefsRepository=getViewContext().getSharedPreferences();

        });
        loginWithMockData.setOnClickListener(view -> {
            presenter.setLoginKind(LoginKind.MOCK);
            baseListener.showLoadingBar(true);
            presenter.loginUser();
        });


        return view;

    }


    private void init(View view) {
        baseListener.changeToolbar(ToolbarKind.HOME, getString(R.string.empty));
        baseListener.showToolbarIcon(ToolBarIconKind.INVISIBLE);
        loginWithMockData = view.findViewById(R.id.btnLoginWihMockData);
        loginWithPhoneNumber = view.findViewById(R.id.btnLoginWithMobile);
    }


    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        return super.onCreateAnimation(transit, enter, nextAnim);
    }

    @Override
    public Context getViewContext() {
        return getContext();
    }

}
