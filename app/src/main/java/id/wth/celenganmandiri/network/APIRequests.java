package id.wth.celenganmandiri.network;

import com.google.gson.JsonObject;

import id.wth.celenganmandiri.model.jwt.GetJWT;
import id.wth.celenganmandiri.model.otp.GetOTP;
import id.wth.celenganmandiri.model.session.GetSession;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIRequests {

    // Request App Info
    @Headers("Content-Type: application/json")
    @GET("rest/pub/apigateway/jwt/getJsonWebToken?app_id=36672f24-4af9-45f7-a97e-9063cc1d20e9")
    Call<GetJWT> getJwtoken();

    @Headers("Content-Type: application/json")
    @POST("api/initiate/createSession")
    Call<GetSession> postDataSession(@Body JsonObject locationPost);


    @Headers("Content-Type: application/json")
    @POST("api/initiate/resendOTP")
    Call<GetSession> postResendOTP(@Body JsonObject locationPost);

    @Headers("Content-Type: application/json")
    @POST("api/initiate/validateOTP")
    Call<GetOTP> postGetOtp(@Body JsonObject locationPost);
}
