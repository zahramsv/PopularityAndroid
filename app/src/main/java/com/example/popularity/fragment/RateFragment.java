package com.example.popularity.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.popularity.R;
import com.example.popularity.presenter.RatePresenter;
import com.example.popularity.model.Friend;
import com.example.popularity.model.SubmitRate;
import com.example.popularity.model.User;
import com.example.popularity.mvp.RateMvp;
import com.example.popularity.utils.ShowMessageType;
import com.example.popularity.utils.ToolbarKind;

import static com.example.popularity.utils.Configs.BUNDLE_FRIEND;


public class RateFragment extends BaseFragment implements RateMvp.View {

    private Friend friend;
    private View view;
    private RateMvp.Presenter presenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new RatePresenter(this, baseListener);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            baseListener.changeToolbar(ToolbarKind.BACK, getString(R.string.rate_us));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        baseListener.changeToolbar(ToolbarKind.BACK, getString(R.string.rate_toolbar));
        Bundle bundle = getArguments();
        friend = (Friend) bundle.getSerializable(BUNDLE_FRIEND);
        view = inflater.inflate(R.layout.fragment_rate, container, false);
        view.findViewById(R.id.btnSaveRates).setOnClickListener(
                view1 -> {
                    TextView txtName = view.findViewById(R.id.txtName);
                    String str = getContext().getString(R.string.rate_to_friend) + " " + friend.getName();
                    txtName.setText(str);
                    User user = baseListener.getMainUser();
                    AppCompatRatingBar rtLook = view.findViewById(R.id.rtLook);
                    AppCompatRatingBar rtStyle = view.findViewById(R.id.rtStyle);
                    AppCompatRatingBar rtPopularity = view.findViewById(R.id.rtPopularity);
                    AppCompatRatingBar rtFitness = view.findViewById(R.id.rtFitness);
                    AppCompatRatingBar rtTrustworthy = view.findViewById(R.id.rtTrustworthy);
                    AppCompatRatingBar rtPersonality = view.findViewById(R.id.rtPersonality);

                    SubmitRate submitRate = new SubmitRate(user.getToken(), user.getSocial_primary(), friend.getUserId()
                            , friend.getName(), friend.getName(),
                            user.getAvatar_url(), user.getSocial_type(), rtLook.getRating(),
                            rtFitness.getRating(), rtStyle.getRating(), rtPersonality.getRating(), rtTrustworthy.getRating(),
                            rtPopularity.getRating()
                    );

                    presenter.submitRate(submitRate);
                }
        );
        return view;
    }

    @Override
    public void comeBackToHomeAfterRateDone() {
        FragmentManager fm = getFragmentManager();
        fm.popBackStackImmediate();
        Fragment fragment = fm.findFragmentById(R.id.frmPlaceholder);
        FragmentTransaction ft = fm.beginTransaction();
        if (fragment != null) {
            ft.show(fragment);
            ft.commit();
        }
    }

    @Override
    public Context getViewContext() {
        return getContext();
    }

}
