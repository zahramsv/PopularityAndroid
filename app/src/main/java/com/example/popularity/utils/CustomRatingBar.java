package com.example.popularity.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.core.content.res.ResourcesCompat;

import com.example.popularity.R;

public class CustomRatingBar extends LinearLayout implements View.OnClickListener {

    private static final int DEFAULT_MAX_RATING = 5;

    /**
     * The drawable used if no unfilled drawable resource is specified.
     */
    private static final int DEFAULT_UNFILLED_DRAWABLE_ID = R.drawable.ic_balloon_off;


    private static final int DEFAULT_FILLED_DRAWABLE_ID = R.drawable.ic_balloon;


    private int mMaxRating;

    private Drawable mFilledDrawable;


    private Drawable mUnfilledDrawable;

    /**
     * The rating specified by the user.
     */
    private int mRating = 0;

    public CustomRatingBar(Context context) {
        this(context, null, 0);
    }

    public CustomRatingBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomRatingBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.CustomRatingBar,
                0, 0);

        try {
            mMaxRating = typedArray.getInt(R.styleable.CustomRatingBar_maxRating,
                    DEFAULT_MAX_RATING);
            mFilledDrawable = typedArray.getDrawable(R.styleable.CustomRatingBar_filledDrawable);
            if (mFilledDrawable == null) {
                mFilledDrawable = ResourcesCompat.getDrawable(getResources(),
                        DEFAULT_FILLED_DRAWABLE_ID, null);
            }
            mUnfilledDrawable = typedArray.getDrawable(R.styleable.CustomRatingBar_unfilledDrawable);
            if (mUnfilledDrawable == null) {
                mUnfilledDrawable = ResourcesCompat.getDrawable(getResources(),
                        DEFAULT_UNFILLED_DRAWABLE_ID, null);
            }
        } finally {
            typedArray.recycle();
        }
        setSaveEnabled(true);
    }

    public int getRating() {
        return mRating;
    }

    @Override
    public void onClick(final View v) {
        mRating = (int) v.getTag();
        drawRatingViews();
        int eventType = Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN ?
                AccessibilityEvent.TYPE_ANNOUNCEMENT : AccessibilityEvent.TYPE_VIEW_FOCUSED;
        sendAccessibilityEvent(eventType);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        drawRatingViews();
    }

    @SuppressLint("StringFormatInvalid")
    @Override
    public void sendAccessibilityEvent(final int eventType) {
      /*  if (!AccessibilityUtils.isAccessibilityEnabled(getContext())) {
            return;
        }*/
        super.sendAccessibilityEvent(eventType);

        AccessibilityEvent event = AccessibilityEvent.obtain(eventType);
        // Get the Talkback text.
        event.getText().add(getContext().getResources().getString(R.string.app_name, mRating, mMaxRating));
        event.setEnabled(true);
        AccessibilityManager accessibilityManager = (AccessibilityManager) getContext()
                .getSystemService(Context.ACCESSIBILITY_SERVICE);
        accessibilityManager.sendAccessibilityEvent(event);
    }

    /**
     * Creates or updates the views used for creating a rating.
     */
    private void drawRatingViews() {
        if (this.getChildCount() == 0) {
            createRatingViews();
        } else {
            updateRatingViews();
        }
    }

    /**
     * Creates ({@link ImageView}s) used to submit a rating using unfilled drawables and adds them to
     * the layout.
     */
    @SuppressLint("StringFormatInvalid")
    private void createRatingViews() {
        for (int i = 0; i < mMaxRating; i++) {
            ImageView imageView = new ImageView(getContext());
            imageView.setLayoutParams(
                    new android.view.ViewGroup.LayoutParams(LayoutParams.WRAP_CONTENT,
                            LayoutParams.WRAP_CONTENT));
            int tagValue = i + 1;
            imageView.setTag(tagValue);
            imageView.setContentDescription(getContext().getString(
                    R.string.app_name, tagValue));
            imageView.setImageDrawable(mUnfilledDrawable);
            imageView.setOnClickListener(this);
            addView(imageView);
        }
    }

    /**
     * Updates ({@link ImageView}s) used to submit a rating, using filled drawables to denote the
     * rating created by the user.
     */
    private void updateRatingViews() {
        for (int i = 0; i < mMaxRating; i++) {
            ImageView view = (ImageView) this.getChildAt(i);
            view.setImageDrawable(i + 1 <= mRating ? mFilledDrawable : mUnfilledDrawable);
        }
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        SavedState savedState = new SavedState(superState);
        savedState.rating = mRating;
        return savedState;
    }

    @Override
    protected void onRestoreInstanceState(final Parcelable state) {
        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());

        mRating = ss.rating;
        drawRatingViews();
    }

    /**
     * Helper class to help retain state during orientation change.
     */
    private static class SavedState extends BaseSavedState {
        int rating;

        SavedState(Parcelable superState) {
            super(superState);
        }

        private SavedState(Parcel in) {
            super(in);
            rating = in.readInt();
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeInt(rating);
        }

        public static final Parcelable.Creator<SavedState> CREATOR
                = new Parcelable.Creator<SavedState>() {
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
    }

}
