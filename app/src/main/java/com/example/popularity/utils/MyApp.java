package com.example.popularity.utils;

import android.app.Application;

import androidx.multidex.MultiDex;

import com.example.popularity.di.component.BaseComponent;
import com.example.popularity.di.component.DaggerBaseComponent;
import com.example.popularity.di.module.ContextModule;

public class MyApp extends Application {

    private BaseComponent component;
    private static MyApp mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);
        mInstance = this;

        component = DaggerBaseComponent
                .builder()
                .contextModule(new ContextModule(this))
                .build();
    }

    public BaseComponent getBaseComponent(){
        return component;
    }

    public static synchronized MyApp getInstance() {
        return mInstance;
    }

    public void setConnectivityListener(ConnectivityReceiver.ConnectivityReceiverListener listener) {
        ConnectivityReceiver.connectivityReceiverListener = listener;
    }
}
