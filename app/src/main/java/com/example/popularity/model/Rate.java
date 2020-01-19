package com.example.popularity.model;

public class Rate {

    private String attribute;
    private Float Rate;

    public Rate(String attribute, Float rate) {
        this.attribute = attribute;
        Rate = rate;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public Float getRate() {
        return Rate;
    }

    public void setRate(Float rate) {
        Rate = rate;
    }
}

