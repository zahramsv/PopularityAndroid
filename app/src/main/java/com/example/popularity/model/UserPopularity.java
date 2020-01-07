package com.example.popularity.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UserPopularity implements Serializable {

    @SerializedName("rate_look")
    private String rate_look;
    @SerializedName("rate_fitness")
    private String rate_fitness;
    @SerializedName("rate_style")
    private String rate_style;
    @SerializedName("rate_popularity")
    private String rate_popularity;
    @SerializedName("rate_trustworthy")
    private String rate_trustworthy;
    @SerializedName("rate_personality")
    private String rate_personality;


    public String getRate_look() {
        return rate_look;
    }

    public void setRate_look(String rate_look) {
        this.rate_look = rate_look;
    }

    public String getRate_fitness() {
        return rate_fitness;
    }

    public void setRate_fitness(String rate_fitness) {
        this.rate_fitness = rate_fitness;
    }

    public String getRate_style() {
        return rate_style;
    }

    public void setRate_style(String rate_style) {
        this.rate_style = rate_style;
    }

    public String getRate_popularity() {
        return rate_popularity;
    }

    public void setRate_popularity(String rate_popularity) {
        this.rate_popularity = rate_popularity;
    }

    public String getRate_trustworthy() {
        return rate_trustworthy;
    }

    public void setRate_trustworthy(String rate_trustworthy) {
        this.rate_trustworthy = rate_trustworthy;
    }

    public String getRate_personality() {
        return rate_personality;
    }

    public void setRate_personality(String rate_personality) {
        this.rate_personality = rate_personality;
    }
}
