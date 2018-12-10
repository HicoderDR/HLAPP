package com.example.jsrgjhl.hlapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class StartActivity extends AppCompatActivity {

    private  final int SPLASH_DISPLAY_LENGHT = 3000;//3秒后进入系统，时间可自行调整

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mainIntent = new Intent(StartActivity.this,LoginActivity.class);
                StartActivity.this.startActivity(mainIntent);
                StartActivity.this.finish();
            }
        },SPLASH_DISPLAY_LENGHT);

    }
}
