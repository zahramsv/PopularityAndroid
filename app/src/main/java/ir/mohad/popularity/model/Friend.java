package ir.mohad.popularity.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Friend implements Serializable {

    @SerializedName("image")
    public String image;

    @SerializedName("name")
    public String name;

    @SerializedName("rate")
    public boolean rate;

    @SerializedName("userId")
    public String userId;

    @SerializedName("id")
    public int id;


    public Friend() {
    }

    public Friend(String image, String name, boolean rate, String userId, int id) {
        this.image = image;
        this.name = name;
        this.rate = rate;
        this.userId = userId;
        this.id = id;
    }


    public Friend(String name, boolean rate) {
        this.name = name;
        this.rate = rate;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isRate() {
        return rate;
    }

    public void setRate(boolean rate) {
        this.rate = rate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
