package com.example.popularity.fragment;

import android.content.Context;
import android.os.Bundle;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.example.popularity.R;
import com.example.popularity.utils.SavePref;
import com.example.popularity.utils.ToolbarState;


public class MenuDrawerFragment extends Fragment {

    private ActionBarDrawerToggle drawertoogle;
    private DrawerLayout my_drawer_layout;
    private ViewGroup layout;
    private Button btn1, btn2;
    private OnSlidingMenuFragmentListener mListener;
    private OpenMenuFragments openMenuFragments;
    private AppCompatButton rateUs, t, privacyPolicy, settings, aboutUs;

    public MenuDrawerFragment() {

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        layout = (ViewGroup) inflater.inflate(R.layout.fragment_menu_drawer, container, false);
        TextView username = layout.findViewById(R.id.name);


        String ss = SavePref.USER_DATA;
        username.setText(SavePref.USER_DATA);
        return layout;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        define();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnSlidingMenuFragmentListener) {
            mListener = (OnSlidingMenuFragmentListener) context;
            openMenuFragments = (OpenMenuFragments) context;  // ? chera

        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnAccountingMainFragmentInteractionListener");
        }
    }

    public void setUp(final DrawerLayout dl, final Toolbar toolbar) {
        my_drawer_layout = dl;

        drawertoogle = new ActionBarDrawerToggle(getActivity(), dl, toolbar, R.string.drawer_open, R.string.drawer_close) {

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


        aboutUs = layout.findViewById(R.id.aboutUs);
        rateUs = layout.findViewById(R.id.rateUs);
        settings = layout.findViewById(R.id.settings);
        privacyPolicy = layout.findViewById(R.id.privacyPolicy);


        aboutUs.setOnClickListener(v -> {
            if (mListener != null) {
                openMenuFragments.Open(AboutUsFragment.newInstance());

            }
        });

        privacyPolicy.setOnClickListener(v -> {
            if (mListener != null) {
                openMenuFragments.Open(PrivacyPolicyFragment.newInstance());

            }
        });

        rateUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMenuFragments.Open(RateUsFragment.newInstance());
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMenuFragments.Open(SettingFragment.newInstance());
            }
        });

    }

    public interface OpenMenuFragments {
        void Open(Fragment fragment);
    }

    public interface OnSlidingMenuFragmentListener {
        void onBtn1Clicked(Fragment fragment);

        void onBtn2Clicked(Fragment fragment);


    }

}
