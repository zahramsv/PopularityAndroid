package ir.mohad.popularity.mvp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import ir.mohad.popularity.model.Rate;

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

        boolean takeScreenShot(android.view.View view);

        void selectAndCropImage();

        void getGalleryAccessPermission();

        void setBundleContent(Bundle bundle);

    }
}
