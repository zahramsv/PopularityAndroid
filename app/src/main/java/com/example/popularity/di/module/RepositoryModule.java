package com.example.popularity.di.module;


import com.example.popularity.model.repository.UserRepository;
import com.example.popularity.myInterface.ApiServices;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(includes = {ServerModule.class})
public class RepositoryModule {

    @Singleton
    @Provides
    public UserRepository provideUserRepository(ApiServices api){
        return new UserRepository(api);
    }

}
