package ir.mohad.popularity.fragment;

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

import ir.mohad.popularity.R;
import ir.mohad.popularity.presenter.RatePresenter;
import ir.mohad.popularity.model.Friend;
import ir.mohad.popularity.model.SubmitRate;
import ir.mohad.popularity.model.User;
import ir.mohad.popularity.mvp.RateMvp;
import ir.mohad.popularity.utils.ToolbarKind;

import ir.mohad.popularity.utils.Configs;


public class RateFragment extends BaseFragment implements RateMvp.View {

    private Friend friend;
    private View view;
    private RateMvp.Presenter presenter;


    public static RateFragment newInstance()
    {
        return new RateFragment();
    }
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
        friend = (Friend) bundle.getSerializable(Configs.BUNDLE_FRIEND);
        view = inflater.inflate(R.layout.fragment_rate, container, false);

        TextView txtName = view.findViewById(R.id.txtFriendName);
        String str = friend.getName();
        txtName.setText(str);

        view.findViewById(R.id.btnSaveRates).setOnClickListener(
                view1 -> {

                    User user = presenter.getCurrentUser();
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
