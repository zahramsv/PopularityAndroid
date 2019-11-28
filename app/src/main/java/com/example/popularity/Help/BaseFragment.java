package com.example.popularity.Help;

import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.popularity.R;

public class BaseFragment extends Fragment {

    public boolean onBackPressed() {
        return false;
    }


    protected void initToolbar(View view) {
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_menu);

    }
}
