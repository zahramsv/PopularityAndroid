package ir.mohad.popularity.di.component;

import ir.mohad.popularity.di.module.ContextModule;
import ir.mohad.popularity.di.module.ImageFileModule;
import ir.mohad.popularity.di.module.RepositoryModule;
import ir.mohad.popularity.di.module.ServerModule;
import ir.mohad.popularity.model.repository.LoginHandler;
import ir.mohad.popularity.model.repository.UserRepository;
import ir.mohad.popularity.myInterface.ApiServices;

import java.io.File;
import java.io.FileOutputStream;

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
