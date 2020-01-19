package com.example.popularity.fragment;

import androidx.fragment.app.Fragment;
import com.example.popularity.myInterface.FragmentNavigationListener;
import com.example.popularity.myInterface.MainActivityTransaction;


public class BaseFragment extends Fragment implements FragmentNavigationListener {

    MainActivityTransaction baseListener;

    @Override
    public void attachFragment(MainActivityTransaction baseListener) {
        this.baseListener = baseListener;
    }
}
