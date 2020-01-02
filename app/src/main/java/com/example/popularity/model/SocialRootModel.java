package com.example.popularity.model;

import com.google.gson.annotations.SerializedName;

public class SocialRootModel {

    @SerializedName("data")
    private User data;
    @SerializedName("code")
    private int code;


    public User getData() {
        return data;
    }

    public void setData(User data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
