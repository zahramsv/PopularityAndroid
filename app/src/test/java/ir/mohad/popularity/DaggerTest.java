package ir.mohad.popularity;

import org.junit.Test;

import javax.inject.Inject;

public class DaggerTest {

    @Inject
    Person person;

    @Test
    public void main(){

        //Person person = new Person(new Body());
        person.getName();

    }


    class Body {
        @Inject
        public Body(){

        }
    }


    class Person{
        @Inject
        Body body;

        @Inject
        public Person(Body body){
            this.body = body;
        }

        public String getName(){
            return "zahra";
        }
    }

}
