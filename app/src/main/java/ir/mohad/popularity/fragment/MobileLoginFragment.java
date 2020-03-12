package ir.mohad.popularity.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.cardview.widget.CardView;

import ir.mohad.popularity.R;
import ir.mohad.popularity.mvp.MobileLoginMvp;
import ir.mohad.popularity.presenter.MobileLoginPresenter;
import ir.mohad.popularity.utils.ShowMessageType;
import ir.mohad.popularity.utils.ToolBarIconKind;
import ir.mohad.popularity.utils.ToolbarKind;

public class MobileLoginFragment extends BaseFragment
        implements MobileLoginMvp.View {

    private MobileLoginMvp.Presenter presenter;
    private AppCompatEditText edtMobile, edtVerifyCode;
    private AppCompatButton btnReceiveCode;
    private AppCompatButton btnVerifyCode;
    private CardView cardViewVerifyCode, cardViewMobileRegister;


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
            baseListener.closeKeyboard();
            if (presenter.phoneNumberValidation(edtMobile.getText().toString()))
            {
                presenter.sendSMS(edtMobile.getText().toString());
                cardViewVerifyCode.setVisibility(View.VISIBLE);
                cardViewMobileRegister.setVisibility(View.GONE);
            }
            else
            {
                baseListener.showMessage(ShowMessageType.TOAST,getString(R.string.phone_number_validation));
            }


        });

        btnVerifyCode.setOnClickListener(view1 -> {
            baseListener.closeKeyboard();
            if (presenter.verifyCodeValidation(edtVerifyCode.getText().toString()))
            {
                presenter.verifyCode(edtVerifyCode.getText().toString());
            }
            else
            {
                baseListener.showMessage(ShowMessageType.TOAST,getString(R.string.verify_code_validation));
            }


        });
        return view;
    }




    private void init(View view) {
        baseListener.changeToolbar(ToolbarKind.HOME, getString(R.string.empty));
        baseListener.showToolbarIcon(ToolBarIconKind.INVISIBLE);
        edtVerifyCode = view.findViewById(R.id.edtVerifyCode);
        edtMobile = view.findViewById(R.id.edtMobile);
        btnVerifyCode = view.findViewById(R.id.btnVerifyCode);
        btnReceiveCode = view.findViewById(R.id.btnReceiveCode);
        cardViewMobileRegister = view.findViewById(R.id.cardViewMobileRegister);
        cardViewVerifyCode = view.findViewById(R.id.cardViewVerifyCode);
    }

    @Override
    public Context getViewContext() {
        return getContext();
    }

}
