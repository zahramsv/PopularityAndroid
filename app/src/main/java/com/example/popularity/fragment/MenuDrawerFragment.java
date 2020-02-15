package com.example.popularity.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.popularity.R;
import com.example.popularity.model.Login;
import com.example.popularity.model.repository.UserRepository;
import com.example.popularity.mvp.MenuDrawerMvp;
import com.example.popularity.utils.MyApp;
import com.example.popularity.utils.ShowMessageType;


public class MenuDrawerFragment extends BaseFragment implements MenuDrawerMvp.View {

    private View view;
    private LinearLayout btnRateUs, btnPrivacyPolicy, btnSettings, btnAboutUs,btnHome;
    private UserRepository userRepository;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_menu_drawer, container, false);
        TextView username = view.findViewById(R.id.txtName);
        userRepository=new UserRepository(MyApp.getInstance().getBaseComponent().provideApiService());
        SharedPreferences preferences = getActivity().getSharedPreferences("user_data", Context.MODE_PRIVATE);
        username.setText(preferences.getString("full_name", null));
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
            if (userRepository.getCurrentUser()!=null)
            {
                baseListener.openFragment(HomeFragment.newInstance(),true,null);
            }
            else
            {
                baseListener.openFragment(LoginFragment.newInstance(),true,null);

            }
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
}


