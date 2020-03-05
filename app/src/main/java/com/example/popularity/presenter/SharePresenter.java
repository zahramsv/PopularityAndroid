package com.example.popularity.presenter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.View;

import androidx.core.content.FileProvider;

import com.example.popularity.mvp.ShareMvp;
import com.example.popularity.myInterface.MainActivityTransaction;
import com.example.popularity.utils.MyApp;

import java.io.FileOutputStream;

public class SharePresenter implements ShareMvp.Presenter {


    private FileOutputStream outputStream2;
    private Context context;
    private MainActivityTransaction.Components baseListener;
    private ShareMvp.View shareView;

    public SharePresenter(Context context, MainActivityTransaction.Components baseListener, ShareMvp.View view) {
        this.context = context;
        this.baseListener = baseListener;
        this.shareView = view;
        this.context = MyApp.getInstance().getBaseContext().getApplicationContext();
    }

    @Override
    public void takeScreenShot(View view) {


        try {
            // image naming and path  to include sd card  appending name you choose for file
            view.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
            view.setDrawingCacheEnabled(false);

            outputStream2 = MyApp.getInstance().getBaseComponent().provideOutputSteam();
            int quality = 100;
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream2);
            outputStream2.flush();
            outputStream2.close();
           // openScreenShot(MyApp.getInstance().getBaseComponent().provideFile());
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            Uri uri = FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".provider", MyApp.getInstance().getBaseComponent().provideFile());
            shareView.shareScreenShot(uri);
        } catch (Throwable e) {
            // Several error may come out with file handling or DOM
            e.printStackTrace();

        }
    }

   /* @Override
    public void openScreenShot(File imageFile) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        Uri uri = FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".provider", imageFile);
        shareView.shareScreenShot(uri);
    }*/
}
