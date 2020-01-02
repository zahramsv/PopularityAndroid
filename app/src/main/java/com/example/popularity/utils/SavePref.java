package com.example.popularity.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.popularity.model.User;

public  class SavePref {

    public final static String USER_DATA = "user_data";
    SharedPreferences sharedPref =null;
    public  void SaveUser(Context context, User user) {
        sharedPref= context.getSharedPreferences(USER_DATA,0);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("user_name", user.getUsername());
        editor.putString("social_primary", user.getSocial_primary());
        editor.putString("token", user.getToken());
        editor.putString("full_name", user.getFull_name());
        editor.putString("avatar_url", user.getAvatar_url());
        editor.putString("rates_summary_sum", user.getRates_summary_sum());
        editor.putInt("rates_count", user.getRates_count());
        editor.putInt("rated_count", user.getRated_count());
        editor.putString("social_type", user.getSocial_primary());
        editor.putString("created_at", user.getCreated_at());
        editor.putString("updated_at", user.getUpdated_at());
        editor.apply();

    }

    public String getUser(String key) {
        if (sharedPref!= null) {
            return sharedPref.getString(key, "");
        }
        return "";
    }
}
