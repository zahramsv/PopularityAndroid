package ir.mohad.popularity.presenter;

import ir.mohad.popularity.mvp.MainMvp;

public class MainPresenter implements MainMvp.Presenter {

    private boolean closeApp = false;

    @Override
    public boolean canAppClose(){
        return closeApp;
    }

    @Override
    public void setAppCLoseStatus(boolean canClose){
        this.closeApp = canClose;
    }
}
