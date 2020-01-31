package com.example.popularity.fragment;

import androidx.fragment.app.Fragment;

import com.example.popularity.myInterface.MainActivityTransaction;


public class BaseFragment extends Fragment implements MainActivityTransaction.Attacher {

    MainActivityTransaction.Components baseListener;

    @Override
    public void attachFragment(MainActivityTransaction.Components baseListener) {
        this.baseListener = baseListener;
    }


}
