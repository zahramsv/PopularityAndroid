package com.example.popularity.model;

public class Login {
   private int social_type;
   private String username,full_name,avatar_url;
   private String social_primary;

    public Login() {
    }

    public Login(String social_primary, int social_type, String username, String full_name, String avatar_url) {
        this.social_primary = social_primary;
        this.social_type = social_type;
        this.username = username;
        this.full_name = full_name;
        this.avatar_url = avatar_url;
    }


    public String getSocial_primary() {
        return social_primary;
    }

    public void setSocial_primary(String social_primary) {
        this.social_primary = social_primary;
    }

    public int getSocial_type() {
        return social_type;
    }

    public void setSocial_type(int social_type) {
        this.social_type = social_type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }
}
