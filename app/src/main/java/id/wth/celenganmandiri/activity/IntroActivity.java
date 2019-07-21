package id.wth.celenganmandiri.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;

import com.github.paolorotolo.appintro.AppIntro;

import id.wth.celenganmandiri.R;
import id.wth.celenganmandiri.fragments.Intro_Slide_1;
import id.wth.celenganmandiri.fragments.Intro_Slide_2;
import id.wth.celenganmandiri.fragments.Intro_Slide_3;
import id.wth.celenganmandiri.utils.PreferenceManager;

public class IntroActivity extends AppIntro {

    PreferenceManager preferenceManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        preferenceManager = new PreferenceManager(this);
        // Note here that we DO NOT use setContentView();

        // Add your slide fragments here.
        // AppIntro will automatically generate the dots indicator and buttons.
        addSlide(new Intro_Slide_1());
        addSlide(new Intro_Slide_2());
        addSlide(new Intro_Slide_3());



        // Hide StatusBar
        showStatusBar(false);
        showSkipButton(false);
        setProgressButtonEnabled(true);

        setBarColor(ContextCompat.getColor(IntroActivity.this, R.color.colorPrimaryLight_1));
        setSeparatorColor(ContextCompat.getColor(IntroActivity.this, R.color.colorPrimaryLight_1));

        setColorDoneText(ContextCompat.getColor(IntroActivity.this, R.color.colorAccent));
        setColorSkipButton(ContextCompat.getColor(IntroActivity.this, R.color.colorPrimary));
        setNextArrowColor(ContextCompat.getColor(IntroActivity.this, R.color.colorAccent));

        setIndicatorColor(ContextCompat.getColor(IntroActivity.this, R.color.colorPrimary),
                ContextCompat.getColor(IntroActivity.this, R.color.iconsLight));
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        if (preferenceManager.isFirstTimeLaunch()) {
            preferenceManager.setFirstTimeLaunch(false);
            // Do something when users tap on Skip button.
            startActivity(new Intent(IntroActivity.this, LandingActivity.class));
            finish();
            overridePendingTransition(R.anim.enter_from_right, R.anim.exit_out_right);
        } else {
            // Finish this Activity
            finish();
        }
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        if (preferenceManager.isFirstTimeLaunch()) {
            preferenceManager.setFirstTimeLaunch(false);
            startActivity(new Intent(IntroActivity.this, LandingActivity.class));
            finish();
            overridePendingTransition(R.anim.enter_from_right, R.anim.exit_out_right);
        } else {
            finish();
        }
    }

    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);
        // Do something when the slide changes.
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (preferenceManager.isFirstTimeLaunch()) {
            //myAppPrefsManager.setFirstTimeLaunch(false);
            // Navigate to MainActivity
            startActivity(new Intent(IntroActivity.this, LandingActivity.class));
            finish();
            overridePendingTransition(R.anim.enter_from_right, R.anim.exit_out_right);
        }
        else {
            // Finish this Activity
            finish();
        }
    }
}
