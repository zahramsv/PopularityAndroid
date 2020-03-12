package ir.mohad.popularity.presenter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

import ir.mohad.popularity.R;
import ir.mohad.popularity.fragment.HomeFragment;
import ir.mohad.popularity.fragment.LoginFragment;
import ir.mohad.popularity.model.BaseResponse;
import ir.mohad.popularity.model.BaseUserData;
import ir.mohad.popularity.model.UpdateInfo;
import ir.mohad.popularity.model.User;
import ir.mohad.popularity.model.repository.LoginHandler;
import ir.mohad.popularity.model.repository.UserRepository;
import ir.mohad.popularity.mvp.SplashMvp;
import ir.mohad.popularity.myInterface.ApiServices;
import ir.mohad.popularity.myInterface.MainActivityTransaction;
import ir.mohad.popularity.utils.MyApp;

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
                    //baseComponent.showMessage(ShowMessageType.TOAST, view.getViewContext().getString(R.string.error_under_construction));
                    //updateAppFromMarket();
                    dialog.dismiss();
                    //System.exit(0);
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
                   // updateAppFromMarket();
                    dialog.dismiss();

                   // baseComponent.showMessage(ShowMessageType.TOAST, view.getViewContext().getString(R.string.error_under_construction));
                });
                return false;
            }

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

      return true;
    }

    private void updateAppFromMarket() {
        Intent intent = new Intent(Intent.ACTION_VIEW , Uri.parse("market://details?id=ir.mohad.popularity.presenter"));
        view.getViewContext().startActivity(intent);
    }
}
