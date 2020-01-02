package com.example.popularity.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class User implements Serializable {

    @SerializedName("social_primary")
    private String social_primary;
    @SerializedName("username")
    private String username;
    @SerializedName("token")
    private String token;

    @SerializedName("full_name")
    private String full_name;
    @SerializedName("avatar_url")
    private String avatar_url;
    @SerializedName("rates_summary_sum")
    private String rates_summary_sum;

    @SerializedName("updated_at")
    private String updated_at;
    @SerializedName("created_at")
    private String created_at;
    @SerializedName("social_type")
    private int social_type;
    @SerializedName("rated_count")
    private int rated_count;
    @SerializedName("rates_count")
    private int rates_count;


    public User(String social_primary, String username, String token, String full_name, String avatar_url, String rates_summary_sum, String updated_at, String created_at, int social_type, int rated_count, int rates_count) {
        this.social_primary = social_primary;
        this.username = username;
        this.token = token;
        this.full_name = full_name;
        this.avatar_url = avatar_url;
        this.rates_summary_sum = rates_summary_sum;
        this.updated_at = updated_at;
        this.created_at = created_at;
        this.social_type = social_type;
        this.rated_count = rated_count;
        this.rates_count = rates_count;
    }

    public String getSocial_primary() {
        return social_primary;
    }

    public void setSocial_primary(String social_primary) {
        this.social_primary = social_primary;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public String getRates_summary_sum() {
        return rates_summary_sum;
    }

    public void setRates_summary_sum(String rates_summary_sum) {
        this.rates_summary_sum = rates_summary_sum;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public int getSocial_type() {
        return social_type;
    }

    public void setSocial_type(int social_type) {
        this.social_type = social_type;
    }

    public int getRated_count() {
        return rated_count;
    }

    public void setRated_count(int rated_count) {
        this.rated_count = rated_count;
    }

    public int getRates_count() {
        return rates_count;
    }

    public void setRates_count(int rates_count) {
        this.rates_count = rates_count;
    }
}
