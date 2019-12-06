package com.example.popularity.utils;

import android.app.Application;

import com.example.popularity.R;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder().setDefaultFontPath("fonts/Samim_FD.ttf").build());

    }
}
