package com.example.popularity.myInterface;

import com.example.popularity.model.BaseResponse;
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


    @POST(URLS.RATE_TO_FRIEND)
    Call<BaseResponse<String>> SubmitRateToFriend(@Body SubmitRate submitRate);

    @GET(URLS.GET_USER_INFO)
    Call<BaseResponse<User>> GetUserInfo(@Query("token") String token,@Query("userId") String userId);
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
