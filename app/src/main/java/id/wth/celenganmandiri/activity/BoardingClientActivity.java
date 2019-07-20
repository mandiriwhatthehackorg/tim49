package id.wth.celenganmandiri.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import id.wth.celenganmandiri.R;
import id.wth.celenganmandiri.fragments.SessionFragment;

public class BoardingClientActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_client);

        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction();
        transaction.replace(R.id.client_content, new SessionFragment());
        transaction.commit();

    }
}
