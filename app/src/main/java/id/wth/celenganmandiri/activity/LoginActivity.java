package id.wth.celenganmandiri.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import id.wth.celenganmandiri.R;
import id.wth.celenganmandiri.model.jwt.GetJWT;
import id.wth.celenganmandiri.network.APIClient;
import id.wth.celenganmandiri.network.APIRequests;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    APIClient apiClient;
    APIRequests apiRequests;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_card_light);

        apiRequests = apiClient.createService(APIRequests.class, "3e47d129-1b43-4d08-8df5-7d80beedd5eb","4c78ce4d-f479-4ed8-931d-3c7672a9b705");
        Call<GetJWT> call = apiRequests.getJwtoken();
        call.enqueue(new Callback<GetJWT>() {
            @Override
            public void onResponse(Call<GetJWT> call, Response<GetJWT> response) {
                if (response.isSuccessful()) {
                    String token = response.body().getToken();
                } else {
                    // error response, no access to resource?
                }
            }

            @Override
            public void onFailure(Call<GetJWT> call, Throwable t) {
                Log.d("Error", t.getMessage().toString());
            }
        });
    }
}
