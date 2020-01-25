package com.example.popularity.myInterface;

import com.example.popularity.model.BaseResponse;
import com.example.popularity.model.Login;
import com.example.popularity.model.SocialRootModel;
import com.example.popularity.model.SubmitRate;
import com.example.popularity.model.User;
import com.example.popularity.utils.URLS;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiServices {


    // ☺
    @FormUrlEncoded
    @POST(URLS.SEND_SMS)
    Call<SocialRootModel> sendSms(@Field("user_mobile") String user_mobile);

    @FormUrlEncoded
    @POST(URLS.VERIFY_SMS)
    Call<SocialRootModel> varifySms(
            @Field("user_mobile") String user_mobile,
            @Field("verify_code") String verify_code);

    @POST(URLS.LOGIN_TO_SOCIAL)
    Call<SocialRootModel> getLoginData(@Body Login login);


    @FormUrlEncoded
    @POST(URLS.LOGIN_TO_SOCIAL)
    Call<SocialRootModel> login(
            @Field("social_primary") String socialPrimary,
            @Field("username") String username,
            @Field("full_name") String fullName,
            @Field("avatar_url") String avatarUrl,
            @Field("social_type") String socialType
    );

    @POST(URLS.RATE_TO_FRIEND)
    Call<BaseResponse<String>> submitRateToFriend(@Body SubmitRate submitRate);

    @GET(URLS.GET_USER_INFO)
    Call<BaseResponse<User>> getUserInfo(@Query("token") String token, @Query("userId") String userId);
    /*@FormUrlEncoded
    @POST(URLS.RateToFriend)
    Call<BaseResponse<String>> SubmitRateToFriend(
            @Field("token") String token,
            @Field("who_rate_id") int who_rate_id,
            @Field("whom_rated_id") int whom_rated_id,
            @Field("whom_username") String whom_username,
            @Field("whom_full_name") String whom_full_name,
            @Field("whom_avatar_url") String whom_avatar_url,
            @Field("social_type") int social_type,
            @Field("rate_look") float rate_look,
            @Field("rate_fitness") float rate_fitness,
            @Field("rate_style") float rate_style,
            @Field("rate_personality") float rate_personality,
            @Field("rate_trustworthy") float rate_trustworthy,
            @Field("rate_popularity") float rate_popularity
            );*/
}
