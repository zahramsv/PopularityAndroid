package com.example.popularity.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class BaseResponse<T> implements Serializable {

    @SerializedName("code")
    private int code;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private T data;

}
