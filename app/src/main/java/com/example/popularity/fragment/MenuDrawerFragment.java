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


public class MenuDrawerFragment extends BaseFragment {

    private ActionBarDrawerToggle drawerToogle;
    private DrawerLayout drawerLayout;
    private ViewGroup layout;
    private AppCompatButton rateUs, privacyPolicy, settings, aboutUs;

    public MenuDrawerFragment() {

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        layout = (ViewGroup) inflater.inflate(R.layout.fragment_menu_drawer, container, false);
        TextView username = layout.findViewById(R.id.name);
        SharedPreferences preferences=getActivity().getSharedPreferences("user_data",Context.MODE_PRIVATE);
        username.setText(preferences.getString("full_name",null));
        return layout;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        define();
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

    private void define() {

        aboutUs = layout.findViewById(R.id.btnAboutUs);
        rateUs = layout.findViewById(R.id.btnRateUs);
        settings = layout.findViewById(R.id.btnSettings);
        privacyPolicy = layout.findViewById(R.id.btnPrivacyPolicy);


        aboutUs.setOnClickListener(v -> {
            if (baseListener != null) {
                baseListener.openFragment(AboutUsFragment.newInstance(), true, null);
            }
        });

        privacyPolicy.setOnClickListener(v -> {
            if (baseListener != null) {
                baseListener.openFragment(PrivacyPolicyFragment.newInstance(), true, null);
            }
        });

        rateUs.setOnClickListener(view -> {
            baseListener.showMessage(getString(R.string.error_under_construction));
        });

        settings.setOnClickListener(view -> {
            if (baseListener != null) {
                baseListener.openFragment(SettingFragment.newInstance(), true, null);
            }
        });

    }

}
