package com.example.popularity.mvp;

import com.example.popularity.utils.ToolbarKind;

public interface AboutUsMvp {

    interface View{
        void changeToolbar(ToolbarKind kind,String title);
        void setDescriptionAboutUs(String desc);
    }

    interface Presenter{

    }
}
