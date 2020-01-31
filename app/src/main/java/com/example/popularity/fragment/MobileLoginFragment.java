package com.example.popularity.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;

import com.example.popularity.R;
import com.example.popularity.logic.MobileLoginPresenter;
import com.example.popularity.model.BaseResponse;
import com.example.popularity.model.User;
import com.example.popularity.model.UserPopularity;
import com.example.popularity.mvp.MobileLoginMvp;
import com.example.popularity.myInterface.ApiServices;
import com.example.popularity.repository.UserRepository;
import com.example.popularity.utils.RetrofitInstance;
import com.example.popularity.utils.SavePref;
import com.example.popularity.utils.ShowMessageType;
import com.example.popularity.utils.ToolbarKind;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Retrofit;

public class MobileLoginFragment extends BaseFragment implements
        UserRepository.UserRepoListener, MobileLoginMvp.View {


    private MobileLoginMvp.Presenter presenter;
    private TextInputEditText edtMobile, edtVerifyCode;
    private AppCompatButton btnReceiveCode;
    private AppCompatButton btnVerifyCode;


    @Override
    public void onHiddenChanged(boolean hidden) {
        if (!hidden) {
            baseListener.changeToolbar(ToolbarKind.HOME, getString(R.string.login_with_mobile));
        }
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter=new MobileLoginPresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_mobile_login, container, false);
        init(view);


        btnReceiveCode.setOnClickListener(view1 -> {
            presenter.sendSMS(edtMobile.getText().toString());
        });

        btnVerifyCode.setOnClickListener(view1 -> {
            presenter.verifyCode(edtVerifyCode.getText().toString());
        });
        return view;
    }

    private void init(View view) {
        baseListener.changeToolbar(ToolbarKind.HOME, getString(R.string.login_with_mobile));
        edtVerifyCode = view.findViewById(R.id.edtVerifyCode);
        edtMobile = view.findViewById(R.id.edtMobile);
        btnVerifyCode=view.findViewById(R.id.btnVerifyCode);
        btnReceiveCode=view.findViewById(R.id.btnReceiveCode);

    }

    @Override
    public void onDone(User user) {
        baseListener.showLoadingBar(false);

        UserPopularity userPopularity = user.getRates_summary_sum();
        SavePref savePref = new SavePref();
        user.setSocial_primary(edtMobile.getText().toString() + "");
        savePref.SaveUser(getContext(), user, userPopularity);
        baseListener.setMainUser(user);
        baseListener.openFragment(new HomeFragment(), true, null);
    }

    @Override
    public void onFailure(String message) {
        baseListener.showLoadingBar(false);
        baseListener.showMessage(ShowMessageType.TOAST,message);
    }

    @Override
    public void showMessage(ShowMessageType messageType, String message) {
        baseListener.showMessage(messageType,message);
    }

    @Override
    public Context getViewContext() {
        return getContext();
    }

    @Override
    public void showLoadingBar(boolean isShow) {
        baseListener.showLoadingBar(isShow);
    }

    @Override
    public UserRepository.UserRepoListener setUserRepo() {
        return this;
    }
}
