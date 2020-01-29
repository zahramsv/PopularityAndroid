package com.example.popularity.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.popularity.R;
import com.example.popularity.utils.ShowMessageType;


public class MenuDrawerFragment extends BaseFragment {

    private ActionBarDrawerToggle drawerToogle;
    private DrawerLayout drawerLayout;
    private View view;
    private AppCompatButton btnRateUs, btnPrivacyPolicy, btnSettings, btnAboutUs;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_menu_drawer, container, false);
        TextView username = view.findViewById(R.id.txtName);
        SharedPreferences preferences=getActivity().getSharedPreferences("user_data",Context.MODE_PRIVATE);
        username.setText(preferences.getString("full_name",null));
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    public void setUp(final DrawerLayout dl, final Toolbar toolbar) {
        drawerLayout = dl;
        drawerToogle = new ActionBarDrawerToggle(getActivity(), dl, toolbar, R.string.drawer_open, R.string.drawer_close) {

            @Override
            public boolean onOptionsItemSelected(MenuItem item) {
                if (item != null && item.getItemId() == android.R.id.home) {
                    if (dl.isDrawerOpen(Gravity.RIGHT)) {
                        dl.closeDrawer(Gravity.RIGHT);
                    } else {
                        dl.openDrawer(Gravity.RIGHT);
                    }
                }
                return false;
            }


        };

    }

    private void init() {

        btnAboutUs = view.findViewById(R.id.btnAboutUs);
        btnRateUs = view.findViewById(R.id.btnRateUs);
        btnSettings = view.findViewById(R.id.btnSettings);
        btnPrivacyPolicy = view.findViewById(R.id.btnPrivacyPolicy);


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
            baseListener.showMessage(ShowMessageType.SNACK,getString(R.string.error_under_construction));
        });

        btnSettings.setOnClickListener(view -> {
            if (baseListener != null) {
                baseListener.openFragment(SettingFragment.newInstance(), true, null);
            }
        });

    }

}
