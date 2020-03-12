package com.example.popularity.presenter;

import android.graphics.Color;
import android.os.Build;
import android.text.Html;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.popularity.R;
import com.example.popularity.mvp.IntroMvp;
import com.example.popularity.myInterface.MainActivityTransaction;

public class IntroPresenter implements IntroMvp.Presenter {


    MainActivityTransaction.Components baseComponent;
    IntroMvp.View view;
    private TextView[] dots;
    private int[] layouts;
    private LinearLayout dotsLayout;

    public IntroPresenter(MainActivityTransaction.Components baseComponent, IntroMvp.View view) {
        this.baseComponent = baseComponent;
        this.view = view;
    }

    @Override
    public void addBottomDots(int currentPage) {

        layouts = new int[]{
                R.layout.intro_slide1,
                R.layout.intro_slide2,
                R.layout.intro_slide3,
                R.layout.intro_slide4};

        dotsLayout = view.getFrgActivity().findViewById(R.id.layoutDots);
        dots = new TextView[layouts.length];




        int[] colorsActive =view.getViewContext().getResources().getIntArray(R.array.array_dot_active);
        int[] colorsInactive = view.getViewContext().getResources().getIntArray(R.array.array_dot_inactive);

        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(view.getViewContext());
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(colorsInactive[currentPage]);
            dotsLayout.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[currentPage].setTextColor(colorsActive[currentPage]);
    }

    @Override
    public void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = view.getFrgActivity().getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }
}
