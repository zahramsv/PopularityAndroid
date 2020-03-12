package ir.mohad.popularity.model;

import com.google.gson.annotations.SerializedName;

public class UpdateInfoOS {
    @SerializedName("last_version")
    private String lastVersion;


    @SerializedName("last_force_update_version")
    private String lastForceUpdateVersion;

    public String getLastVersion() {
        return lastVersion;
    }

    public void setLastVersion(String lastVersion) {
        this.lastVersion = lastVersion;
    }

    public String getLastForceUpdateVersion() {
        return lastForceUpdateVersion;
    }

    public void setLastForceUpdateVersion(String lastForceUpdateVersion) {
        this.lastForceUpdateVersion = lastForceUpdateVersion;
    }
}
