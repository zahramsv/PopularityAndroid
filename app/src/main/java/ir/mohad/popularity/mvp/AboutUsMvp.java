package ir.mohad.popularity.mvp;

import android.content.Context;

public interface AboutUsMvp {

    interface View{
        void setDescriptionAboutUs(String desc);
        Context getViewContext();
    }

    interface Presenter{

        void openFaceBookPage();
        void openTwitterPage();
        void openInstagramPage();
        void openLinedInPage();
        void openWhatsAppPage();
        void openYoutubePage();
        boolean isAppInstalled(Context context, String packageName);
    }
}
