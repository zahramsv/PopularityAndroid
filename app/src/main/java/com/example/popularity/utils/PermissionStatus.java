package com.example.popularity.utils;


import io.reactivex.rxjava3.core.Observable;

public class PermissionStatus {
    private static PermissionStatus INSTANCE = null;

    private PermissionStatus() {}

    private Observable<Boolean> isPermissionGranted;

    public static synchronized PermissionStatus getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PermissionStatus();
        }
        return(INSTANCE);
    }

    public Observable<Boolean> observePermission(){
        return isPermissionGranted;
    }

}
