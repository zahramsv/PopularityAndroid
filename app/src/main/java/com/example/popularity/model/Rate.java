package com.example.popularity.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Rate implements Serializable {

    @SerializedName("attribute")
    private String attribute;
    @SerializedName("Rate")
    private int Rate;

    public Rate(String attribute, int rate) {
        this.attribute = attribute;
        Rate = rate;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public int getRate() {
        return Rate;
    }

    public void setRate(int rate) {
        Rate = rate;
    }
}

