package com.example.quranprojectdemo.Activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.example.quranprojectdemo.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Locale;

public class SplashScreen extends AppCompatActivity {
    TextView tv_Title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        setLocale("ar");
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

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void setLocale(String lang) {
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();

        Configuration conf = res.getConfiguration();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            conf.setLocale(new Locale(lang.toLowerCase()));

        } else {
            conf.locale = new Locale(lang.toLowerCase());

        }

        res.updateConfiguration(conf, dm);
    }
}
