package com.example.popularity.model;

import com.google.gson.annotations.SerializedName;

public class BaseUserData {
    @SerializedName("user_info")
    private User userInfo;

    @SerializedName("update_info")
    private UpdateInfo updateInfo;


    public User getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(User userInfo) {
        this.userInfo = userInfo;
    }

    public UpdateInfo getUpdateInfo() {
        return updateInfo;
    }

    public void setUpdateInfo(UpdateInfo updateInfo) {
        this.updateInfo = updateInfo;
    }


}
