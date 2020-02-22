package com.example.popularity.presenter;

import android.app.Dialog;

import androidx.appcompat.widget.SwitchCompat;

import com.example.popularity.R;
import com.example.popularity.fragment.LoginFragment;
import com.example.popularity.model.repository.LoginHandler;
import com.example.popularity.model.repository.SharedPrefsRepository;
import com.example.popularity.mvp.SettingMvp;
import com.example.popularity.myInterface.MainActivityTransaction;
import com.example.popularity.utils.MyApp;

public class SettingPresenter implements SettingMvp.Presenter {

    private SettingMvp.View view;
    private MainActivityTransaction.Components baseComponent;


    public SettingPresenter(SettingMvp.View view, MainActivityTransaction.Components baseComponent) {
        this.view = view;
        this.baseComponent = baseComponent;
    }

    @Override
    public void logout(int btnId,SwitchCompat switchCompat) {

        if (btnId==R.id.btnPhoneLogout)
        {
            Dialog dialog = new Dialog(view.getViewContext());
            dialog.setContentView(R.layout.logout_confirm_dialog);
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            dialog.show();
            dialog.findViewById(R.id.btnNo).setOnClickListener(view2 -> {
                dialog.dismiss();
               switchCompat.setChecked(false);
                return;
            });

            dialog.findViewById(R.id.btnYes).setOnClickListener(view22 -> {

                SharedPrefsRepository sharedPrefsRepository = new SharedPrefsRepository(view.getViewContext());
                sharedPrefsRepository.DeleteUser();
                dialog.dismiss();
                baseComponent.openFragment(LoginFragment.newInstance(),true,null);
            });
        }

        if (btnId==R.id.btnTwitterLogout)
        {

        }
    }
}
