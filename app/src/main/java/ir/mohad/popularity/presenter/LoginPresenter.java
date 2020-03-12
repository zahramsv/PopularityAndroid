package ir.mohad.popularity.presenter;

import ir.mohad.popularity.fragment.HomeFragment;
import ir.mohad.popularity.model.Login;
import ir.mohad.popularity.model.User;
import ir.mohad.popularity.model.repository.LoginHandler;
import ir.mohad.popularity.model.repository.UserRepository;
import ir.mohad.popularity.mvp.LoginFragmentMvp;
import ir.mohad.popularity.myInterface.MainActivityTransaction;
import ir.mohad.popularity.utils.LoginKind;
import ir.mohad.popularity.utils.MyApp;
import ir.mohad.popularity.utils.ShowMessageType;

public class LoginPresenter implements LoginFragmentMvp.Presenter, UserRepository.UserRepoListener {

    private LoginFragmentMvp.View view;
    private MainActivityTransaction.Components baseComponent;

    private UserRepository userRepository;
    private LoginHandler loginHandler;

    public LoginPresenter(LoginFragmentMvp.View view, MainActivityTransaction.Components baseComponent) {
        this.view = view;
        this.baseComponent = baseComponent;
        userRepository = MyApp.getInstance().getBaseComponent().provideUserRepository();
        loginHandler = MyApp.getInstance().getBaseComponent().provideLoginHandler();
    }

    @Override
    public void setLoginKind(LoginKind kind) {
        loginHandler.setLoginKind(kind);
    }

    @Override
    public void loginUser() {
        if (loginHandler.getLoginKind() == LoginKind.MOCK) {
            userRepository.loginToServer(new Login().getMockData(), this);
        }
    }

    @Override
    public void onLoginDone(User user) {
        baseComponent.showLoadingBar(false);
        user.setSocial_primary(new Login().getMockData().getSocial_primary());
        loginHandler.saveLoginInfo(user, user.getRates_summary_sum());
        baseComponent.openFragment(new HomeFragment(), true, null);
    }

    @Override
    public void onLoginFailure(String message) {
        baseComponent.showMessage(ShowMessageType.SNACK, message);
    }
}
