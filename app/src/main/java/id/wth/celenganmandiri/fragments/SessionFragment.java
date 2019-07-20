package id.wth.celenganmandiri.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.AppCompatEditText;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.balysv.materialripple.MaterialRippleLayout;
import com.google.gson.JsonObject;

import java.util.Calendar;

import id.wth.celenganmandiri.R;
import id.wth.celenganmandiri.model.session.GetSession;
import id.wth.celenganmandiri.network.APIClientBoarding;
import id.wth.celenganmandiri.network.APIRequests;
import id.wth.celenganmandiri.utils.PreferenceManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SessionFragment extends Fragment {

    AppCompatEditText nik, tgl, phone;
    Button nextBtn;
    DatePickerDialog picker;

    APIRequests apiRequests;
    PreferenceManager preferenceManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_session, container, false);

        preferenceManager = new PreferenceManager(getActivity());

        final AppCompatEditText email = rootView.findViewById(R.id.email);
        nik = rootView.findViewById(R.id.nik);
        tgl = rootView.findViewById(R.id.ttgl);
        phone = rootView.findViewById(R.id.phone);
        nextBtn = rootView.findViewById(R.id.nextBtn);

        tgl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        StringBuffer strBuf = new StringBuffer();
                        strBuf.append(year);
                        strBuf.append("-");
                        strBuf.append(month+1);
                        strBuf.append("-");
                        strBuf.append(dayOfMonth);
                        tgl.setText(strBuf.toString());
                    }
                };

                // Get current year, month and day.
                Calendar now = Calendar.getInstance();
                int year = now.get(java.util.Calendar.YEAR);
                int month = now.get(java.util.Calendar.MONTH);
                int day = now.get(java.util.Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), android.R.style.Theme_Holo_Dialog, onDateSetListener, year, month, day);
                // Popup the dialog.
                datePickerDialog.show();
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ProgressDialog progressDialog = new ProgressDialog(getActivity());
                progressDialog.setMessage("Loading...");
                progressDialog.show();

                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("email",email.getText().toString());
                jsonObject.addProperty("nik", nik.getText().toString());
                jsonObject.addProperty("phone",phone.getText().toString());
                jsonObject.addProperty("ttl",tgl.getText().toString());

                apiRequests = APIClientBoarding.createService(APIRequests.class, "3e47d129-1b43-4d08-8df5-7d80beedd5eb","4c78ce4d-f479-4ed8-931d-3c7672a9b705");
                Call<GetSession> call = apiRequests.postDataSession(jsonObject);
                call.enqueue(new Callback<GetSession>() {
                    @Override
                    public void onResponse(Call<GetSession> call, Response<GetSession> response) {
                        if (response.isSuccessful()) {
                            progressDialog.dismiss();
                            if (response.body().isSuccess()) {
                                preferenceManager.setPreference("jwt", response.body().getData().getToken());
                                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                                ft.replace(R.id.client_content, new VerifivcationOtpFragment(), "NewFragmentTag");
                                ft.commit();
                            } else {
                                showCustomDialog();
                            }
                        } else {
                            showCustomDialog();
                        }
                    }

                    @Override
                    public void onFailure(Call<GetSession> call, Throwable t) {
                        progressDialog.dismiss();
                        showCustomDialog();
                    }
                });

            }
        });
        return rootView;
    }

    private void showCustomDialog() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.dialog_gdpr_basic);
        dialog.setCancelable(true);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        ((TextView) dialog.findViewById(R.id.tv_content)).setMovementMethod(LinkMovementMethod.getInstance());

        ((Button) dialog.findViewById(R.id.bt_accept)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resendOtp();
            }
        });

        ((Button) dialog.findViewById(R.id.bt_decline)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               dialog.dismiss();
            }
        });


        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }

    private void resendOtp() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("nik", nik.getText().toString());
        apiRequests = APIClientBoarding.createService(APIRequests.class, "3e47d129-1b43-4d08-8df5-7d80beedd5eb","4c78ce4d-f479-4ed8-931d-3c7672a9b705");
        Call<GetSession> call = apiRequests.postResendOTP(jsonObject);
        call.enqueue(new Callback<GetSession>() {
            @Override
            public void onResponse(Call<GetSession> call, Response<GetSession> response) {
                if (response.isSuccessful()) {
                    if (response.body().isSuccess()) {
                        final FragmentTransaction ft = getFragmentManager().beginTransaction();
                        ft.replace(R.id.client_content, new VerifivcationOtpFragment(), "NewFragmentTag");
                        ft.commit();
                    } else {
                        showCustomDialog();
                    }
                } else {
                    showCustomDialog();
                }
            }

            @Override
            public void onFailure(Call<GetSession> call, Throwable t) {
                showCustomDialog();
            }
        });
    }

}
