package com.example.popularity.di.module;


import android.content.Context;

import com.example.popularity.model.repository.LoginHandler;
import com.example.popularity.model.repository.UserRepository;
import com.example.popularity.myInterface.ApiServices;
import com.example.popularity.model.repository.SharedPrefsRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(includes = {ServerModule.class, ContextModule.class})
public class RepositoryModule {

    @Singleton
    @Provides
    public UserRepository provideUserRepository(ApiServices api){
        return new UserRepository(api);
    }

    @Singleton
    @Provides
    public LoginHandler provideLoginHandler(SharedPrefsRepository sharedPrefsRepository){
        return new LoginHandler(sharedPrefsRepository);
    }

    @Singleton
    @Provides
    public SharedPrefsRepository provideSharedPreferences(Context context){
        return new SharedPrefsRepository(context);
    }

}
