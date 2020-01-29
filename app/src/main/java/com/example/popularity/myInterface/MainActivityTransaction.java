package com.example.popularity.myInterface;

import android.os.Bundle;

import com.example.popularity.fragment.BaseFragment;
import com.example.popularity.model.User;
import com.example.popularity.utils.ShowMessageType;
import com.example.popularity.utils.ToolbarKind;

public interface MainActivityTransaction {
    void showLoadingBar(boolean isShow);
    void openFragment(BaseFragment fragment, Boolean addStack, Bundle bundle);
    void changeToolbar(ToolbarKind kind, String title);
    User getMainUser();
    void setMainUser(User user);
    void showMessage(ShowMessageType messageType,String message);
}
