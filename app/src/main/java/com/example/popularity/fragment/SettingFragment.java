package com.example.popularity.fragment;

import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.popularity.R;
import com.example.popularity.activity.MainActivity;
import com.example.popularity.mvp.SettingMvp;
import com.example.popularity.utils.SavePref;
import com.example.popularity.utils.ToolbarKind;


public class SettingFragment extends BaseFragment implements SettingMvp.View {


    @Override
    public void onHiddenChanged(boolean hidden) {
        if (!hidden)
        baseListener.changeToolbar(ToolbarKind.BACK,getString(R.string.setting_toolbar_txt));

    }

    public static SettingFragment newInstance() {
        SettingFragment fragment = new SettingFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        baseListener.changeToolbar(ToolbarKind.BACK,getString(R.string.setting_toolbar_txt));
        View view= inflater.inflate(R.layout.fragment_setting, container, false);
        view.findViewById(R.id.btnLogout).setOnClickListener(view1 -> {

            SavePref savePref=new SavePref();
            savePref.DeleteUser(getContext());
            getActivity().finish();
            Intent intent=new Intent(getContext(),MainActivity.class);
            startActivity(intent);
        });
        return view;
    }

}
