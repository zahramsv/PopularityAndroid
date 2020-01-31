package com.example.popularity.presenter;

import com.example.popularity.fragment.HomeFragment;
import com.example.popularity.model.Login;
import com.example.popularity.model.User;
import com.example.popularity.model.UserPopularity;
import com.example.popularity.model.repository.UserRepository;
import com.example.popularity.mvp.LoginFragmentMvp;
import com.example.popularity.myInterface.MainActivityTransaction;
import com.example.popularity.utils.LoginKind;
import com.example.popularity.utils.SavePref;
import com.example.popularity.utils.ShowMessageType;

public class LoginPresenter implements LoginFragmentMvp.Presenter, UserRepository.UserRepoListener {

    private LoginFragmentMvp.View view;
    private MainActivityTransaction.Components baseComponent;

    public LoginPresenter(LoginFragmentMvp.View view, MainActivityTransaction.Components baseComponent) {
        this.view = view;
        this.baseComponent = baseComponent;
    }

    @Override
    public void setLoginKind(LoginKind kind) {
        baseComponent.setLoginKind(kind);
    }

    @Override
    public void loginUser() {
        if (baseComponent.getLoginKind() == LoginKind.MOCK) {
            UserRepository userRepository = new UserRepository();
            userRepository.loginToServer(new Login().getMockData(), this);
        }
    }

    @Override
    public void onDone(User user) {
        baseComponent.showLoadingBar(false);
        UserPopularity userPopularity = user.getRates_summary_sum();
        SavePref savePref = new SavePref();
        user.setSocial_primary(new Login().getMockData().getSocial_primary());
        savePref.SaveUser(view.getViewContext(), user, userPopularity);
        baseComponent.setMainUser(user);
        baseComponent.openFragment(new HomeFragment(), true, null);
    }

    @Override
    public void onFailure(String message) {
        baseComponent.showMessage(ShowMessageType.SNACK, message);
    }
}
