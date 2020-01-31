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
import com.example.popularity.logic.LoginPresenter;
import com.example.popularity.model.Login;
import com.example.popularity.model.User;
import com.example.popularity.model.UserPopularity;
import com.example.popularity.mvp.LoginFragmentMvp;
import com.example.popularity.repository.UserRepository;
import com.example.popularity.utils.LoginKind;
import com.example.popularity.utils.SavePref;
import com.example.popularity.utils.ShowMessageType;
import com.example.popularity.utils.ToolbarKind;

public class LoginFragment extends BaseFragment
        implements UserRepository.UserRepoListener, LoginFragmentMvp.View {


    private Button loginWithPhoneNumber, loginWithMockData;
    private View view;
    private LoginFragmentMvp.Presenter presenter;

    @Override
    public void onHiddenChanged(boolean hidden) {
        if (!hidden)
            baseListener.changeToolbar(ToolbarKind.HOME, getString(R.string.login_toolbar_txt));

    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter=new LoginPresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        changeToolbar(ToolbarKind.HOME,getString(R.string.login_toolbar_txt));
        view = inflater.inflate(R.layout.fragment_login, container, false);
        init(view);

        loginWithPhoneNumber.setOnClickListener(view -> {
            setLoginKind(LoginKind.SMS);
            openFragment(new MobileLoginFragment(), true, null);
        });
        loginWithMockData.setOnClickListener(view -> {
            setLoginKind(LoginKind.MOCK);
            showLoadingBar(true);
            presenter.loginToServer();
        });


        return view;

    }


    public void init(View view) {
        loginWithMockData = view.findViewById(R.id.btnLoginWihMockData);
        loginWithPhoneNumber = view.findViewById(R.id.btnLoginWithMobile);
    }



    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        return super.onCreateAnimation(transit, enter, nextAnim);
    }

    @Override
    public void onDone(User user) {
        baseListener.showLoadingBar(false);
        UserPopularity userPopularity = user.getRates_summary_sum();
        SavePref savePref = new SavePref();
        user.setSocial_primary(new Login().getMockData().getSocial_primary());
        savePref.SaveUser(getContext(), user, userPopularity);

        baseListener.setMainUser(user);
        baseListener.openFragment(new HomeFragment(), true, null);
    }

    @Override
    public void onFailure(String message) {
        baseListener.showMessage(ShowMessageType.SNACK, message);
    }

    @Override
    public void openFragment(BaseFragment fragment, Boolean addStack, Bundle bundle) {

        baseListener.openFragment(fragment,addStack,bundle);
    }

    @Override
    public void changeToolbar(ToolbarKind kind, String title) {

        baseListener.changeToolbar(kind,title);
    }

    @Override
    public void showLoadingBar(boolean isShow) {
        baseListener.showLoadingBar(isShow);
    }

    @Override
    public void showMessage(ShowMessageType messageType, String message) {
        baseListener.showMessage(messageType,message);
    }

    @Override
    public Context getViewContext() {
        return getContext();
    }

    @Override
    public void setMainUser(User user) {
        baseListener.setMainUser(user);
    }

    @Override
    public void setLoginKind(LoginKind kind) {
        baseListener.setLoginKind(kind);
    }
}
