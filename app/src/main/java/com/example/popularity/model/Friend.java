package com.example.popularity.model;

public class Friend {

    public int image;
    public String name;
    public boolean rate;

    public Friend(String name, boolean rate) {
        this.name = name;
        this.rate = rate;
    }


    public Friend(int image, String name, boolean rate) {
        this.image = image;
        this.name = name;
        this.rate = rate;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
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
}
