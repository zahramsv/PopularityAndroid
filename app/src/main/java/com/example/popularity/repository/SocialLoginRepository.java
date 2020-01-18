package com.example.popularity.repository;

import com.example.popularity.model.Login;

public class SocialLoginRepository {


    public Login DataModel1;
    public Login DataModel2;

    public Login getMockDataModel2() {
        return DataModel2;
    }

    public Login getMockDataModel1() {
        return DataModel1;
    }

    public  void generateDataMock() {
        DataModel1=new Login();
        DataModel2=new Login();

       DataModel1.setAvatar_url("image1");
       DataModel1.setFull_name("hanie");
       DataModel1.setSocial_primary(4);
       DataModel1.setSocial_type(2);
       DataModel1.setUsername("hani");



      /*  DataModel2.setAvatar_url("test1");
        DataModel2.setFull_name("sara");
        DataModel2.setSocial_primary(8002);
        DataModel2.setSocial_type(2);
        DataModel2.setUsername("sara.sh");*/
        
    }


    public void GenerateDataFromServer(){}
    public void GenerateDataFromAPI(){}
}
