package com.example.popularity.Utils;

import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.View;
import com.example.popularity.R;

public class BaseFragment extends Fragment {
    Toolbar toolbar;

    protected void initToolbar(final View view, String title ) {
        toolbar = view.findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_menu);
        toolbar.setNavigationOnClickListener(v -> {
            initNavigationMenu( view);
           // Toast.makeText(getActivity(),"sdsdf",Toast.LENGTH_LONG).show();
        });
        getActivity().setTitle(title);
    }



    protected void initNavigationMenu(View view) {


    }
}
