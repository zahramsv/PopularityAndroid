package com.example.popularity.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
import com.example.popularity.utils.BaseFragment;
import com.example.popularity.R;

public class LoginFragment extends BaseFragment {



    Button instagram_btn;

    public LoginFragment() {
        // Required empty public constructor
    }


    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        clickEvents(view);

        return view;

    }




    //Fragment Button Click
    public void clickEvents(View v) {

        switch (v.getId())
        {
            case R.id.instagram_btn:
                break;
        }

    }



    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        return super.onCreateAnimation(transit, enter, nextAnim);
    }


}
