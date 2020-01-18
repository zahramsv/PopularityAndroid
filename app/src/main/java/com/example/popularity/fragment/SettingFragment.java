package com.example.popularity.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.popularity.R;
import com.example.popularity.activity.MainActivity;
import com.example.popularity.utils.SavePref;
import com.example.popularity.utils.ToolbarState;


public class SettingFragment extends Fragment {


    private ToolbarState toolbarState;
    public SettingFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static SettingFragment newInstance() {
        SettingFragment fragment = new SettingFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
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
        View view= inflater.inflate(R.layout.fragment_setting, container, false);
        view.findViewById(R.id.log_out_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SavePref savePref=new SavePref();
                savePref.DeleteUser(getContext());
                getActivity().finish();
                Intent intent=new Intent(getContext(),MainActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof MenuDrawerFragment.OpenMenuFragments)
        {
            toolbarState= (ToolbarState) context;
        }
        else
        {
            throw new RuntimeException(context.toString()
                    + " must implement OnAccountingMainFragmentInteractionListener");
        }
    }
}
