package id.wth.celenganmandiri.model.session;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class GetSession implements Serializable {

    @SerializedName("success")
    @Expose
    boolean success;

    @SerializedName("data")
    @Expose
    GetSessionData data;

    @SerializedName("message")
    @Expose
    String message;

    public GetSession(boolean success, GetSessionData data, String message) {
        this.success = success;
        this.data = data;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public GetSessionData getData() {
        return data;
    }

    public void setData(GetSessionData data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
