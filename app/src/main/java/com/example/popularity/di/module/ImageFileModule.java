package com.example.popularity.di.module;

import android.os.Environment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Date;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ImageFileModule {


    @Provides
    @Singleton
    public FileOutputStream getOutputStream(File file) {
        try {
            return new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }

    }


    @Provides
    public File getImageFile() {
        Date now = new Date();
        android.text.format.DateFormat.format("MM-dd_hh:mm:ss", now);
        return new File(Environment.getExternalStorageDirectory().toString() + "/" + now + ".jpg");
    }



}
