package ir.mohad.popularity.myInterface;

import android.os.Bundle;

import ir.mohad.popularity.fragment.BaseFragment;
import ir.mohad.popularity.utils.ShowMessageType;
import ir.mohad.popularity.utils.ToolBarIconKind;
import ir.mohad.popularity.utils.ToolbarKind;

public interface MainActivityTransaction {
    interface Components {
        void showLoadingBar(boolean isShow);
        void openFragment(BaseFragment fragment, Boolean addStack, Bundle bundle);
        void changeToolbar(ToolbarKind kind, String title);
        void showToolbarIcon(ToolBarIconKind iconKind);
        void showMessage(ShowMessageType messageType, String message);
        void closeKeyboard();
    }

    interface Attacher {
        void attachFragment(MainActivityTransaction.Components baseListener);
    }
}
