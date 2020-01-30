package com.example.popularity.logic;

import android.os.Handler;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.popularity.R;

import com.example.popularity.model.BaseResponse;
import com.example.popularity.model.SubmitRate;
import com.example.popularity.mvp.RateMvp;
import com.example.popularity.myInterface.ApiServices;
import com.example.popularity.utils.RetrofitInstance;
import com.example.popularity.utils.ShowMessageType;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RatePresenter implements RateMvp.Presenter {

    RateMvp.View rateView;

    public RatePresenter(RateMvp.View rateView) {
        this.rateView = rateView;
    }

    @Override
    public void submitRate(SubmitRate submitRate ) {

        RetrofitInstance retrofitInstance = new RetrofitInstance();
        Retrofit retrofit = retrofitInstance.getRetrofitInstance();
        ApiServices friendsRate = retrofit.create(ApiServices.class);
        friendsRate.submitRateToFriend(submitRate).enqueue(new Callback<BaseResponse<String>>() {
            @Override
            public void onResponse(Call<BaseResponse<String>> call, Response<BaseResponse<String>> response) {


                rateView.showMessage(ShowMessageType.TOAST, rateView.getViewContext().getString(R.string.submitted_rates));
                final Handler handler = new Handler();
                handler.postDelayed(() -> {
                    rateView.comeBackToHomeAfterRateDone();
                }, 2000);

            }

            @Override
            public void onFailure(Call<BaseResponse<String>> call, Throwable t) {

                rateView.showMessage(ShowMessageType.TOAST,rateView.getViewContext().getString(R.string.some_problems_when_use_api));
            }
        });

    }


}
