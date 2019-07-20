package id.wth.celenganmandiri.model.session;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class GetSessionData implements Serializable {

    @SerializedName("viewName")
    @Expose
    String viewName;

    @SerializedName("token")
    @Expose
    String token;

    public GetSessionData(String viewName, String token) {
        this.viewName = viewName;
        this.token = token;
    }

    public String getViewName() {
        return viewName;
    }

    public void setViewName(String viewName) {
        this.viewName = viewName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
