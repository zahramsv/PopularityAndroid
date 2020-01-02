package com.example.popularity.model;

public class RateSendDataModel {

    private String token,whom_username,whom_full_name,whom_avatar_url;
    private int social_type, rate_look, rate_fitness, rate_style, rate_personality, rate_trustworthy, rate_popularity, whom_rated_id, who_rate_id;


    public RateSendDataModel(String token, String whom_username, String whom_full_name, String whom_avatar_url, int social_type, int rate_look, int rate_fitness, int rate_style, int rate_personality, int rate_trustworthy, int rate_popularity, int whom_rated_id, int who_rate_id) {
        this.token = token;
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
        this.whom_rated_id = whom_rated_id;
        this.who_rate_id = who_rate_id;
    }


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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

    public int getRate_look() {
        return rate_look;
    }

    public void setRate_look(int rate_look) {
        this.rate_look = rate_look;
    }

    public int getRate_fitness() {
        return rate_fitness;
    }

    public void setRate_fitness(int rate_fitness) {
        this.rate_fitness = rate_fitness;
    }

    public int getRate_style() {
        return rate_style;
    }

    public void setRate_style(int rate_style) {
        this.rate_style = rate_style;
    }

    public int getRate_personality() {
        return rate_personality;
    }

    public void setRate_personality(int rate_personality) {
        this.rate_personality = rate_personality;
    }

    public int getRate_trustworthy() {
        return rate_trustworthy;
    }

    public void setRate_trustworthy(int rate_trustworthy) {
        this.rate_trustworthy = rate_trustworthy;
    }

    public int getRate_popularity() {
        return rate_popularity;
    }

    public void setRate_popularity(int rate_popularity) {
        this.rate_popularity = rate_popularity;
    }

    public int getWhom_rated_id() {
        return whom_rated_id;
    }

    public void setWhom_rated_id(int whom_rated_id) {
        this.whom_rated_id = whom_rated_id;
    }

    public int getWho_rate_id() {
        return who_rate_id;
    }

    public void setWho_rate_id(int who_rate_id) {
        this.who_rate_id = who_rate_id;
    }
}
