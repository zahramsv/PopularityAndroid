package com.example.popularity.fragment;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.popularity.R;
import com.example.popularity.model.BaseResponse;
import com.example.popularity.model.Friend;
import com.example.popularity.model.SubmitRate;
import com.example.popularity.model.User;
import com.example.popularity.myInterface.ApiServices;
import com.example.popularity.utils.RetrofitInstance;
import com.example.popularity.utils.ToolbarKind;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class RateFragment extends BaseFragment {

    private TextView userName;



    public static RateFragment newInstance() {
        RateFragment fragment = new RateFragment();
        return fragment;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden){
            baseListener.changeToolbar(ToolbarKind.BACK,getString(R.string.rate_us));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        baseListener.changeToolbar(ToolbarKind.BACK,getString(R.string.rate_toolbar));
        Bundle bundle=getArguments();
        Friend friend= (Friend) bundle.getSerializable("Friend");
        User user= (User) bundle.getSerializable("User");
        View view= inflater.inflate(R.layout.fragment_rate, container, false);
        AppCompatRatingBar look=view.findViewById(R.id.rtLook);
        AppCompatRatingBar style=view.findViewById(R.id.rtStyle);
        AppCompatRatingBar popularity=view.findViewById(R.id.rtPopularity);
        AppCompatRatingBar fitness=view.findViewById(R.id.rtFitness);
        AppCompatRatingBar trustworthy=view.findViewById(R.id.rtTrustworthy);
        AppCompatRatingBar personality=view.findViewById(R.id.rtPersonality);
        userName= view.findViewById(R.id.username);
        userName.setText(friend.getName() + " - "+friend.getUserId()+" - "+user.getFull_name());
        view.findViewById(R.id.btnSave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SubmitRate submitRate=new SubmitRate(user.getToken(), user.getSocial_primary(),friend.getUserId()
                        ,friend.getName(),friend.getName(),
                        user.getAvatar_url(),user.getSocial_type(),look.getRating(),
                        fitness.getRating(),style.getRating(),personality.getRating(),trustworthy.getRating(),
                        popularity.getRating());

                RetrofitInstance retrofitInstance=new RetrofitInstance();
                Retrofit retrofit=retrofitInstance.getRetrofitInstance();
                ApiServices friendsRate=retrofit.create(ApiServices.class);
                friendsRate.submitRateToFriend(submitRate).enqueue(new Callback<BaseResponse<String>>() {
                    @Override
                    public void onResponse(Call<BaseResponse<String>> call, Response<BaseResponse<String>> response) {

                        baseListener.showMessage("امتیاز دهی با موفقیت انجام شد.");
                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                getFragmentManager().popBackStackImmediate();
                                FragmentManager fm = getFragmentManager();
                                Fragment fragment = fm.findFragmentById(R.id.your_placeholder);
                                FragmentTransaction ft = fm.beginTransaction();
                                if(fragment!=null){
                                    ft.show(fragment);
                                    ft.commit();
                                }
                            }
                        }, 2000);

                    }

                    @Override
                    public void onFailure(Call<BaseResponse<String>> call, Throwable t) {

                        baseListener.showMessage("در ثبت امتیاز مشکل بوجود آمده است.");
                    }
                });

            }
        });
        return view;
    }



}
