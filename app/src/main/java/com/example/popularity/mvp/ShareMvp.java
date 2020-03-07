package com.example.popularity.mvp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.popularity.model.Rate;

import java.io.File;
import java.util.List;

public interface ShareMvp {

    interface View {

        AppCompatActivity getFragActivity();

        Context getViewContext();

        void shareImageOnSocial(Intent intent);

        void onBackPressed();

        void setRatesList(List<Rate> rates);
    }

    interface Presenter {

        void takeScreenShot(android.view.View view);

        void selectAndCropImage();

        void getGalleryAccessPermission();

        void setBundleContent(Bundle bundle);

    }
}
