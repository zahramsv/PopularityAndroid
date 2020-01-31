
package com.example.popularity.logic;

import androidx.appcompat.widget.AppCompatEditText;

import com.example.popularity.R;
import com.example.popularity.model.BaseResponse;
import com.example.popularity.model.Login;
import com.example.popularity.model.VerifySmsResponseData;
import com.example.popularity.mvp.MobileLoginMvp;
import com.example.popularity.myInterface.ApiServices;
import com.example.popularity.repository.UserRepository;
import com.example.popularity.utils.ConnectivityReceiver;
import com.example.popularity.utils.RetrofitInstance;
import com.example.popularity.utils.ShowMessageType;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MobileLoginPresenter implements MobileLoginMvp.Presenter {


    MobileLoginMvp.View view;
    private String userMobile;
    private BaseResponse sendSmsResult;
    private RetrofitInstance retrofitInstance;
    private Retrofit retrofit;
    private ApiServices apiServices;

    public MobileLoginPresenter(MobileLoginMvp.View view) {
        this.view = view;
        retrofitInstance = new RetrofitInstance();
        retrofit = retrofitInstance.getRetrofitInstance();
        apiServices = retrofit.create(ApiServices.class);
    }

    @Override
    public void verifyCode(String verifyCode) {
        if (ConnectivityReceiver.isConnected()) {
            if (sendSmsResult != null) {
                if (sendSmsResult.getCode() == 200) {

                    apiServices.verifySms(userMobile, verifyCode).enqueue(new Callback<BaseResponse<VerifySmsResponseData>>() {
                        @Override
                        public void onResponse(Call<BaseResponse<VerifySmsResponseData>> call, Response<BaseResponse<VerifySmsResponseData>> response) {

                            if (response.body().getData().isRegistered) {
                                loginToServer();
                            } else {
                                view.showMessage(ShowMessageType.TOAST,view.getViewContext().getString(R.string.some_problems_when_use_api));
                            }
                        }

                        @Override
                        public void onFailure(Call<BaseResponse<VerifySmsResponseData>> call, Throwable t) {
                            view.showMessage(ShowMessageType.TOAST,t.getMessage());
                        }
                    });
                } else {
                    view.showMessage(ShowMessageType.TOAST,view.getViewContext().getString(R.string.error_receive_code));
                }
            } else {
                view.showMessage(ShowMessageType.TOAST,view.getViewContext().getString(R.string.error_api_call));
            }

        } else {
            view.showMessage(ShowMessageType.TOAST,view.getViewContext().getString(R.string.network_connection_error));
        }
    }

    @Override
    public void sendSMS(String mobile) {

        userMobile=mobile;
        if (ConnectivityReceiver.isConnected()) {

            apiServices.sendSms(mobile).enqueue(new Callback<BaseResponse>() {
                @Override
                public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {

                    sendSmsResult = response.body();
                }

                @Override
                public void onFailure(Call<BaseResponse> call, Throwable t) {

                }
            });
        } else {
            view.showMessage(ShowMessageType.SNACK,view.getViewContext().getString(R.string.network_connection_error));
        }
    }

    @Override
    public void loginToServer() {


        view.showLoadingBar(true);
        UserRepository userRepository = new UserRepository();
        userRepository.loginToServer(getLoginInfo(), view.setUserRepo());

    }

    @Override
    public Login getLoginInfo() {

        Login user = new Login();
        user.setAvatar_url("myavatar.jpg");
        user.setFull_name("zahra hadi");
        user.setSocial_primary(userMobile);
        user.setUsername("z.hadi");
        user.setSocial_type(0);
        return user;
    }


}
