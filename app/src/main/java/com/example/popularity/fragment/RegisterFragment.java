package com.example.popularity.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.AppCompatButton;

import com.example.popularity.R;
import com.example.popularity.model.Login;
import com.example.popularity.mvp.MobileLoginMvp;
import com.example.popularity.mvp.RegisterMvp;
import com.example.popularity.presenter.MobileLoginPresenter;
import com.example.popularity.presenter.RegisterPresenter;
import com.example.popularity.utils.ToolBarIconKind;
import com.example.popularity.utils.ToolbarKind;
import com.google.android.material.textfield.TextInputEditText;


public class RegisterFragment extends BaseFragment implements RegisterMvp.View {


    private TextInputEditText edtFullName, edtUserName;
    private AppCompatButton btnRegister;
    private RegisterMvp.Presenter presenter;


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
        presenter = new RegisterPresenter(this, baseListener);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_register, container, false);
        init(view);
        Bundle bundle = getArguments();
        String primaryKey = bundle.getString("primary_key");

        presenter.setUserSocialPrimary(primaryKey);

        btnRegister.setOnClickListener(view1 -> {
            Login user = presenter.getLoginInfo(
                    edtFullName.getText().toString(),
                    edtUserName.getText().toString()
            );
            presenter.loginToServer(user);
        });


        return view;
    }


    private void init(View view) {
        baseListener.changeToolbar(ToolbarKind.HOME, getString(R.string.empty));
        baseListener.showToolbarIcon(ToolBarIconKind.INVISIBLE);
        btnRegister = view.findViewById(R.id.btnRegister);
        edtFullName = view.findViewById(R.id.edtFullName);
        edtUserName = view.findViewById(R.id.edtUserName);


    }

    @Override
    public Context getViewContext() {
        return getContext();
    }
}
