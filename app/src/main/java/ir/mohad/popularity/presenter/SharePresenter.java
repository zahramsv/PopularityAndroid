package ir.mohad.popularity.presenter;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;

import ir.mohad.popularity.R;
import ir.mohad.popularity.fragment.ShareFragment;
import ir.mohad.popularity.model.Rate;
import ir.mohad.popularity.mvp.ShareMvp;
import ir.mohad.popularity.myInterface.MainActivityTransaction;
import ir.mohad.popularity.utils.MyApp;
import ir.mohad.popularity.utils.ShowMessageType;
import com.tedpark.tedpermission.rx2.TedRx2Permission;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

public class SharePresenter implements ShareMvp.Presenter {

    private FileOutputStream outputStream2;
    private Context context;
    private MainActivityTransaction.Components baseListener;
    private ShareMvp.View shareView;
    private List<Rate> rates;

    public SharePresenter(Context context, MainActivityTransaction.Components baseListener, ShareMvp.View view) {
        this.context = context;
        this.baseListener = baseListener;
        this.shareView = view;
        this.context = MyApp.getInstance().getBaseContext().getApplicationContext();
    }

    private String SHARE_IMAGE_FILE_NAME = "popularity.jpg";

    @Override
    public boolean takeScreenShot(View view) {
        try {
            view.setDrawingCacheEnabled(true);
            Bitmap bit = view.getDrawingCache(true).copy(Bitmap.Config.ARGB_8888, false);
            view.destroyDrawingCache();
            File sdCard = new File(view.getContext().getApplicationContext().getFilesDir(), "shared");
            if (!sdCard.exists()) sdCard.mkdir();
            File file = new File(sdCard, SHARE_IMAGE_FILE_NAME);
            FileOutputStream fos = new FileOutputStream(file);
            bit.compress(Bitmap.CompressFormat.JPEG, 75, fos);

            Intent shareIntent = getShareIntent(file.getPath());
            shareView.shareImageOnSocial(shareIntent);
            return true;

        } catch (Throwable e) {
            e.printStackTrace();
            return false;
        }
    }


    private Intent getShareIntent(String medeImagePath) {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.setType("image/*");
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        String path = null;
        try {
            path = MediaStore.Images.Media.insertImage(
                    context.getContentResolver(),
                    medeImagePath,
                    SHARE_IMAGE_FILE_NAME,
                    "Identified image"
            );
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String tempShareImagePathInGallery = getRealPathFromUriString(path);

        shareIntent.putExtra(
                Intent.EXTRA_STREAM, Uri.parse(path)
        );
        return shareIntent;
    }


    private String getRealPathFromUriString(String contentPath) {
        Cursor cursor = null;
        try {
            Uri uri = Uri.parse(contentPath);
            String[] project = new String[]{MediaStore.Images.Media.DATA};
            cursor = context.getContentResolver().query(uri, project, null, null, null);
            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(columnIndex);
        } finally {
            cursor.close();
        }
    }

    @Override
    public void selectAndCropImage() {
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setAspectRatio(3, 1)
                .setFixAspectRatio(true)
                .start(shareView.getViewContext(), ShareFragment.newInstance());
    }

    @Override
    public void getGalleryAccessPermission() {
        getPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    @Override
    public void setBundleContent(Bundle bundle) {
        rates = (List<Rate>) bundle.getSerializable("rates");
        shareView.setRatesList(rates);
    }

    @SuppressLint("CheckResult")
    private void getPermission(String permission) {
        TedRx2Permission.with(shareView.getViewContext())
                .setRationaleTitle(R.string.hint_get_permissions)
                //.setRationaleMessage(R.string.rationale_message) // "we need permission for read contact and find your location"
                .setPermissions(permission)
                .request()
                .subscribe(tedPermissionResult -> {
                    if (tedPermissionResult.isGranted()) {
                        Log.d("app_tag", "granted");
                    } else {

                        Log.d("app_tag", "not granted");
                        baseListener.showMessage(ShowMessageType.TOAST, shareView.getViewContext().getString(R.string.hint_you_should_confirm_permissions));
                        shareView.onBackPressed();
                    }
                }, throwable -> {
                    Log.d("app_tag", "throw error");

                });
    }

}
