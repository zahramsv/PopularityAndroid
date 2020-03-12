package ir.mohad.popularity.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import ir.mohad.popularity.R;
import ir.mohad.popularity.mvp.AboutUsMvp;
import ir.mohad.popularity.presenter.AboutUsPresenter;
import ir.mohad.popularity.utils.ShowMessageType;
import ir.mohad.popularity.utils.ToolbarKind;

public class AboutUsFragment extends BaseFragment implements AboutUsMvp.View {


    private View view;
    private AboutUsMvp.Presenter presenter;


    public static AboutUsFragment newInstance() {
        AboutUsFragment fragment = new AboutUsFragment();
        return fragment;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if (!hidden)
            baseListener.changeToolbar(ToolbarKind.BACK, getString(R.string.about_us_toolbar_txt));
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new AboutUsPresenter(this, baseListener);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        baseListener.changeToolbar(ToolbarKind.BACK, getString(R.string.about_us_toolbar_txt));
        view = inflater.inflate(R.layout.fragment_about_us, container, false);
        view.findViewById(R.id.imgFacebook).setOnClickListener(view -> baseListener.showMessage(ShowMessageType.TOAST,"test"));

        view.findViewById(R.id.imgInstagram).setOnClickListener(view -> {
            presenter.openInstagramPage();
        });

        view.findViewById(R.id.imgLinkedIn).setOnClickListener(view1 -> {
            presenter.openLinedInPage();
        });

        view.findViewById(R.id.imgFacebook).setOnClickListener(view1 -> {
            presenter.openFaceBookPage();
        });


        view.findViewById(R.id.imgWhatsApp).setOnClickListener(view1 -> {
            presenter.openWhatsAppPage();
        });

        view.findViewById(R.id.imgTwitter).setOnClickListener(view1 -> {
          presenter.openTwitterPage();
        });

        view.findViewById(R.id.imgYoutube).setOnClickListener(view1 -> {
            presenter.openYoutubePage();
        });

        return view;
    }


    @Override
    public void setDescriptionAboutUs(String desc) {

    }

    @Override
    public Context getViewContext() {
        return getContext();
    }
}
