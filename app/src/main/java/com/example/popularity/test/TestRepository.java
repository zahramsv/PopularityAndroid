package com.example.popularity.test;
import android.os.Handler;


public class TestRepository {

    private TestModel num1;
    private TestModel num2;

    public TestModel getNum1(){
        return num1;
    }

    public TestModel getNum2(){
        return num2;
    }

    public void generateDataMock(){
        num1 = new TestModel(7);
        num2 = new TestModel(4);
    }

    public void generateDataFromServer(){
        Handler h = new Handler();
        h.postDelayed(() -> {
            num1 = new TestModel(2);
            num2 = new TestModel(8);
        },1000);
    }

    public void generagteDataFromDatabase(){

    }

    public void getDataFromSharedPrefrences(){

    }

    public void changeNum1InDatabase(int newNum1){

    }

    public void insertNewNumber(int number){

    }


}
