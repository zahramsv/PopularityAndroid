package com.example.popularity.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.popularity.R;
import com.example.popularity.model.Login;
import com.example.popularity.model.User;
import com.example.popularity.model.repository.UserRepository;
import com.example.popularity.mvp.MenuDrawerMvp;
import com.example.popularity.presenter.MobileLoginPresenter;
import com.example.popularity.rx.rxTest;
import com.example.popularity.utils.MyApp;
import com.example.popularity.utils.ShowMessageType;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;


public class MenuDrawerFragment extends BaseFragment implements MenuDrawerMvp.View, rxTest.observer {

    private View view;
    private LinearLayout btnRateUs, btnPrivacyPolicy, btnSettings, btnAboutUs, btnHome;
    private UserRepository userRepository;
    private Observer<Login> observer;
    private rxTest.observable observable;
    private TextView username;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_menu_drawer, container, false);
        username = view.findViewById(R.id.txtName);

        setObserver();

//        myObervable().subscribe(observer);


        userRepository = new UserRepository(MyApp.getInstance().getBaseComponent().provideApiService());
       /* if (userRepository.getCurrentUser()!=null)
        {
            User user=userRepository.getCurrentUser();
            username.setText(user.getFull_name());
        }*/

       /* SharedPreferences preferences = getActivity().getSharedPreferences("user_data", Context.MODE_PRIVATE);
        username.setText(preferences.getString("full_name", null));*/

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    private void init() {

        btnAboutUs = view.findViewById(R.id.btnAboutUs);
        btnRateUs = view.findViewById(R.id.btnRateUs);
        btnSettings = view.findViewById(R.id.btnSettings);
        btnPrivacyPolicy = view.findViewById(R.id.btnPrivacyPolicy);
        btnHome = view.findViewById(R.id.btnHome);


        btnHome.setOnClickListener(view -> {

        });
        btnAboutUs.setOnClickListener(v -> {
            if (baseListener != null) {
                baseListener.openFragment(AboutUsFragment.newInstance(), true, null);
            }
        });


        btnPrivacyPolicy.setOnClickListener(v -> {
            if (baseListener != null) {
                baseListener.openFragment(PrivacyPolicyFragment.newInstance(), true, null);
            }
        });

        btnRateUs.setOnClickListener(view -> {
            baseListener.showMessage(ShowMessageType.SNACK, getString(R.string.error_under_construction));
        });

        btnSettings.setOnClickListener(view -> {
            if (baseListener != null) {

                baseListener.openFragment(SettingFragment.newInstance(), true, null);
            }
        });


    }


    @Override
    public void setObserver() {

        observer = new Observer<Login>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull Login login) {

                username.setText(login.getFull_name());
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };

       // observer.onSubscribe(observable.setObservable());

    }

    @Override
    public Observer<Login> getObserver() {
        return observer;
    }


}


