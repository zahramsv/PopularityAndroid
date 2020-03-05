package com.example.popularity.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.popularity.R;
import com.example.popularity.adapter.RateListAdapter;
import com.example.popularity.model.Rate;
import com.example.popularity.mvp.ShareMvp;
import com.example.popularity.presenter.SharePresenter;
import com.example.popularity.utils.ToolbarKind;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.List;


public class ShareFragment extends BaseFragment implements ShareMvp.View {


    private SharePresenter presenter;
    private RateListAdapter rateListAdapter;
    private List<Rate> rates;
    private RecyclerView rvYourRates;
    private AppCompatImageView imgUploadProfile, imgViewEditProfile;
    private LinearLayout linearUserProfile;
    private LinearLayout layoutScreenShot;
    private AppCompatButton btnShare;

    public ShareFragment() {

    }

    public static ShareFragment newInstance() {
        ShareFragment fragment = new ShareFragment();
        return fragment;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if (!hidden)
        {
            baseListener.changeToolbar(ToolbarKind.BACK,getString(R.string.share_your_rates));
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new SharePresenter(getContext(), baseListener, this);
        baseListener.changeToolbar(ToolbarKind.BACK, getString(R.string.share_your_rates));
        Bundle bundle = getArguments();
        rates = (List<Rate>) bundle.getSerializable("rates");
        rateListAdapter = new RateListAdapter(rates, getActivity());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_share, container, false);
        init(view);

        btnShare.setOnClickListener(view1 -> {
            imgViewEditProfile.setVisibility(View.GONE);
            View screenView = getActivity().getWindow().getDecorView().getRootView().findViewById(R.id.layoutScreenShot);
            presenter.takeScreenShot(screenView);

        });

        imgUploadProfile.setOnClickListener(view12 -> {
            selectAndCropImage();
        });

        imgViewEditProfile.setOnClickListener(view13 -> {
            selectAndCropImage();
        });

        return view;
    }

    private void init(View view) {
        btnShare = view.findViewById(R.id.btnShare);
        imgViewEditProfile = view.findViewById(R.id.imgViewEditProfile);
        imgViewEditProfile.setVisibility(View.GONE);
        layoutScreenShot = view.findViewById(R.id.layoutScreenShot);
        imgUploadProfile = view.findViewById(R.id.imgUploadProfile);
        rvYourRates = view.findViewById(R.id.rvYourRates);
        linearUserProfile = view.findViewById(R.id.linearUserProfile);
        GridLayoutManager layoutManager1 = new GridLayoutManager(getActivity(), 2, RecyclerView.VERTICAL, false);
        rvYourRates.setLayoutManager(layoutManager1);
        rvYourRates.setAdapter(rateListAdapter);
    }

    public void selectAndCropImage() {
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setAspectRatio(3, 1)
                .setFixAspectRatio(true)
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
            layoutScreenShot.setBackgroundResource(R.color.white);
        }
    }


    @Override
    public void shareScreenShot(Uri uri) {
        if (uri != null) {
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); // temp permission for receiving app to read this file
            shareIntent.setDataAndType(uri, getActivity().getContentResolver().getType(uri));
            shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
            startActivity(Intent.createChooser(shareIntent, "Choose an app"));
        }
    }
}
