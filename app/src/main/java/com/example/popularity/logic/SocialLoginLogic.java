package com.example.popularity.logic;

import com.example.popularity.model.LoginSendDataModel;
import com.example.popularity.repository.SocialLoginRepository;

public class SocialLoginLogic {

    SocialLoginRepository socialLoginRepository;

    public SocialLoginLogic() {
        socialLoginRepository = new SocialLoginRepository();
    }


    public LoginSendDataModel GetFirstUserLoginData()
    {
        socialLoginRepository.generateDataMock();
        return socialLoginRepository.getMockDataModel1();
    }
}
