package com.example.popularity.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;

import androidx.annotation.NonNull;

import com.example.popularity.GetLoginDataService;
import com.example.popularity.logic.SocialLoginLogic;
import com.example.popularity.model.User;
import com.example.popularity.model.SocialRootModel;
import com.example.popularity.utils.BaseFragment;
import com.example.popularity.R;
import com.example.popularity.utils.RetrofitInstance;
import com.example.popularity.utils.ToolbarState;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginFragment extends BaseFragment {


    public static final String ACCESS_TOKEN_KEY = "";
    Button instagram_btn;
    private ToolbarState toolbarState;

    public LoginFragment() {
        // Required empty public constructor
    }


    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        return fragment;
    }

    CallbackManager callbackManager = CallbackManager.Factory.create();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        toolbarState.toolbarState(true);
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        clickEvents(view);

        String EMAIL = "email,friends";


        LoginButton loginButton = view.findViewById(R.id.login_button);
      /*  loginButton.setReadPermissions("email", "user_friends", "public_profile");
        loginButton.setFragment(this);


        LoginManager.getInstance().logInWithReadPermissions(
                this,
                Arrays.asList("email", "friends"));


        Log.i("app_tag", AccessToken.getCurrentAccessToken().getPermissions().toString());
        Log.i("app_tag", AccessToken.getCurrentAccessToken().getDeclinedPermissions().toString());*/


        loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                RetrofitInstance retrofitInstance = new RetrofitInstance();
                Retrofit retrofit = retrofitInstance.getRetrofitInstance();

                SocialLoginLogic socialLoginLogic = new SocialLoginLogic();
                socialLoginLogic.GetFirstUserLoginData();


                GetLoginDataService getLoginDataService = retrofit.create(GetLoginDataService.class);
                getLoginDataService.getLoginData(socialLoginLogic.GetFirstUserLoginData()).enqueue(new Callback<SocialRootModel>() {
                    @Override
                    public void onResponse(Call<SocialRootModel> call, Response<SocialRootModel> response) {

                        Log.i("app_tag", response.toString());
                        if((response.isSuccessful())){
                            SocialRootModel obr = response.body();

                            SharedPreferences prefs = null;
                            User data = obr.getData();
                            SharedPreferences.Editor editor = prefs.edit();
                            editor.putString("user_name", data.getUsername());
                            editor.commit();
                            editor.apply();

                            Log.i("app_tag", "info: "+obr.getCode());

                        }else{
                            Log.i("app_tag", "error");
                        }
                    }

                    @Override
                    public void onFailure(Call<SocialRootModel> call, Throwable t) {

                        Log.i("app_tag", t.getMessage().toString());
                    }
                });

            }
        });

        // Callback registration
        /*loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.i("app_tag","onSuccess: "+loginResult.getAccessToken());
                AccessToken.setCurrentAccessToken(loginResult.getAccessToken());
                getFacebookData();

            }

            @Override
            public void onCancel() {
                Log.i("app_tag","onCancel");
            }

            @Override
            public void onError(FacebookException exception) {
                Log.i("app_tag","onError: "+exception.getMessage());
            }
        });*/


        //getUserFriends();
        return view;

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        Log.i("app_tag", requestCode + "");
        Log.i("app_tag", resultCode + "");
        super.onActivityResult(requestCode, resultCode, data);
    }


    //Fragment Button Click
    public void clickEvents(View v) {

        switch (v.getId()) {
            case R.id.instagram_btn:
                break;
        }

    }


    private void getFacebookData() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        boolean isLoggedIn = accessToken != null && !accessToken.isExpired();
        if (isLoggedIn) {
            GraphRequest request = GraphRequest.newMeRequest(
                    accessToken,
                    new GraphRequest.GraphJSONObjectCallback() {
                        @Override
                        public void onCompleted(JSONObject object, GraphResponse response) {
                            Log.i("app_tag", response.toString());
                            Log.i("app_tag", object.toString());

                            //Call loginBySocial Mahad
                            //parse Recived Data
                        }
                    });

            Bundle parameters = new Bundle();
            parameters.putString("fields", "id,name");
            request.setParameters(parameters);
            request.executeAsync();

        }
    }


    private void getUserFriends() {
        AccessToken token = AccessToken.getCurrentAccessToken();
        boolean isLoggedIn = token != null && !token.isExpired();
        if (isLoggedIn) {
            GraphRequest graphRequest = GraphRequest.newMeRequest(token, new GraphRequest.GraphJSONObjectCallback() {
                @Override
                public void onCompleted(JSONObject jsonObject, GraphResponse graphResponse) {
                    // try {
                    Log.i("app_tag", graphResponse.toString());
                    Log.i("app_tag", jsonObject.toString());

                    try {
                        String personId = jsonObject.getString("id");
                        myNewGraphReq(personId);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                       /* JSONArray jsonArrayFriends = jsonObject.getJSONObject("friendlist").getJSONArray("data");
                        JSONObject friendlistObject = jsonArrayFriends.getJSONObject(0);
                        String friendListID = friendlistObject.getString("id");
                        myNewGraphReq(friendListID);*/

//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
                }
            });
            Bundle param = new Bundle();
            param.putString("fields", "friends");
            graphRequest.setParameters(param);
            graphRequest.executeAsync();
        }
    }


    private void myNewGraphReq(String friendlistId) {
        final String graphPath = "/" + friendlistId + "/friends";
        AccessToken token = AccessToken.getCurrentAccessToken();
        GraphRequest request = new GraphRequest(token, graphPath, null, HttpMethod.GET, new GraphRequest.Callback() {
            @Override
            public void onCompleted(GraphResponse graphResponse) {
                JSONObject object = graphResponse.getJSONObject();
                try {
                    // JSONArray arrayOfUsersInFriendList= object.getJSONArray("data");
                    /* Do something with the user list */
                    /* ex: get first user in list, "name" */
                    //JSONObject user = arrayOfUsersInFriendList.getJSONObject(0);
                    //String usersName = user.getString("name");
                    Log.i("app_tag", object.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        Bundle param = new Bundle();
        param.putString("fields", "name");
        request.setParameters(param);
        request.executeAsync();
    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        return super.onCreateAnimation(transit, enter, nextAnim);
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof MenuDrawerFragment.OnSlidingMenuFragmentListener) {
            toolbarState = (ToolbarState) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnAccountingMainFragmentInteractionListener");
        }
    }
}
