package ir.mohad.popularity.rx;

import ir.mohad.popularity.model.Login;

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
