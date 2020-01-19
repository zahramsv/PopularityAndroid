package com.example.popularity.myInterface;

import android.os.Bundle;

import com.example.popularity.fragment.BaseFragment;

public interface MainActivityTransaction {
    void showLoadingBar(boolean isShow);
    void showMessage(String message);
    void openFragment(BaseFragment fragment, Boolean addStack, Bundle bundle);
}
