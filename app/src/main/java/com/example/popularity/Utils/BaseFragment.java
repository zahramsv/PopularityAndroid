package com.example.popularity.Utils;

import android.app.Activity;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import com.example.popularity.Activities.MainActivity;
import com.example.popularity.Fragments.MenuDrawer;
import com.example.popularity.R;

public class BaseFragment extends Fragment {
    Toolbar toolbar;

    protected void initToolbar(final View view, String title ) {
        toolbar = view.findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_menu);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initNavigationMenu( view);
               // Toast.makeText(getActivity(),"sdsdf",Toast.LENGTH_LONG).show();
            }
        });
        getActivity().setTitle(title);
    }



    protected void initNavigationMenu(View view) {


    }
}
