package com.example.popularity.myInterface;

import com.example.popularity.model.Login;
import com.example.popularity.model.SocialRootModel;
import com.example.popularity.utils.URLS;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface GetLoginDataService {


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
}
