package com.example.popularity.mvp;

import android.net.Uri;

import java.io.File;

public interface ShareMvp {

    interface View
    {
        void shareScreenShot(Uri uri);
    }

    interface Presenter {

        void takeScreenShot(android.view.View view);

    }
}
