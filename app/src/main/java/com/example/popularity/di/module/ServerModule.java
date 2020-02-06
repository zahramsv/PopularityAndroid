package com.example.popularity.di.module;

import android.util.Log;

import com.example.popularity.myInterface.ApiServices;
import com.example.popularity.utils.URLS;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ServerModule {

    @Singleton
    @Provides
    public ApiServices provideApiService(Retrofit retrofit){
        return retrofit.create(ApiServices.class);
    }

    @Provides
    public Retrofit provideRetrofit(OkHttpClient okHttpClient, Gson gson){
        return new Retrofit
                .Builder()
                .baseUrl(URLS.BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    @Provides
    public OkHttpClient provideOkHttpClient(HttpLoggingInterceptor logging){
        return new OkHttpClient.Builder()
                .addInterceptor(logging)
                .connectTimeout(180, TimeUnit.SECONDS)
                .readTimeout(180, TimeUnit.SECONDS)
                .writeTimeout(180, TimeUnit.SECONDS)
                .build();
    }

    @Provides
    public HttpLoggingInterceptor provideLoggingInterceptor() {
        return new HttpLoggingInterceptor(message -> Log.d("app_tag", message))
                .setLevel(HttpLoggingInterceptor.Level.BODY);
    }

    @Provides
    public Gson provideGson(){
        return new GsonBuilder()
                .setLenient()
                .create();
    }
}
