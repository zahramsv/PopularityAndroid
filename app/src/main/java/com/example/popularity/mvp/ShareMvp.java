package com.example.popularity.mvp;

import android.content.Context;
import android.net.Uri;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;

public interface ShareMvp {

    interface View {
        void shareScreenShot(Uri uri);

        AppCompatActivity getFragActivity();

        Context getViewContext();
    }

    interface Presenter {

        void takeScreenShot(android.view.View view);

        void selectAndCropImage();

        void getGalleryAccessPermission();

    }
}
