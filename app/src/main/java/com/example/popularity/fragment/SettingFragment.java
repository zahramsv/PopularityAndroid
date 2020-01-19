package com.example.popularity.fragment;

import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.popularity.R;
import com.example.popularity.activity.MainActivity;
import com.example.popularity.utils.SavePref;
import com.example.popularity.utils.ToolbarState;


public class SettingFragment extends BaseFragment {


    private ToolbarState toolbarState;

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
        toolbarState=(ToolbarState)getContext();

        toolbarState.toolbarState(true,getResources().getString(R.string.setting_toolbar_txt));
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

}
