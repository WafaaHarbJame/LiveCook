package com.livecook.livecookapp.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;

import com.livecook.livecookapp.MainActivity;
import com.livecook.livecookapp.Model.Constants;
import com.livecook.livecookapp.R;

public class SplashActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    public static final int SPLASH_TIME = 2000;
    Boolean ISLOGIN;
    SharedPreferences preferences ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash);
        preferences = getSharedPreferences(Constants.PREF_FILE_CONFIG, MODE_PRIVATE);
        //preferences.edit().clear().apply();
        //preferences.edit().commit();


        /*if (preferences != null && preferences.getBoolean(Constants.ISLOGIN, false)) {
            ISLOGIN = preferences.getBoolean(Constants.ISLOGIN, false);
            if (ISLOGIN) {
                //ISLOGIN=true;
                //SPLASH_TIME_OUT=0;
                Intent i = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }*/
        //progressBar = (ProgressBar) findViewById(R.id.progressBar);
        // progressBar.setVisibility(View.VISIBLE);
        // progressBar.getIndeterminateDrawable()
        // .setColorFilter( Color.parseColor("#CA6E01"), PorterDuff.Mode.SRC_IN);
        // else {
        new CountDownTimer(SPLASH_TIME, 1000) {

            @Override
            public void onFinish() {
                //  progressBar.setVisibility(View.GONE);

                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(intent);
                finish();

            }

            @Override
            public void onTick(long millisUntilFinished) {

            }
        }.start();

    }

//
    //}
    // }




    @Override
    public void onBackPressed()
    {

        super.onBackPressed();
    }
}
