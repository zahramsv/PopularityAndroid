package ir.mohad.popularity.model.repository;

import ir.mohad.popularity.model.User;
import ir.mohad.popularity.model.UserPopularity;
import ir.mohad.popularity.utils.LoginKind;

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
