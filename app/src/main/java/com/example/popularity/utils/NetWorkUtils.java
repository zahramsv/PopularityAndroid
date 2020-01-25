package com.example.popularity.utils;

import android.content.Context;
import android.net.ConnectivityManager;

public class NetWorkUtils {

    private boolean isNetworkConnected(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();

    }
}
