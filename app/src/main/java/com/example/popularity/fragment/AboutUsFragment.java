package com.example.popularity.fragment;

import android.content.Context;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.popularity.R;
import com.example.popularity.utils.BaseFragment;
import com.example.popularity.utils.ToolbarState;

public class AboutUsFragment extends Fragment {


    private ToolbarState toolbarState;

    public AboutUsFragment() {
        // Required empty public constructor
    }

    public static AboutUsFragment newInstance() {
        AboutUsFragment fragment = new AboutUsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        toolbarState.toolbarState(true);
        return inflater.inflate(R.layout.fragment_about_us, container, false);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof MenuDrawerFragment.OpenMenuFragments) {
            toolbarState = (ToolbarState) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnAccountingMainFragmentInteractionListener");
        }
    }

}
