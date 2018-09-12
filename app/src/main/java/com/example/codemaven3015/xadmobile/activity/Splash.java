package com.example.codemaven3015.xadmobile.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.codemaven3015.xadmobile.R;

public class Splash extends AppCompatActivity {

    private static final int SPLASH_TIMEOUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(Splash.this,Login.class);
                startActivity(intent);

                finish();
            }
        },SPLASH_TIMEOUT);
    }
}
