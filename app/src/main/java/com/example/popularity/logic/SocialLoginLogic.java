package com.example.popularity.logic;

import com.example.popularity.model.Login;
import com.example.popularity.repository.SocialLoginRepository;

public class SocialLoginLogic {

    SocialLoginRepository socialLoginRepository;

    public SocialLoginLogic() {
        socialLoginRepository = new SocialLoginRepository();
    }


    public Login GetFirstUserLoginData()
    {
        socialLoginRepository.generateDataMock();
        return socialLoginRepository.getMockDataModel1();
    }
}
