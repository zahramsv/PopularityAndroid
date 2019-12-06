package com.example.popularity.utils;

import android.app.Application;

import com.example.popularity.R;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;


public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
       // CalligraphyConfig.initDefault(new CalligraphyConfig.Builder().setDefaultFontPath("fonts/Samim_FD.ttf").build());

    }
}
