package ir.mohad.popularity.mvp;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

public interface IntroMvp {

    interface View
    {
        void launchHomeScreen();
       // ViewPager.OnPageChangeListener viewPagerPageChangeListener();
        int getItem(int i);
        Context getViewContext();
        AppCompatActivity getFrgActivity();

    }
    interface Presenter
    {
        void addBottomDots(int currentPage);
        void changeStatusBarColor();
    }
}
