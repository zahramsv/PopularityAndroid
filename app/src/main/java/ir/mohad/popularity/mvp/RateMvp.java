package ir.mohad.popularity.mvp;

import android.content.Context;

import ir.mohad.popularity.model.SubmitRate;
import ir.mohad.popularity.model.User;

public interface RateMvp {

    interface View {
        void comeBackToHomeAfterRateDone();
        Context getViewContext();
    }

    interface Presenter {
        void submitRate(SubmitRate submitRate );
        User getCurrentUser();

    }
}

