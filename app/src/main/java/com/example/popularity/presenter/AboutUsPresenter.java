package com.example.popularity.presenter;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.widget.Toast;

import com.example.popularity.mvp.AboutUsMvp;
import com.example.popularity.myInterface.MainActivityTransaction;
import com.example.popularity.utils.ShowMessageType;

import static java.security.AccessController.getContext;

public class AboutUsPresenter implements AboutUsMvp.Presenter {

    private AboutUsMvp.View view;
    private MainActivityTransaction.Components baseComponents;
    public AboutUsPresenter(AboutUsMvp.View view, MainActivityTransaction.Components baseComponents) {
        this.view = view;
        this.baseComponents = baseComponents;
    }

    @Override
    public void openFaceBookPage() {
        try {
            if (isAppInstalled(view.getViewContext(), "com.facebook.orca") || isAppInstalled(view.getViewContext(), "com.facebook.katana")
                    || isAppInstalled(view.getViewContext(), "com.example.facebook") || isAppInstalled(view.getViewContext(), "com.facebook.android")) {

               view.getViewContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("fb://page/{fb_page_numerical_id}")));
            } else {
                view.getViewContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/{mohammadHadi}")));
            }
        }catch (Exception e){e.printStackTrace();}
    }

    @Override
    public void openTwitterPage() {

        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://twitter.com/{zmosavi01@gmail.com}"));
            intent.setPackage("com.twitter.android");
            view.getViewContext().startActivity(intent);
        }
        catch (ActivityNotFoundException anfe) {
            view.getViewContext().startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://twitter.com/{zmosavi01@gmail.com}")));
        }
    }

    @Override
    public void openInstagramPage() {

        Uri uri = Uri.parse("http://instagram.com/_u/xahraa__");
        Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);

        likeIng.setPackage("com.instagram.android");

        try {
           view.getViewContext().startActivity(likeIng);
        } catch (ActivityNotFoundException e) {
            view.getViewContext().startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://instagram.com/xahraa__")));
        }
    }

    @Override
    public void openLinedInPage() {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("linkedin://zmosavi01@gmail.com"));
            intent.setPackage("com.linkedin.android");
            view.getViewContext().startActivity(intent);
        }
        catch (ActivityNotFoundException anfe)
        {
            baseComponents.showMessage(ShowMessageType.TOAST,"Something wrong happened");
            //view.getViewContext().startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("linkedin://zmosavi01@gmail.com")));
        }

    }

    @Override
    public void openWhatsAppPage() {

        PackageManager pm=view.getViewContext().getPackageManager();
        try {

            Intent waIntent = new Intent(Intent.ACTION_SEND);
            waIntent.setType("text/plain");
            String text = "YOUR TEXT HERE";
            PackageInfo info=pm.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA);
            waIntent.setPackage("com.whatsapp");

            waIntent.putExtra(Intent.EXTRA_TEXT, text);
           view.getViewContext().startActivity(Intent.createChooser(waIntent, "Share with"));

        } catch (PackageManager.NameNotFoundException e) {
            baseComponents.showMessage(ShowMessageType.TOAST,"WhatsApp not Installed");

        }
    }

    @Override
    public void openYoutubePage() {

        try {
            Intent intent =new Intent(Intent.ACTION_VIEW);
            intent.setPackage("com.google.android.youtube");
            intent.setData(Uri.parse("https://www.youtube.com/channel/{androidProgrammer"));
            view.getViewContext().startActivity(intent);
        } catch (ActivityNotFoundException anfe) {
            view.getViewContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/channel/{androidProgrammer")));
        }
    }

    @Override
    public boolean isAppInstalled(Context context, String packageName) {
        return false;
    }
}
