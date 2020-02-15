package com.example.popularity.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.popularity.R;
import com.example.popularity.model.Login;
import com.example.popularity.model.User;
import com.example.popularity.mvp.MobileLoginMvp;
import com.example.popularity.presenter.MobileLoginPresenter;
import com.example.popularity.utils.ToolbarKind;
import com.google.android.material.textfield.TextInputEditText;


public class RegisterFragment extends BaseFragment implements MobileLoginMvp.View {




    private TextInputEditText edtFullName,edtUserName;
    private AppCompatButton btnRegister;
    private MobileLoginMvp.Presenter presenter;


    public RegisterFragment() {
        // Required empty public constructor
    }



    public static RegisterFragment newInstance() {
        RegisterFragment fragment = new RegisterFragment();
        return fragment;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if (!hidden) {
            baseListener.changeToolbar(ToolbarKind.HOME, getString(R.string.empty));
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new MobileLoginPresenter(this, baseListener);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_register, container, false);
        init(view);
        Bundle bundle=getArguments();
        String primaryKey=bundle.getString("primary_key");
        btnRegister.setOnClickListener(view1 -> {
            Login user=new Login();
            user.setSocial_type(0);
            user.setUsername(edtUserName.getText().toString());
            user.setFull_name(edtFullName.getText().toString());
            user.setSocial_primary(primaryKey);
            user.setAvatar_url("test.jpg");
            presenter.loginToServer(user);
        });


        return view;
    }


    public void init(View view)
    {
        baseListener.changeToolbar(ToolbarKind.HOME, getString(R.string.empty));
        btnRegister=view.findViewById(R.id.btnRegister);
        edtFullName=view.findViewById(R.id.edtFullName);
        edtUserName=view.findViewById(R.id.edtUserName);



    }

    @Override
    public Context getViewContext() {
        return getContext();
    }
}
