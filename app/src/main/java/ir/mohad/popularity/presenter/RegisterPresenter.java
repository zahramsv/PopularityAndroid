package ir.mohad.popularity.presenter;

import ir.mohad.popularity.fragment.HomeFragment;
import ir.mohad.popularity.model.Login;
import ir.mohad.popularity.model.User;
import ir.mohad.popularity.model.repository.LoginHandler;
import ir.mohad.popularity.model.repository.UserRepository;
import ir.mohad.popularity.mvp.RegisterMvp;
import ir.mohad.popularity.myInterface.MainActivityTransaction;
import ir.mohad.popularity.utils.MyApp;
import ir.mohad.popularity.utils.ShowMessageType;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterPresenter implements
        RegisterMvp.Presenter
        , UserRepository.UserRepoListener {

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
        baseComponent.openFragment(new HomeFragment(), false, null);
    }

    @Override
    public void onLoginFailure(String message) {
        baseComponent.showLoadingBar(false);
        baseComponent.showMessage(ShowMessageType.TOAST, message);
    }


    private static final String USERNAME_PATTERN = "^[a-z0-9_-]{3,15}$";

    @Override
    public boolean userRegisterInformationValidation(String fullName, String userName) {

        if (fullName.trim().length() > 0 && userName.trim().length() > 0) {
            Pattern pattern = Pattern.compile(USERNAME_PATTERN);
            Matcher matcher = pattern.matcher(userName);
            if (matcher.matches()) {
                return true;
            } else
                return false;
        } else

            return false;

    }

}
