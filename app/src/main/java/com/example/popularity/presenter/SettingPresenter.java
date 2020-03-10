package com.example.popularity.presenter;

import android.app.Dialog;
import android.content.res.Configuration;
import android.widget.RadioButton;

import androidx.appcompat.widget.SwitchCompat;

import com.example.popularity.R;
import com.example.popularity.fragment.LoginFragment;
import com.example.popularity.model.repository.LoginHandler;
import com.example.popularity.model.repository.SharedPrefsRepository;
import com.example.popularity.mvp.SettingMvp;
import com.example.popularity.myInterface.MainActivityTransaction;
import com.example.popularity.utils.MyApp;
import com.example.popularity.utils.ShowMessageType;

import java.util.Locale;

public class SettingPresenter implements SettingMvp.Presenter {

    private SettingMvp.View view;
    private MainActivityTransaction.Components baseComponent;
    private SharedPrefsRepository sharedPrefsRepository;


    public SettingPresenter(SettingMvp.View view, MainActivityTransaction.Components baseComponent, SharedPrefsRepository sharedPrefsRepository) {
        this.view = view;
        this.baseComponent = baseComponent;
        this.sharedPrefsRepository = sharedPrefsRepository;
    }

    @Override
    public void logout(int btnId, SwitchCompat switchCompat) {

        if (btnId == R.id.btnPhoneLogout) {
            Dialog dialog = new Dialog(view.getViewContext());
            dialog.setContentView(R.layout.logout_confirm_dialog);
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            dialog.show();
            dialog.findViewById(R.id.btnNo).setOnClickListener(view2 -> {
                dialog.dismiss();
                switchCompat.setChecked(true);
                return;
            });

            dialog.findViewById(R.id.btnYes).setOnClickListener(view22 -> {

                SharedPrefsRepository sharedPrefsRepository = new SharedPrefsRepository(view.getViewContext());
                sharedPrefsRepository.DeleteUser();
                dialog.dismiss();
                switchCompat.setChecked(false);
                baseComponent.openFragment(LoginFragment.newInstance(), true, null);
            });
        }

        if (btnId == R.id.btnTwitterLogout) {

            baseComponent.showMessage(ShowMessageType.TOAST, "Coming soon ...");
        }
    }

    @Override
    public void setLocalLanguage() {

        sharedPrefsRepository.setApplicationLanguageWithUser(view.getChosenLanguage());
        Configuration config = view.getFragActivity().getBaseContext().getResources().getConfiguration();
        Locale locale = new Locale(view.getChosenLanguage());
        Locale.setDefault(locale);
        config.locale = locale;
        view.getFragActivity().getBaseContext().getResources().updateConfiguration(
                config,
                view.getFragActivity().getBaseContext().getResources().getDisplayMetrics()
        );
        view.restartActivity();
    }

}
