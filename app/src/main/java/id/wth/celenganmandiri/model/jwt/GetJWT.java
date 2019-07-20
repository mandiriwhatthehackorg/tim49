package id.wth.celenganmandiri.model.jwt;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class GetJWT implements Serializable {

    @SerializedName("jwt")
    @Expose
    String token;

    public GetJWT(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
