package com.example.popularity.myInterface;

import android.os.Bundle;

import com.example.popularity.fragment.BaseFragment;
import com.example.popularity.model.User;
import com.example.popularity.utils.LoginKind;
import com.example.popularity.utils.ShowMessageType;
import com.example.popularity.utils.ToolBarIconKind;
import com.example.popularity.utils.ToolbarKind;

public interface MainActivityTransaction {
    interface Components {
        void showLoadingBar(boolean isShow);
        void openFragment(BaseFragment fragment, Boolean addStack, Bundle bundle);
        void changeToolbar(ToolbarKind kind, String title);
        void showToolbarIcon(ToolBarIconKind iconKind);
        void showMessage(ShowMessageType messageType,String message);
        void getPermission(String permission);
    }

    interface Attacher {
        void attachFragment(MainActivityTransaction.Components baseListener);
    }
}
