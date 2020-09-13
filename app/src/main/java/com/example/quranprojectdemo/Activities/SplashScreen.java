package com.example.quranprojectdemo.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.example.quranprojectdemo.R;

public class SplashScreen extends AppCompatActivity {
    TextView tv_Title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        blink(tv_Title);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getBaseContext(), RegisterAs.class));
                finish();
            }
        }, 6000);
    }

    public void blink(View view) {

        tv_Title = findViewById(R.id.SplashScreen_tv_Title);
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blink);
        tv_Title.startAnimation(animation);
    }
}
