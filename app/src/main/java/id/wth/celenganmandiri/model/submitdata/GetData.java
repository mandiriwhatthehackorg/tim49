package id.wth.celenganmandiri.model.submitdata;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class GetData implements Serializable {

    @SerializedName("success")
    @Expose
    boolean success;

    @SerializedName("data")
    @Expose
    GetDataToken data;

    @SerializedName("message")
    @Expose
    String message;

    public GetData(boolean success, GetDataToken data, String message) {
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

    public GetDataToken getData() {
        return data;
    }

    public void setData(GetDataToken data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
