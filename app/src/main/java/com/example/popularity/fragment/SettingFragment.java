package com.example.popularity.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;

import com.example.popularity.R;
import com.example.popularity.model.repository.LoginHandler;
import com.example.popularity.mvp.SettingMvp;
import com.example.popularity.presenter.SettingPresenter;
import com.example.popularity.utils.LoginKind;
import com.example.popularity.utils.MyApp;
import com.example.popularity.utils.ToolbarKind;

import java.util.Locale;


public class SettingFragment extends BaseFragment implements SettingMvp.View {

    private SettingMvp.Presenter presenter;
    private LoginHandler loginHandler;
    private Button btnSetLanguage;
    private RadioGroup rgbLanguage;
    private View view;

    @Override
    public void onHiddenChanged(boolean hidden) {
        if (!hidden)
            baseListener.changeToolbar(ToolbarKind.BACK, getString(R.string.setting));
    }


    public static SettingFragment newInstance() {
        SettingFragment fragment = new SettingFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new SettingPresenter(this, baseListener);
        this.loginHandler = MyApp.getInstance().getBaseComponent().provideLoginHandler();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        SharedPreferences prefs = getViewContext().getSharedPreferences("logout_status", Context.MODE_PRIVATE);
        String phoneNumberLogin = prefs.getString("phone_number_login", null);
        String twitterLogin = prefs.getString("twitter_login", null);


        baseListener.changeToolbar(ToolbarKind.BACK, getString(R.string.setting));
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        SwitchCompat btnPhoneLogout = view.findViewById(R.id.btnPhoneLogout);

        LoginKind loginKind = loginHandler.getLoginKind();
        if (loginKind == LoginKind.SMS) {
            btnPhoneLogout.setChecked(true);
        }

        view.findViewById(R.id.btnPhoneLogout).setOnClickListener(view1 -> {
            presenter.logout(R.id.btnPhoneLogout, btnPhoneLogout);
        });

        SwitchCompat btnTwitterLogout = view.findViewById(R.id.btnTwitterLogout);
        view.findViewById(R.id.btnTwitterLogout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.logout(R.id.btnTwitterLogout, btnTwitterLogout);
            }
        });

        btnSetLanguage = view.findViewById(R.id.btnSetLanguage);
        rgbLanguage = view.findViewById(R.id.rgLanguage);

        btnSetLanguage.setOnClickListener(v -> {
            setLocalLanguage();
        });

        this.view = view;
        return view;
    }

    private void setLocalLanguage() {
        Configuration config = getActivity().getBaseContext().getResources().getConfiguration();
        Locale locale = new Locale(getChosenLanguage());
        Locale.setDefault(locale);
        config.locale = locale;
        getActivity().getBaseContext().getResources().updateConfiguration(
                config,
                getActivity().getBaseContext().getResources().getDisplayMetrics()
        );
    }

    private String getChosenLanguage() {
        RadioButton rbSelected = view.findViewById(rgbLanguage.getCheckedRadioButtonId());
        return rbSelected.getTag().toString();
    }

    @Override
    public Context getViewContext() {
        return getContext();
    }
}
