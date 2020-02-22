package com.example.popularity.model;

public class Rate {

    private String attribute;
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

