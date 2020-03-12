package ir.mohad.popularity.model;

import com.google.gson.annotations.SerializedName;

public class UpdateInfo {

    @SerializedName("android")
    private UpdateInfoOS updateInfoOS;

    @SerializedName("update_message_fa")
    private String updateMessageFa;

    @SerializedName("update_message_en")
    private String updateMessageEn;

    public UpdateInfoOS getUpdateInfoOS() {
        return updateInfoOS;
    }

    public void setUpdateInfoOS(UpdateInfoOS updateInfoOS) {
        this.updateInfoOS = updateInfoOS;
    }

    public String getUpdateMessageFa() {
        return updateMessageFa;
    }

    public void setUpdateMessageFa(String updateMessageFa) {
        this.updateMessageFa = updateMessageFa;
    }

    public String getUpdateMessageEn() {
        return updateMessageEn;
    }

    public void setUpdateMessageEn(String updateMessageEn) {
        this.updateMessageEn = updateMessageEn;
    }
}
