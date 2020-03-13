package ir.mohad.popularity.model.repository;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import ir.mohad.popularity.model.User;
import ir.mohad.popularity.model.UserPopularity;

public class SharedPrefsRepository {

    private Context context;
    SharedPreferences sharedPref = null;

    public final static String USER_DATA = "user_data";
    public final static String LOGOUT_STATUS = "logout_status";
    public final static String LANGUAGE = "language";
    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";


    public SharedPrefsRepository(Context context) {
        this.context = context;
        sharedPref = context.getSharedPreferences(USER_DATA, Context.MODE_PRIVATE);
    }

    public void SaveLogoutStatus(boolean twitterLogin, boolean phoneNumberLogin) {
        sharedPref = context.getSharedPreferences(LOGOUT_STATUS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean("phone_number_login", phoneNumberLogin);
        editor.putBoolean("twitter_login", twitterLogin);
    }

    public void setApplicationLanguageWithUser(String language) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(LANGUAGE, language);
        editor.commit();
        editor.apply();

    }

    public String getApplicationLanguage() {
        return sharedPref.getString(LANGUAGE,"en");
        /*SharedPreferences prefs = context.getSharedPreferences("CommonPrefs",
                Activity.MODE_PRIVATE);
        String language = prefs.getString(LANGUAGE, "");
        return language;*/
    }

    public void SaveUser(User user, UserPopularity userPopularity) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("rate_look", userPopularity.getRate_look());
        editor.putString("rate_fitness", userPopularity.getRate_fitness());
        editor.putString("rate_style", userPopularity.getRate_style());
        editor.putString("rate_personality", userPopularity.getRate_personality());
        editor.putString("rate_trustworthy", userPopularity.getRate_trustworthy());
        editor.putString("rate_popularity", userPopularity.getRate_popularity());
        editor.putString("user_name", user.getUsername());
        editor.putString("social_primary", user.getSocial_primary());
        editor.putString("token", user.getToken());
        editor.putString("full_name", user.getFull_name());
        editor.putString("avatar_url", user.getAvatar_url());
        ///// editor.putString("rates_summary_sum", user.getRates_summary_sum());
        editor.putInt("rates_count", user.getRates_count());
        editor.putInt("rated_count", user.getRated_count());
        editor.putString("social_type", user.getSocial_primary());
        editor.putString("created_at", user.getCreated_at());
        editor.putString("updated_at", user.getUpdated_at());


        editor.apply();


    }

    public void DeleteUser() {
        sharedPref = context.getSharedPreferences(USER_DATA, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.clear();
        editor.apply();

    }

    public void saveLoginKind(String loginKind) {

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("login_kind", loginKind);
        editor.commit();
        editor.apply();
    }

    public String getLoginKind() {
        return sharedPref.getString("login_kind", "");
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
        editor.apply();
    }

    public boolean isFirstTimeLaunch() {
        return sharedPref.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }


}
