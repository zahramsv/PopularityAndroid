package com.example.popularity.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;

import com.example.popularity.R;
import com.example.popularity.model.Login;
import com.example.popularity.model.User;
import com.example.popularity.model.UserPopularity;
import com.example.popularity.repository.UserRepository;
import com.example.popularity.utils.LoginKind;
import com.example.popularity.utils.SavePref;
import com.example.popularity.utils.ShowMessageType;
import com.example.popularity.utils.ToolbarKind;

public class LoginFragment extends BaseFragment
        implements UserRepository.UserRepoListener {


    private Button loginWithPhoneNumber, loginWithMockData;
    private View view;

    @Override
    public void onHiddenChanged(boolean hidden) {
        if (!hidden)
            baseListener.changeToolbar(ToolbarKind.HOME, getString(R.string.login_toolbar_txt));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        baseListener.changeToolbar(ToolbarKind.HOME, getString(R.string.login_toolbar_txt));
        view = inflater.inflate(R.layout.fragment_login, container, false);
        init(view);

        loginWithPhoneNumber.setOnClickListener(view -> {
            baseListener.setLoginKind(LoginKind.SMS);
            baseListener.openFragment(new MobileLoginFragment(), true, null);
        });
        loginWithMockData.setOnClickListener(view -> {
            baseListener.setLoginKind(LoginKind.MOCK);
            baseListener.showLoadingBar(true);
            loginToServer();
        });


        return view;

    }


    public void init(View view) {
        loginWithMockData = view.findViewById(R.id.btnLoginWihMockData);
        loginWithPhoneNumber = view.findViewById(R.id.btnLoginWithMobile);
    }

    private void loginToServer() {
        UserRepository userRepository = new UserRepository();
        userRepository.loginToServer(new Login().getMockData(), this);
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
}
