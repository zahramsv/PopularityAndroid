package com.example.popularity.rx;

import android.util.Log;

import com.example.popularity.model.Login;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;

public interface rxTest {

    interface observer {
        void setObserver();

        Observer<Login> getObserver();
    }


    interface observable {
        void setObservable();
      // Observer<Login> getObservable();
    }
}
