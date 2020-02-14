package com.example.popularity.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.cardview.widget.CardView;

import com.example.popularity.R;
import com.example.popularity.mvp.MobileLoginMvp;
import com.example.popularity.presenter.MobileLoginPresenter;
import com.example.popularity.utils.ToolbarKind;
import com.google.android.material.textfield.TextInputEditText;

public class MobileLoginFragment extends BaseFragment
        implements MobileLoginMvp.View
{

    private MobileLoginMvp.Presenter presenter;
    private AppCompatEditText edtMobile, edtVerifyCode;
    private AppCompatButton btnReceiveCode;
    private AppCompatButton btnVerifyCode;
    private CardView cardViewVerifyCode,cardViewMobileRegister;


    @Override
    public void onHiddenChanged(boolean hidden) {
        if (!hidden) {
            baseListener.changeToolbar(ToolbarKind.HOME, getString(R.string.empty));
        }
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new MobileLoginPresenter(this, baseListener);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_mobile_login, container, false);
        init(view);


        btnReceiveCode.setOnClickListener(view1 -> {
            presenter.sendSMS(edtMobile.getText().toString());
            cardViewVerifyCode.setVisibility(View.VISIBLE);
            cardViewMobileRegister.setVisibility(View.GONE);
        });

        btnVerifyCode.setOnClickListener(view1 -> {
            presenter.verifyCode(edtVerifyCode.getText().toString());
        });
        return view;
    }

    private void init(View view) {
        baseListener.changeToolbar(ToolbarKind.HOME, getString(R.string.empty));
        edtVerifyCode = view.findViewById(R.id.edtVerifyCode);
        edtMobile = view.findViewById(R.id.edtMobile);
        btnVerifyCode = view.findViewById(R.id.btnVerifyCode);
        btnReceiveCode = view.findViewById(R.id.btnReceiveCode);
        cardViewMobileRegister=view.findViewById(R.id.cardViewMobileRegister);
        cardViewVerifyCode=view.findViewById(R.id.cardViewVerifyCode);

    }

    @Override
    public Context getViewContext() {
        return getContext();
    }

}
