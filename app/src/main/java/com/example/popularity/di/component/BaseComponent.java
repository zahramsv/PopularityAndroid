package com.example.popularity.di.component;

import com.example.popularity.di.module.ContextModule;
import com.example.popularity.di.module.RepositoryModule;
import com.example.popularity.di.module.ServerModule;
import com.example.popularity.model.repository.UserRepository;
import com.example.popularity.myInterface.ApiServices;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {RepositoryModule.class, ContextModule.class, ServerModule.class})
public interface BaseComponent {
    UserRepository provideUserRepository();
    ApiServices provideApiService();
}
