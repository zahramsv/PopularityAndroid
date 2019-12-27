package com.example.popularity.test;

public class TestLogic {

    TestRepository repository;

    public TestLogic() {
        repository = new TestRepository();
    }

    public String addNumbers(){
        repository.generateDataMock();

        return "result is : "+(repository.getNum1().number + repository.getNum2().number);
    }

    public String minusNumbers(){
        return "result is : "+(7-4);
    }

}
