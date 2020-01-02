package com.example.popularity;

import com.example.popularity.model.User;
import com.example.popularity.model.LoginSendDataModel;
import com.example.popularity.utils.URLS;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface GetLoginDataService {

    @POST("http://popularity.mohad.ir/api/login_to_social")
    Call<LoginDataResponseModel> getLoginData(@Body LoginSendDataModel loginSendDataModel, Callback <Res);
}
                                                                                                                                                                 