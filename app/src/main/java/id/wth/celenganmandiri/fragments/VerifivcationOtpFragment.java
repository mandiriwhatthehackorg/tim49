package id.wth.celenganmandiri.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.JsonObject;

import id.wth.celenganmandiri.R;
import id.wth.celenganmandiri.model.otp.GetOTP;
import id.wth.celenganmandiri.network.APIClientBoarding;
import id.wth.celenganmandiri.network.APIRequests;
import id.wth.celenganmandiri.utils.PreferenceManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerifivcationOtpFragment extends Fragment {

    AppCompatEditText one, two, three, four, five,six;
    AppCompatButton submitBtn;
    APIRequests apiRequests;
    PreferenceManager preferenceManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_verification_code, container, false);

        preferenceManager = new PreferenceManager(getActivity());

        one = rootView.findViewById(R.id.one);
        two = rootView.findViewById(R.id.two);
        three = rootView.findViewById(R.id.three);
        four = rootView.findViewById(R.id.four);
        five = rootView.findViewById(R.id.five);
        six = rootView.findViewById(R.id.six);
        submitBtn = rootView.findViewById(R.id.verivyBtn);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ProgressDialog progressDialog = new ProgressDialog(getActivity());
                progressDialog.setMessage("Loading...");
                progressDialog.show();

                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("otp", one.getText().toString()
                        +two.getText().toString()+three.getText().toString()
                        +four.getText().toString()+five.getText().toString()
                        +six.getText().toString());

                apiRequests = APIClientBoarding.createService(APIRequests.class, "Bearer "+preferenceManager.getPreference("jwt"), true);
                Call<GetOTP> call = apiRequests.postGetOtp( jsonObject);
                call.enqueue(new Callback<GetOTP>() {
                    @Override
                    public void onResponse(Call<GetOTP> call, Response<GetOTP> response) {
                        if (response.isSuccessful()) {
                            progressDialog.dismiss();
                            if (response.body().isSuccess()) {
                                preferenceManager.setPreference("jwt", response.body().getData().getToken());
                                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                                ft.replace(R.id.client_content, new SubmitRekFragment(), "NewFragmentTag");
                                ft.commit();
                            } else {

                            }
                        } else {

                        }
                    }

                    @Override
                    public void onFailure(Call<GetOTP> call, Throwable t) {
                        progressDialog.dismiss();
                        Log.d("error", t.getMessage().toString());
                    }
                });
            }
        });

        return rootView;
    }
}
