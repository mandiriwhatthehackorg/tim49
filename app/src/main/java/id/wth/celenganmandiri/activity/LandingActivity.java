package id.wth.celenganmandiri.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import id.wth.celenganmandiri.R;

public class LandingActivity extends AppCompatActivity {

    Button signinBtn, signupBtn;
    AppCompatEditText edtKode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        Typeface font1 = Typeface.createFromAsset(getAssets(), "fonts/myriadpro-regular.otf");

        signinBtn = (Button) findViewById(R.id.signinBtn);
        signupBtn = (Button) findViewById(R.id.signup);
        edtKode = (AppCompatEditText) findViewById(R.id.edtKode);
        signinBtn.setTypeface(font1);
        signupBtn.setTypeface(font1);
        edtKode.setTypeface(font1);

        edtKode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count>0) {
                    signinBtn.setEnabled(true);
                    signinBtn.setBackgroundResource(R.drawable.btn_rounded);
                } else {
                    signinBtn.setEnabled(false);
                    signinBtn.setBackgroundResource(R.drawable.btn_rounded_grey);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LandingActivity.this, BoardingClientActivity.class);
                startActivity(intent);
            }
        });
    }
}
