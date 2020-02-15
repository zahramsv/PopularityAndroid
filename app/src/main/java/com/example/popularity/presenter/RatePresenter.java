package com.example.popularity.presenter;

import android.app.Dialog;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.widget.AppCompatButton;

import com.example.popularity.R;

import com.example.popularity.model.BaseResponse;
import com.example.popularity.model.SubmitRate;
import com.example.popularity.model.User;
import com.example.popularity.model.repository.UserRepository;
import com.example.popularity.mvp.RateMvp;
import com.example.popularity.myInterface.ApiServices;
import com.example.popularity.myInterface.MainActivityTransaction;
import com.example.popularity.utils.MyApp;
import com.example.popularity.utils.ShowMessageType;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RatePresenter implements RateMvp.Presenter {

    private RateMvp.View rateView;
    private MainActivityTransaction.Components baseComponents;
    private UserRepository userRepository;
    private ApiServices apiServices;
    private AppCompatButton btnRateDone;

    public RatePresenter(RateMvp.View rateView, MainActivityTransaction.Components baseComponents) {
        this.rateView = rateView;
        this.baseComponents = baseComponents;
        userRepository = MyApp.getInstance().getBaseComponent().provideUserRepository();
        apiServices = MyApp.getInstance().getBaseComponent().provideApiService();
    }

    @Override
    public void submitRate(SubmitRate submitRate) {


        apiServices.submitRateToFriend(submitRate).enqueue(new Callback<BaseResponse<String>>() {
            @Override
            public void onResponse(Call<BaseResponse<String>> call, Response<BaseResponse<String>> response) {

                if (response.body().getCode() == 200) {
                    Dialog dialog = new Dialog(rateView.getViewContext());
                    dialog.setContentView(R.layout.submit_rate_dialog);
                    dialog.show();
                    btnRateDone=dialog.findViewById(R.id.btnRateDone);
                    btnRateDone.setOnClickListener(view -> {

                        final Handler handler = new Handler();
                        handler.postDelayed(() -> {
                            rateView.comeBackToHomeAfterRateDone();
                            dialog.dismiss();
                        }, 2000);
                    });
                  //  baseComponents.showMessage(ShowMessageType.TOAST, rateView.getViewContext().getString(R.string.submitted_rates));

                }

                if (response.body().getCode()==401)
                {
                    baseComponents.showMessage(ShowMessageType.TOAST, rateView.getViewContext().getString(R.string.rate_before));

                }



            }


            @Override
            public void onFailure(Call<BaseResponse<String>> call, Throwable t) {
                baseComponents.showMessage(ShowMessageType.TOAST, rateView.getViewContext().getString(R.string.some_problems_when_use_api));
            }
        });

    }

    @Override
    public User getCurrentUser() {
        return userRepository.getCurrentUser();
    }


}
