package com.example.popularity.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import com.example.popularity.R;
import com.example.popularity.activity.MainActivity;
import com.example.popularity.model.repository.LoginHandler;
import com.example.popularity.model.repository.SharedPrefsRepository;
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
    private LinearLayout changeLanguage, layoutLanguage;
    private SwitchCompat btnPhoneLogout, btnTwitterLogout;
    private SharedPrefsRepository sharedPrefsRepository;

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
        sharedPrefsRepository=new SharedPrefsRepository(getContext());
        presenter = new SettingPresenter(this, baseListener,sharedPrefsRepository);
        this.loginHandler = MyApp.getInstance().getBaseComponent().provideLoginHandler();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        sharedPrefsRepository = new SharedPrefsRepository(getContext());
        init(view);

        LoginKind loginKind = loginHandler.getLoginKind();
        if (loginKind == LoginKind.SMS) {
            btnPhoneLogout.setChecked(true);
        }

        btnPhoneLogout.setOnClickListener(view1 -> {
            presenter.logout(R.id.btnPhoneLogout, btnPhoneLogout);
        });

        btnTwitterLogout.setOnClickListener(view12 -> presenter.logout(R.id.btnTwitterLogout, btnTwitterLogout));

        changeLanguage.setOnClickListener(view1 -> {
            layoutLanguage.setVisibility(View.VISIBLE);
            layoutLanguage.animate()
                    .translationY(layoutLanguage.getHeight())
                    .alpha(1.0f)
                    .setListener(null);
        });


        btnSetLanguage.setOnClickListener(v -> {
            presenter.setLocalLanguage();
            layoutLanguage.setVisibility(View.GONE);
        });

        this.view = view;
        return view;
    }

    private void init(View view) {
        SharedPreferences prefs = getViewContext().getSharedPreferences("logout_status", Context.MODE_PRIVATE);
        String phoneNumberLogin = prefs.getString("phone_number_login", null);
        String twitterLogin = prefs.getString("twitter_login", null);
        baseListener.changeToolbar(ToolbarKind.BACK, getString(R.string.setting));
        btnPhoneLogout = view.findViewById(R.id.btnPhoneLogout);
        changeLanguage = view.findViewById(R.id.changeLanguage);
        layoutLanguage = view.findViewById(R.id.layoutLanguage);
        btnSetLanguage = view.findViewById(R.id.btnSetLanguage);
        rgbLanguage = view.findViewById(R.id.rgLanguage);
        btnPhoneLogout = view.findViewById(R.id.btnPhoneLogout);
        btnTwitterLogout = view.findViewById(R.id.btnTwitterLogout);

    }

    @Override
    public void restartActivity() {
        Intent intent = new Intent(getContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public AppCompatActivity getFragActivity() {
       return (AppCompatActivity) getActivity();
    }


   /* private void setLocalLanguage() {

        sharedPrefsRepository.setApplicationLanguageWithUser(getChosenLanguage());
        Configuration config = getActivity().getBaseContext().getResources().getConfiguration();
        Locale locale = new Locale(getChosenLanguage());
        Locale.setDefault(locale);
        config.locale = locale;
        getActivity().getBaseContext().getResources().updateConfiguration(
                config,
                getActivity().getBaseContext().getResources().getDisplayMetrics()
        );
        restartActivity();
    }*/

  /*  @Override
    private String getChosenLanguage() {
        RadioButton rbSelected = view.findViewById(rgbLanguage.getCheckedRadioButtonId());
        return rbSelected.getTag().toString();
    }*/

    @Override
    public Context getViewContext() {
        return getContext();
    }

    @Override
    public String getChosenLanguage() {
        RadioButton rbSelected = view.findViewById(rgbLanguage.getCheckedRadioButtonId());
        return rbSelected.getTag().toString();
    }
}
