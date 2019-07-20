package id.wth.celenganmandiri.model.otp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class GetOTP implements Serializable {
    @SerializedName("success")
    @Expose
    boolean success;

    @SerializedName("data")
    @Expose
    GetOTPData data;

    @SerializedName("message")
    @Expose
    String message;

    public GetOTP(boolean success, GetOTPData data, String message) {
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

    public GetOTPData getData() {
        return data;
    }

    public void setData(GetOTPData data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
