package com.example.codemaven3015.xadmobile.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.codemaven3015.xadmobile.R;

public class Splash extends AppCompatActivity {

    private static final int SPLASH_TIMEOUT = 3000;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        sharedPreferences=this.getSharedPreferences("User_Info",MODE_PRIVATE);
        editor=sharedPreferences.edit();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(sharedPreferences.getBoolean("FIRST_LOGIN",false)){
                    Intent intent = new Intent(Splash.this, Home.class);
                    startActivity(intent);
                    finish();

                }else {
                    Intent intent = new Intent(Splash.this, Login.class);
                    startActivity(intent);
                    finish();
                }
            }
        },SPLASH_TIMEOUT);
    }
}
