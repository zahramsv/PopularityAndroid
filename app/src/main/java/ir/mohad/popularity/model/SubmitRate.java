package ir.mohad.popularity.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SubmitRate implements Serializable {


    @SerializedName("token")
    private String token;

    @SerializedName("who_rate_id")
    private String who_rate_id;

    @SerializedName("whom_rated_id")
    private String whom_rated_id;


    @SerializedName("whom_username")
    private String whom_username;

    @SerializedName("whom_full_name")
    private String whom_full_name;

    @SerializedName("whom_avatar_url")
    private String whom_avatar_url;

    @SerializedName("social_type")
    private int social_type;

    @SerializedName("rate_look")
    private float rate_look;

    @SerializedName("rate_fitness")
    private float rate_fitness;

    @SerializedName("rate_style")
    private float rate_style;

    @SerializedName("rate_personality")
    private float rate_personality;

    @SerializedName("rate_trustworthy")
    private float rate_trustworthy;

    @SerializedName("rate_popularity")
    private float rate_popularity;


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getWho_rate_id() {
        return who_rate_id;
    }

    public void setWho_rate_id(String who_rate_id) {
        this.who_rate_id = who_rate_id;
    }

    public String getWhom_rated_id() {
        return whom_rated_id;
    }

    public void setWhom_rated_id(String whom_rated_id) {
        this.whom_rated_id = whom_rated_id;
    }

    public String getWhom_username() {
        return whom_username;
    }

    public void setWhom_username(String whom_username) {
        this.whom_username = whom_username;
    }

    public String getWhom_full_name() {
        return whom_full_name;
    }

    public void setWhom_full_name(String whom_full_name) {
        this.whom_full_name = whom_full_name;
    }

    public String getWhom_avatar_url() {
        return whom_avatar_url;
    }

    public void setWhom_avatar_url(String whom_avatar_url) {
        this.whom_avatar_url = whom_avatar_url;
    }

    public int getSocial_type() {
        return social_type;
    }

    public void setSocial_type(int social_type) {
        this.social_type = social_type;
    }

    public float getRate_look() {
        return rate_look;
    }

    public void setRate_look(float rate_look) {
        this.rate_look = rate_look;
    }

    public float getRate_fitness() {
        return rate_fitness;
    }

    public void setRate_fitness(float rate_fitness) {
        this.rate_fitness = rate_fitness;
    }

    public float getRate_style() {
        return rate_style;
    }

    public void setRate_style(float rate_style) {
        this.rate_style = rate_style;
    }

    public float getRate_personality() {
        return rate_personality;
    }

    public void setRate_personality(float rate_personality) {
        this.rate_personality = rate_personality;
    }

    public float getRate_trustworthy() {
        return rate_trustworthy;
    }

    public void setRate_trustworthy(float rate_trustworthy) {
        this.rate_trustworthy = rate_trustworthy;
    }

    public float getRate_popularity() {
        return rate_popularity;
    }

    public void setRate_popularity(float rate_popularity) {
        this.rate_popularity = rate_popularity;
    }

    public SubmitRate(String token, String who_rate_id, String whom_rated_id, String whom_username, String whom_full_name, String whom_avatar_url, int social_type, float rate_look, float rate_fitness, float rate_style, float rate_personality, float rate_trustworthy, float rate_popularity) {
        this.token = token;
        this.who_rate_id = who_rate_id;
        this.whom_rated_id = whom_rated_id;
        this.whom_username = whom_username;
        this.whom_full_name = whom_full_name;
        this.whom_avatar_url = whom_avatar_url;
        this.social_type = social_type;
        this.rate_look = rate_look;
        this.rate_fitness = rate_fitness;
        this.rate_style = rate_style;
        this.rate_personality = rate_personality;
        this.rate_trustworthy = rate_trustworthy;
        this.rate_popularity = rate_popularity;
    }
}
