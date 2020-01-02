package com.example.popularity;

import com.example.popularity.model.LoginSendDataModel;
import com.example.popularity.model.SocialRootModel;
import com.example.popularity.utils.URLS;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface GetLoginDataService {


    @POST(URLS.LoginToSocial)
    Call<SocialRootModel> getLoginData(@Body LoginSendDataModel loginSendDataModel);

    @FormUrlEncoded
    @POST(URLS.LoginToSocial)
    Call<SocialRootModel> login(
      @Field("social_primary") String socialPrimary,
      @Field("username") String username,
      @Field("full_name") String fullName,
      @Field("avatar_url") String avatarUrl,
      @Field("social_type") String socialType
    );
}
