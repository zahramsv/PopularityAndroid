package com.example.popularity.model.repository;

import com.example.popularity.model.User;
import com.example.popularity.model.UserPopularity;
import com.example.popularity.utils.LoginKind;

public class LoginHandler {

    private SharedPrefsRepository sharedPrefsRepository;

    public LoginHandler(SharedPrefsRepository sharedPrefsRepository) {
        this.sharedPrefsRepository = sharedPrefsRepository;
    }

    public LoginKind getLoginKind() {
        String kind = sharedPrefsRepository.getLoginKind();
        if(kind.equals("")){
            return LoginKind.MOCK;
        }else{
            return LoginKind.valueOf(kind);
        }
    }

    public void setLoginKind(LoginKind loginKind) {
        sharedPrefsRepository.saveLoginKind(loginKind.name());
    }

    public void saveLoginInfo(User user, UserPopularity userPopularity){
        sharedPrefsRepository.SaveUser(user, userPopularity);
    }

}
