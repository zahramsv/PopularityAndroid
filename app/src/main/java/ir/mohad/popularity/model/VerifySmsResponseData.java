package ir.mohad.popularity.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class VerifySmsResponseData implements Serializable {

    @SerializedName("isRegistered")
    public boolean isRegistered;



    public boolean isRegistered() {
        return isRegistered;
    }

    public void setRegistered(boolean registered) {
        isRegistered = registered;
    }
}
