package com.example.popularity.presenter;

import com.example.popularity.fragment.HomeFragment;
import com.example.popularity.model.Login;
import com.example.popularity.model.User;
import com.example.popularity.model.repository.LoginHandler;
import com.example.popularity.model.repository.UserRepository;
import com.example.popularity.mvp.RegisterMvp;
import com.example.popularity.myInterface.MainActivityTransaction;
import com.example.popularity.utils.MyApp;
import com.example.popularity.utils.ShowMessageType;

public class RegisterPresenter implements
        RegisterMvp.Presenter
    , UserRepository.UserRepoListener
{

    private RegisterMvp.View view;
    private MainActivityTransaction.Components baseComponent;

    private UserRepository userRepository;
    private LoginHandler loginHandler;

    private String userSocialPrimary;

    public RegisterPresenter(RegisterMvp.View view, MainActivityTransaction.Components baseComponent) {
        this.view = view;
        this.baseComponent = baseComponent;

        this.loginHandler = MyApp.getInstance().getBaseComponent().provideLoginHandler();
        this.userRepository = MyApp.getInstance().getBaseComponent().provideUserRepository();
    }

    @Override
    public Login getLoginInfo(String fullname, String username) {
        Login loginInfo = new Login();
        //fixme : social type must get from current chosen login type
        loginInfo.setSocial_type(0);
        loginInfo.setUsername(username);
        loginInfo.setFull_name(fullname);
        loginInfo.setSocial_primary(userSocialPrimary);
        loginInfo.setAvatar_url("test.jpg");
        return loginInfo;
    }

    @Override
    public void setUserSocialPrimary(String userSocialPrimary) {
        this.userSocialPrimary = userSocialPrimary;
    }

    @Override
    public void loginToServer(Login user) {
        baseComponent.showLoadingBar(true);
        userRepository.loginToServer(user, this);
    }

    @Override
    public void onLoginDone(User user) {
        baseComponent.showLoadingBar(false);
        user.setSocial_primary(userSocialPrimary);
        loginHandler.saveLoginInfo(user, user.getRates_summary_sum());
        baseComponent.openFragment(new HomeFragment(), true, null);
    }

    @Override
    public void onLoginFailure(String message) {
        baseComponent.showLoadingBar(false);
        baseComponent.showMessage(ShowMessageType.TOAST, message);
    }

}
