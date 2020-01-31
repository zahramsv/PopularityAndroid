package com.example.popularity.presenter;

import com.example.popularity.fragment.HomeFragment;
import com.example.popularity.model.Login;
import com.example.popularity.model.User;
import com.example.popularity.model.UserPopularity;
import com.example.popularity.model.repository.UserRepository;
import com.example.popularity.mvp.LoginFragmentMvp;
import com.example.popularity.utils.LoginKind;
import com.example.popularity.utils.SavePref;
import com.example.popularity.utils.ShowMessageType;

public class LoginPresenter implements LoginFragmentMvp.Presenter, UserRepository.UserRepoListener {

    private LoginFragmentMvp.View view;


    public LoginPresenter(LoginFragmentMvp.View view) {
        this.view = view;
    }

    private LoginKind loginKind;

    @Override
    public void setLoginKind(LoginKind kind) {
        this.loginKind = kind;
        view.setLoginKind(kind);
    }

    @Override
    public void loginUser() {
        if (loginKind == LoginKind.MOCK) {
            UserRepository userRepository = new UserRepository();
            userRepository.loginToServer(new Login().getMockData(), this);
        }
    }

    @Override
    public void onDone(User user) {
        view.showLoadingBar(false);
        UserPopularity userPopularity = user.getRates_summary_sum();
        SavePref savePref = new SavePref();
        user.setSocial_primary(new Login().getMockData().getSocial_primary());
        savePref.SaveUser(view.getViewContext(), user, userPopularity);
        view.setMainUser(user);
        view.openFragment(new HomeFragment(), true, null);
    }

    @Override
    public void onFailure(String message) {
        view.showMessage(ShowMessageType.SNACK, message);
    }
}
