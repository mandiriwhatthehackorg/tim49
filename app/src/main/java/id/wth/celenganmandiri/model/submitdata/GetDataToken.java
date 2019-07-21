package id.wth.celenganmandiri.model.submitdata;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class GetDataToken implements Serializable {

    @SerializedName("viewName")
    @Expose
    String viewName;

    @SerializedName("token")
    @Expose
    String token;

    public GetDataToken(String viewName, String token) {
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
