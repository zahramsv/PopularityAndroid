package com.example.popularity.mvp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;

public interface ShareMvp {

    interface View {

        AppCompatActivity getFragActivity();

        Context getViewContext();

        void shareImageOnSocial(Intent intent);

        void onBackPressed();
    }

    interface Presenter {

        void takeScreenShot(android.view.View view);

        void selectAndCropImage();

        void getGalleryAccessPermission();

    }
}
