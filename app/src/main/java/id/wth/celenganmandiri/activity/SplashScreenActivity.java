package id.wth.celenganmandiri.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import id.wth.celenganmandiri.R;
import id.wth.celenganmandiri.utils.PreferenceManager;

public class SplashScreenActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 2500;
    private PreferenceManager preferenceManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        preferenceManager = new PreferenceManager(this);
        preferenceManager.setFirstTimeLaunch(true);
        displaySplashScreen();
    }

    private void displaySplashScreen() {
        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */
            @Override
            public void run() {
                if (preferenceManager.isFirstTimeLaunch()) {
                    Intent i = new Intent(SplashScreenActivity.this, IntroActivity.class);
                    startActivity(i);
                    finish();
                }
                else {
                    // Navigate to MainActivity
                    startActivity(new Intent(getBaseContext(), IntroActivity.class));
                    finish();
                }
            }
        }, SPLASH_TIME_OUT);
    }
}
