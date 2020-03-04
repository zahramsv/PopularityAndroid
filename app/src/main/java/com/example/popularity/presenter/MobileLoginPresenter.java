
package com.example.popularity.presenter;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.popularity.R;
import com.example.popularity.fragment.HomeFragment;
import com.example.popularity.fragment.MenuDrawerFragment;
import com.example.popularity.fragment.RegisterFragment;
import com.example.popularity.model.BaseResponse;
import com.example.popularity.model.Login;
import com.example.popularity.model.User;
import com.example.popularity.model.VerifySmsResponseData;
import com.example.popularity.model.repository.FriendRepository;
import com.example.popularity.model.repository.LoginHandler;
import com.example.popularity.model.repository.UserRepository;
import com.example.popularity.mvp.MobileLoginMvp;
import com.example.popularity.myInterface.ApiServices;
import com.example.popularity.myInterface.MainActivityTransaction;
import com.example.popularity.rx.rxTest;
import com.example.popularity.utils.ConnectivityReceiver;
import com.example.popularity.utils.LoginKind;
import com.example.popularity.utils.MyApp;
import com.example.popularity.utils.ShowMessageType;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MobileLoginPresenter implements
        MobileLoginMvp.Presenter,
        UserRepository.UserRepoListener, rxTest.observable {
    private MobileLoginMvp.View view;
    private String userMobile;
    private BaseResponse sendSmsResult;
    private MainActivityTransaction.Components baseComponent;

    private UserRepository userRepository;
    private ApiServices apiServices;
    private LoginHandler loginHandler;
    public Observable<Login> myobservable;
    private rxTest.observer observer;

    public MobileLoginPresenter(MobileLoginMvp.View view, MainActivityTransaction.Components baseComponent) {
        this.view = view;
        this.baseComponent = baseComponent;

        this.loginHandler = MyApp.getInstance().getBaseComponent().provideLoginHandler();
        this.apiServices = MyApp.getInstance().getBaseComponent().provideApiService();
        this.userRepository = MyApp.getInstance().getBaseComponent().provideUserRepository();


    }

    @Override
    public void verifyCode(String verifyCode) {
        if (ConnectivityReceiver.isConnected()) {

            apiServices.verifySms(userMobile, verifyCode).enqueue(new Callback<BaseResponse<VerifySmsResponseData>>() {
                @Override
                public void onResponse(Call<BaseResponse<VerifySmsResponseData>> call, Response<BaseResponse<VerifySmsResponseData>> response) {

                    if ((response.isSuccessful())) {
                        if (response.body().getData().isRegistered) {
                            loginToServer(getLoginInfo());
                            setObservable();
                        } else {
                            Bundle bundle = new Bundle();
                            bundle.putString("primary_key", userMobile);
                            baseComponent.openFragment(RegisterFragment.newInstance(), false, bundle);
                        }
                    } else {

                        baseComponent.showMessage(ShowMessageType.TOAST, MyApp.getInstance().getApplicationContext().getString(R.string.error_api_call));
                    }


                }

                @Override
                public void onFailure(Call<BaseResponse<VerifySmsResponseData>> call, Throwable t) {
                    baseComponent.showMessage(ShowMessageType.TOAST, t.getMessage());
                }
            });
        } else {
            baseComponent.showMessage(ShowMessageType.TOAST, view.getViewContext().getString(R.string.network_connection_error));
        }


    }

    @Override
    public void sendSMS(String mobile) {


        userMobile = mobile;

        apiServices.sendSms(mobile).enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {

                sendSmsResult = response.body();

            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                baseComponent.showMessage(ShowMessageType.SNACK, view.getViewContext().getString(R.string.some_problems_when_use_api));
            }
        });


    }

    @Override
    public void loginToServer(Login user) {
        baseComponent.showLoadingBar(true);
        userRepository.loginToServer(user, this);
    }

    @Override
    public Login getLoginInfo() {

        Login user = new Login();


        if (loginHandler.getLoginKind() == LoginKind.MOCK) {
            // return userRepository.getCurrentUser();
            user.setAvatar_url("test.jpg");
            user.setFull_name("zahra hadi");
            user.setSocial_primary(userMobile);
            user.setUsername("z.hadi");
            user.setSocial_type(0);
        } else if (loginHandler.getLoginKind() == LoginKind.SMS) {
            user.setAvatar_url("");
            user.setFull_name("");
            user.setSocial_primary(userMobile);
            user.setUsername("");
            user.setSocial_type(0);
        }


        return user;
    }

    @Override
    public boolean phoneNumberValidation(String phoneNumber) {
        if (phoneNumber.trim().length() == 11) {
            Pattern pattern = Pattern.compile("^09[0|1|2|3][0-9]{8}$");
            Matcher matcher = pattern.matcher(phoneNumber);
            if (matcher.matches()) {
                return true;
            } else
                return false;
        } else
            return false;

    }

    @Override
    public boolean verifyCodeValidation(String code) {
        if (code.trim().length() == 4) {
            Pattern pattern = Pattern.compile("[0-9]+");
            Matcher matcher = pattern.matcher(code);
            if (matcher.matches()) {
                return true;
            } else
                return false;
        } else
            return false;
    }

    @Override
    public void onLoginDone(User user) {
        baseComponent.showLoadingBar(false);
        user.setSocial_primary(userMobile);
        loginHandler.saveLoginInfo(user, user.getRates_summary_sum());
        baseComponent.openFragment(new HomeFragment(), true, null);
    }

    @Override
    public void onLoginFailure(String message) {
        baseComponent.showLoadingBar(false);
        baseComponent.showMessage(ShowMessageType.TOAST, message);
    }


    @Override
    public void setObservable() {
        myobservable = Observable.create(emitter -> myobservable.subscribeWith(observer.getObserver()));
    }


}
