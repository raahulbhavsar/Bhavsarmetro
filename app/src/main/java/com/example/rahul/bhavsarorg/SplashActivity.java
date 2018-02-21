package com.example.rahul.bhavsarorg;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.rahul.bhavsarorg.MainClasses.AppUtils;
import com.example.rahul.bhavsarorg.MainClasses.Utils;

public class SplashActivity extends AppCompatActivity {

    private boolean bActivityStopped;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        if (!Utils.isConnected(this)) {
            if (AppUtils.isLoggedIn(this)) {
                startActivity(new Intent(this, LoginActivity.class));
                finish();
            } else{}
//                DialogHelper.showDialog(this, getString(R.string.error), "Turn On Network",
//                        null, getString(R.string.ok), null, false, true);
        }
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        if (!bActivityStopped) {
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                finish();
            }
        }

    @Override
    protected void onStart() {
        super.onStart();
        bActivityStopped = false;


    }

    @Override
    protected void onStop() {
        super.onStop();
        bActivityStopped = true;
    }

}


