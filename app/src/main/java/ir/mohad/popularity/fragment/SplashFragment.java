package ir.mohad.popularity.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ir.mohad.popularity.R;
import ir.mohad.popularity.presenter.SplashPresenter;
import ir.mohad.popularity.mvp.SplashMvp;
import ir.mohad.popularity.utils.ConnectivityReceiver;
import ir.mohad.popularity.utils.ShowMessageType;
import ir.mohad.popularity.utils.ToolbarKind;

public class SplashFragment extends BaseFragment implements SplashMvp.View {

    private SplashMvp.Presenter presenter;


    @Override
    public void onHiddenChanged(boolean hidden) {
        if (!hidden) {
            baseListener.changeToolbar(ToolbarKind.EMPTY, "");
        }

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = new SplashPresenter(this, baseListener);
        baseListener.changeToolbar(ToolbarKind.EMPTY, "");
        if (ConnectivityReceiver.isConnected()) {
            presenter.getUserInfoAfterWait();
        } else {
            baseListener.showMessage(ShowMessageType.TOAST, getString(R.string.network_connection_error));
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_splash, container, false);
    }

    @Override
    public Context getViewContext() {
        return getActivity();
    }

}
