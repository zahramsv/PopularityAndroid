package com.example.popularity.di.component;

import com.example.popularity.di.module.ContextModule;
import com.example.popularity.di.module.ImageFileModule;
import com.example.popularity.di.module.RepositoryModule;
import com.example.popularity.di.module.ServerModule;
import com.example.popularity.model.repository.LoginHandler;
import com.example.popularity.model.repository.UserRepository;
import com.example.popularity.myInterface.ApiServices;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {RepositoryModule.class, ContextModule.class, ServerModule.class, ImageFileModule.class})
public interface BaseComponent {

    UserRepository provideUserRepository();

    ApiServices provideApiService();

    LoginHandler provideLoginHandler();

    FileOutputStream provideOutputSteam();

    File provideFile();
}
