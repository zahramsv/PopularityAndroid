package com.example.popularity.presenter;

import android.os.Handler;

import com.example.popularity.R;

import com.example.popularity.model.BaseResponse;
import com.example.popularity.model.SubmitRate;
import com.example.popularity.model.User;
import com.example.popularity.model.repository.UserRepository;
import com.example.popularity.mvp.RateMvp;
import com.example.popularity.myInterface.ApiServices;
import com.example.popularity.myInterface.MainActivityTransaction;
import com.example.popularity.utils.MyApp;
import com.example.popularity.utils.RetrofitInstance;
import com.example.popularity.utils.ShowMessageType;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RatePresenter implements RateMvp.Presenter {

    private RateMvp.View rateView;
    private MainActivityTransaction.Components baseComponents;
    private UserRepository userRepository;

    public RatePresenter(RateMvp.View rateView, MainActivityTransaction.Components baseComponents) {
        this.rateView = rateView;
        this.baseComponents = baseComponents;
        userRepository = MyApp.getInstance().getBaseComponent().provideUserRepository();
    }

    @Override
    public void submitRate(SubmitRate submitRate ) {

        RetrofitInstance retrofitInstance = new RetrofitInstance();
        Retrofit retrofit = retrofitInstance.getRetrofitInstance();
        ApiServices friendsRate = retrofit.create(ApiServices.class);
        friendsRate.submitRateToFriend(submitRate).enqueue(new Callback<BaseResponse<String>>() {
            @Override
            public void onResponse(Call<BaseResponse<String>> call, Response<BaseResponse<String>> response) {
                baseComponents.showMessage(ShowMessageType.TOAST, rateView.getViewContext().getString(R.string.submitted_rates));
                final Handler handler = new Handler();
                handler.postDelayed(() -> {
                    rateView.comeBackToHomeAfterRateDone();
                }, 2000);

            }

            @Override
            public void onFailure(Call<BaseResponse<String>> call, Throwable t) {
                baseComponents.showMessage(ShowMessageType.TOAST,rateView.getViewContext().getString(R.string.some_problems_when_use_api));
            }
        });

    }

    @Override
    public User getCurrentUser() {
        return userRepository.getCurrentUser();
    }


}
