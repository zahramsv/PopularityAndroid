package ir.mohad.popularity.mvp;

public interface MainMvp {

    interface Presenter {
        boolean canAppClose();
        void setAppCLoseStatus(boolean canClose);
    }

    interface View {

    }

}
