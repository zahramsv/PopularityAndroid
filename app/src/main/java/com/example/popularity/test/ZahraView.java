package com.example.popularity.test;

public class ZahraView {

    private int color;
    private String text;

    public ZahraView setColor(){
        //todo set color

        return this;
    }

    public String getTextZahra(){
        return text;
    }

    public ZahraView setText(String str){
        text = str;
        return this;
    }

    public ZahraView setImage(){
        return this;
    }

    public ZahraView setBackground(){
        return this;
    }

    public ZahraView setLogic(){
        return this;
    }

}


/*how to use :

private void test(){
    ZahraView zahra = new ZahraView()
        .setBackground()
        .setColor()
        .setLogic();
}
*
*
* */
