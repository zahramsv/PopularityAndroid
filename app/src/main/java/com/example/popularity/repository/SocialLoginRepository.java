package com.example.popularity.repository;

import com.example.popularity.model.LoginSendDataModel;

public class SocialLoginRepository {


    public  LoginSendDataModel DataModel1;
    public  LoginSendDataModel DataModel2;

    public LoginSendDataModel getMockDataModel2() {
        return DataModel2;
    }

    public LoginSendDataModel getMockDataModel1() {
        return DataModel1;
    }

    public  void generateDataMock() {
        DataModel1=new LoginSendDataModel();
        DataModel2=new LoginSendDataModel();

       DataModel1.setAvatar_url("test");
       DataModel1.setFull_name("zahra");
       DataModel1.setSocial_primary(8001);
       DataModel1.setSocial_type(2);
       DataModel1.setUsername("zahra.msv");



        DataModel2.setAvatar_url("test1");
        DataModel2.setFull_name("sara");
        DataModel2.setSocial_primary(8002);
        DataModel2.setSocial_type(2);
        DataModel2.setUsername("sara.sh");
        
    }

    public void GenerateDataFromServer(){}
    public void GenerateDataFromAPI(){}
}
