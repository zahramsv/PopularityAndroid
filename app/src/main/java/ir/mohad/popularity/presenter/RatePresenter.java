package ir.mohad.popularity.presenter;

import android.app.Dialog;
import android.os.Handler;

import androidx.appcompat.widget.AppCompatButton;

import ir.mohad.popularity.R;
import ir.mohad.popularity.model.BaseResponse;
import ir.mohad.popularity.model.SubmitRate;
import ir.mohad.popularity.model.User;
import ir.mohad.popularity.model.repository.UserRepository;
import ir.mohad.popularity.mvp.RateMvp;
import ir.mohad.popularity.myInterface.ApiServices;
import ir.mohad.popularity.myInterface.MainActivityTransaction;
import ir.mohad.popularity.utils.MyApp;
import ir.mohad.popularity.utils.ShowMessageType;

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

        baseComponents.showLoadingBar(true);

        apiServices.submitRateToFriend(submitRate).enqueue(new Callback<BaseResponse<String>>() {
            @Override
            public void onResponse(Call<BaseResponse<String>> call, Response<BaseResponse<String>> response) {
                baseComponents.showLoadingBar(false);
                if (response.isSuccessful()) {
                    if (response.body().getCode() == 200) {
                        Dialog dialog = new Dialog(rateView.getViewContext());
                        dialog.setContentView(R.layout.submit_rate_dialog);
                        dialog.setCancelable(false);
                        dialog.setCanceledOnTouchOutside(false);
                        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                        dialog.show();
                        btnRateDone = dialog.findViewById(R.id.btnRateDone);
                        btnRateDone.setOnClickListener(view -> {

                            final Handler handler = new Handler();
                            handler.postDelayed(() -> {
                                rateView.comeBackToHomeAfterRateDone();
                                dialog.dismiss();
                            }, 300);
                        });
                        //  baseComponents.showMessage(ShowMessageType.TOAST, rateView.getViewContext().getString(R.string.submitted_rates));
                    }

                    if (response.body().getCode() == 401) {
                        baseComponents.showMessage(ShowMessageType.TOAST, rateView.getViewContext().getString(R.string.rate_before));
                    }
                } else {
                    baseComponents.showMessage(ShowMessageType.TOAST, rateView.getViewContext().getString(R.string.error_api_call));
                }
            }


            @Override
            public void onFailure(Call<BaseResponse<String>> call, Throwable t) {
                baseComponents.showLoadingBar(false);
                baseComponents.showMessage(ShowMessageType.TOAST, rateView.getViewContext().getString(R.string.some_problems_when_use_api));
            }
        });

    }

    @Override
    public User getCurrentUser() {
        return userRepository.getCurrentUser();
    }


}
