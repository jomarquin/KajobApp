package com.kajoba.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        getSupportActionBar().hide();//Hide the ActionBar
        ImageView imgSplash = findViewById(R.id.Img_Splash);

        Animation animation = AnimationUtils.loadAnimation(SplashActivity.this,R.anim.anim);
        imgSplash.startAnimation(animation);

        //getSupportActionBar().hide(); //Hide the ActionBar

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        },4000);
    }

    @Override
    protected void onStop() {
        super.onStop();
        getSupportActionBar().show();
    }
}
