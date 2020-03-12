package ir.mohad.popularity.mvp;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

public interface SettingMvp {

    interface View{
        Context getViewContext();
        void restartActivity();
        AppCompatActivity getFragActivity();
        String getChosenLanguage();
    }

    interface Presenter
    {
         void logout(int btnId, SwitchCompat switchCompat);
         void setLocalLanguage();

    }
}
