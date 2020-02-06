package com.example.popularity.test;

import javax.inject.Inject;

public class DaggerTest {


    @Inject
    Mive mive;






    Done d = new Done();
    Sib s = new Sib(d);
    Derakht de = new Derakht();


    Mive m = new Mive(s, de);

    class Mive{
        Mive(Sib s, Derakht d){}
    }

    class Sib{
        Sib(Done d){

        }
    }

    class Done{

    }

    class Derakht{

    }

}
