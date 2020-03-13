package ir.mohad.popularity.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ir.mohad.popularity.R;
import ir.mohad.popularity.adapter.RateListAdapter;
import ir.mohad.popularity.model.Rate;
import ir.mohad.popularity.mvp.ShareMvp;
import ir.mohad.popularity.presenter.SharePresenter;
import ir.mohad.popularity.utils.ShowMessageType;
import ir.mohad.popularity.utils.ToolbarKind;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.List;


public class ShareFragment extends BaseFragment implements ShareMvp.View {

    private SharePresenter presenter;
    private RateListAdapter rateListAdapter;
    private RecyclerView rvYourRates;
    private AppCompatImageView imgUploadProfile, imgViewEditProfile;
    private LinearLayout linearUserProfile;
    private CardView layoutScreenShot;
    private AppCompatButton btnShare;
    private RelativeLayout relativeLayoutShare;
    private  View view;
    private boolean intentBack=false;

    public static ShareFragment newInstance() {
        ShareFragment fragment = new ShareFragment();
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (intentBack)
        {
            view.findViewById(R.id.imgViewEditProfile).setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if (!hidden) {
            baseListener.changeToolbar(ToolbarKind.BACK, getString(R.string.share_your_rates));
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = new SharePresenter(getContext(), baseListener, this);
        baseListener.changeToolbar(ToolbarKind.BACK, getString(R.string.share_your_rates));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_share, container, false);
        init(view);

        presenter.setBundleContent(getArguments());

        btnShare.setOnClickListener(view1 -> {
            imgViewEditProfile.setVisibility(View.GONE);
            View shareView = layoutScreenShot;
            intentBack=presenter.takeScreenShot(shareView);
           // presenter.takeScreenShot(shareView);

        });

        imgUploadProfile.setOnClickListener(view12 -> {
            selectAndCropImage();


        });

        imgViewEditProfile.setOnClickListener(view13 -> {
            selectAndCropImage();

        });

        presenter.getGalleryAccessPermission();

        return view;
    }

    @Override
    public void onBackPressed() {
        getActivity().onBackPressed();
    }

    @Override
    public void setRatesList(List<Rate> rates) {
        rateListAdapter = new RateListAdapter(rates, getActivity());
        GridLayoutManager layoutManager= new GridLayoutManager(getActivity(), 2, RecyclerView.VERTICAL, false);
        rvYourRates.setLayoutManager(layoutManager);
        rvYourRates.setAdapter(rateListAdapter);
    }

    private void init(View view) {
        btnShare = view.findViewById(R.id.btnShare);
        imgViewEditProfile = view.findViewById(R.id.imgViewEditProfile);
        imgViewEditProfile.setVisibility(View.GONE);
        layoutScreenShot = view.findViewById(R.id.layoutScreenShot);
        imgUploadProfile = view.findViewById(R.id.imgUploadProfile);
        rvYourRates = view.findViewById(R.id.rvYourRates);
        linearUserProfile = view.findViewById(R.id.linearUserProfile);
       // relativeLayoutShare=view.findViewById(R.id.relativeLayoutShare);
    }

    public void selectAndCropImage() {
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setAspectRatio(3, 1)
                .setFixAspectRatio(true)
                .setBackgroundColor(R.color.transparent)
                .start(getContext(), this);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            Uri resultUri = result.getUri();
            imgUploadProfile.setImageBitmap(BitmapFactory.decodeFile(resultUri.getPath()));
            Drawable myDrawable = imgUploadProfile.getDrawable();
            imgUploadProfile.setImageBitmap(null);
            linearUserProfile.setBackground(myDrawable);
            imgViewEditProfile.setVisibility(View.VISIBLE);
            //fixme ? when comment this two line corner of layout back
            /*layoutScreenShot.setBackgroundResource(R.color.colorPrimary);
            relativeLayoutShare.setBackgroundColor(R.color.colorPrimary);*/
        }
    }

    @Override
    public AppCompatActivity getFragActivity() {
        return (AppCompatActivity) getActivity();
    }

    @Override
    public Context getViewContext() {
        return getContext();
    }

    @Override
    public void shareImageOnSocial(Intent intent) {
        startActivity(Intent.createChooser(intent, getString(R.string.choose_app)));
    }
}
