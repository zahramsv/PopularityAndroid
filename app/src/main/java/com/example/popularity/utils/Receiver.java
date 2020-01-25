package com.example.popularity.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.widget.Toast;

public class Receiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        boolean isConnected= cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
        if (isConnected)
        {
            Toast.makeText(context,"OK",Toast.LENGTH_LONG).show();
        }
        else
            Toast.makeText(context,"NO",Toast.LENGTH_LONG).show();

    }
}
