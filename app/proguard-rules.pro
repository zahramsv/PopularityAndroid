package ir.mohad.popularity;

import ir.mohad.popularity.model.User;
import ir.mohad.popularity.model.Login;
import ir.mohad.popularity.utils.URLS;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;


public interface GetLoginDataService {

    @POST("http://popularity.mohad.ir/api/login_to_social")
    Call<LoginDataResponseModel> getLoginData(@Body LoginSendDataModel login, Callback <Res);
}
                                                                                                                                                                 