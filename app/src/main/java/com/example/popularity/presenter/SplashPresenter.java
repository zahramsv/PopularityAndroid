package com.example.popularity.presenter;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.popularity.R;
import com.example.popularity.fragment.HomeFragment;
import com.example.popularity.fragment.LoginFragment;
import com.example.popularity.model.BaseResponse;
import com.example.popularity.model.BaseUserData;
import com.example.popularity.model.UpdateInfo;
import com.example.popularity.model.UpdateInfoOS;
import com.example.popularity.model.User;
import com.example.popularity.model.repository.LoginHandler;
import com.example.popularity.model.repository.UserRepository;
import com.example.popularity.mvp.SplashMvp;
import com.example.popularity.myInterface.ApiServices;
import com.example.popularity.myInterface.MainActivityTransaction;
import com.example.popularity.utils.MyApp;
import com.example.popularity.utils.ShowMessageType;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashPresenter implements SplashMvp.Presenter {

    private SplashMvp.View view;
    private MainActivityTransaction.Components baseComponent;
    private UserRepository userRepository;
    private LoginHandler loginHandler;


    public SplashPresenter(SplashMvp.View view, MainActivityTransaction.Components baseComponent) {
        this.view = view;
        this.baseComponent = baseComponent;
        this.loginHandler = MyApp.getInstance().getBaseComponent().provideLoginHandler();
        this.userRepository = MyApp.getInstance().getBaseComponent().provideUserRepository();
    }

    @Override
    public void getUserInfoAfterWait() {
        final Handler handler = new Handler();
        handler.postDelayed(() -> {
            getUserInfoFromServer();
        }, 1000);
    }


    private void getUserInfoFromServer() {
        SharedPreferences prefs = view.getViewContext().getSharedPreferences("user_data", Context.MODE_PRIVATE);
        String token = prefs.getString("token", null);
        String social_primary = prefs.getString("social_primary", null);


        if (token != null && social_primary != null) {

            ApiServices apiServices = MyApp.getInstance().getBaseComponent().provideApiService();
            apiServices.getUserInfo(token, social_primary).enqueue(new Callback<BaseResponse<BaseUserData>>() {
                @Override
                public void onResponse(Call<BaseResponse<BaseUserData>> call, Response<BaseResponse<BaseUserData>> response) {

                    assert response.body() != null;
                    BaseResponse<BaseUserData> result = response.body();

                    //todo: check update status
                    if (isUpdateStatusOkay(result.getData().getUpdateInfo())) {
                        if (result.getCode() == 200) {
                            User user = result.getData().getUserInfo();
                            userRepository.setCurrentUser(user);
                            loginHandler.saveLoginInfo(user, user.getRates_summary_sum());
                            baseComponent.openFragment(new HomeFragment(), false, null);
                        } else {
                            baseComponent.openFragment(new LoginFragment(), false, null);
                        }
                    }
                }


                @Override
                public void onFailure(Call<BaseResponse<BaseUserData>> call, Throwable t) {

                }
            });


        } else {
            baseComponent.openFragment(new LoginFragment(), false, null);
        }
        Log.i("app_tag", token + "");
    }

    private boolean isUpdateStatusOkay(UpdateInfo updateInfo) {
        try {
            PackageInfo pInfo = view.getViewContext().getPackageManager().getPackageInfo(view.getViewContext().getPackageName(), 0);
            int verCode = pInfo.versionCode;

            if ( Integer.parseInt(updateInfo.getUpdateInfoOS().getLastForceUpdateVersion())>verCode) {
                Dialog dialog = new Dialog(view.getViewContext());
                dialog.setContentView(R.layout.update_dialog);
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                TextView updateMessage = dialog.findViewById(R.id.txtUpdateMessage);
                updateMessage.setText(R.string.update_message);
                dialog.show();
                dialog.findViewById(R.id.btnUpdate).setOnClickListener(view1 -> {
                    baseComponent.showMessage(ShowMessageType.TOAST, view.getViewContext().getString(R.string.error_under_construction));
                    System.exit(0);
                });

                return false;
            }else if (Integer.parseInt(updateInfo.getUpdateInfoOS().getLastVersion())>verCode)
            {
                Dialog dialog = new Dialog(view.getViewContext());
                dialog.setContentView(R.layout.update_dialog);
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                TextView updateMessage = dialog.findViewById(R.id.txtUpdateMessage);
                updateMessage.setText(R.string.update_message);
                dialog.show();
                dialog.findViewById(R.id.btnUpdate).setOnClickListener(view1 -> {
                    baseComponent.showMessage(ShowMessageType.TOAST, view.getViewContext().getString(R.string.error_under_construction));
                });
                return false;
            }

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }


      return true;
    }

}
