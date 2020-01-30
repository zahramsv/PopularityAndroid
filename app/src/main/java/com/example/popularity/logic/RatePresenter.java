package com.example.popularity.logic;

import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.popularity.R;

import com.example.popularity.model.BaseResponse;
import com.example.popularity.model.Friend;
import com.example.popularity.model.SubmitRate;
import com.example.popularity.model.User;
import com.example.popularity.mvp.RateMvp;
import com.example.popularity.myInterface.ApiServices;
import com.example.popularity.utils.RetrofitInstance;
import com.example.popularity.utils.ShowMessageType;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RatePresenter implements RateMvp.Presenter {

    RateMvp.RateView rateView;

    public RatePresenter(RateMvp.RateView rateView) {
        this.rateView = rateView;
    }

    @Override
    public void SubmitRate() {

        View view = rateView.getCurrentView();
        Friend friend = rateView.getFriend();
        TextView userName = view.findViewById(R.id.txtName);
        userName.setText("Rate To: " + " " + friend.getName());
        User user = rateView.getUserData();
        AppCompatRatingBar look = view.findViewById(R.id.rtLook);
        AppCompatRatingBar style = view.findViewById(R.id.rtStyle);
        AppCompatRatingBar popularity = view.findViewById(R.id.rtPopularity);
        AppCompatRatingBar fitness = view.findViewById(R.id.rtFitness);
        AppCompatRatingBar trustworthy = view.findViewById(R.id.rtTrustworthy);
        AppCompatRatingBar personality = view.findViewById(R.id.rtPersonality);

        SubmitRate submitRate = new SubmitRate(user.getToken(), user.getSocial_primary(), friend.getUserId()
                , friend.getName(), friend.getName(),
                user.getAvatar_url(), user.getSocial_type(), look.getRating(),
                fitness.getRating(), style.getRating(), personality.getRating(), trustworthy.getRating(),
                popularity.getRating());

        RetrofitInstance retrofitInstance = new RetrofitInstance();
        Retrofit retrofit = retrofitInstance.getRetrofitInstance();
        ApiServices friendsRate = retrofit.create(ApiServices.class);
        friendsRate.submitRateToFriend(submitRate).enqueue(new Callback<BaseResponse<String>>() {
            @Override
            public void onResponse(Call<BaseResponse<String>> call, Response<BaseResponse<String>> response) {


                rateView.showMessage(ShowMessageType.TOAST, rateView.getViewContext().getString(R.string.submitted_rates));
                final Handler handler = new Handler();
                handler.postDelayed(() -> {
                    rateView.returnFmManager().popBackStackImmediate();
                    FragmentManager fm = rateView.returnFmManager();
                    Fragment fragment = fm.findFragmentById(R.id.frmPlaceholder);
                    FragmentTransaction ft = fm.beginTransaction();
                    if (fragment != null) {
                        ft.show(fragment);
                        ft.commit();
                    }
                }, 2000);

            }

            @Override
            public void onFailure(Call<BaseResponse<String>> call, Throwable t) {

                rateView.showMessage(ShowMessageType.TOAST,rateView.getViewContext().getString(R.string.some_problems_when_use_api));
            }
        });

    }


}
