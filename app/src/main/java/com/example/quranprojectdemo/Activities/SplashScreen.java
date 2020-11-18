package com.example.quranprojectdemo.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
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

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quranprojectdemo.R;

import java.util.Locale;

public class SplashScreen extends AppCompatActivity {
    TextView tv_Title;

    SharedPreferences sp ;
    SharedPreferences.Editor editor;
    int check_center;
    int check_teacher;
    int check_student;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        sp=getSharedPreferences(Main_center.CHECK_REG_CENTER,MODE_PRIVATE);
        check_center= sp.getInt(Main_center.CHECK_REG_CENTER_ID,0);

        sp=getSharedPreferences(Main_teacher.CHECK_REG_TEACHER,MODE_PRIVATE);
        check_teacher= sp.getInt(Main_teacher.CHECK_REG_TEACHER_ID,0);

        sp=getSharedPreferences(Main_student.CHECK_REG_STUDENT,MODE_PRIVATE);
        check_student= sp.getInt(Main_student.CHECK_REG_STUDENT_ID,0);


        if (check_center==1){
            Intent intent=new Intent(getBaseContext(),QuranCenter_Login.class);
            startActivity(intent);
        }else if (check_teacher==1){
            Intent intent=new Intent(getBaseContext(),TeacherLogin.class);
            startActivity(intent);
        }else if (check_student==1){
            Intent intent=new Intent(getBaseContext(),GuardianLogin.class);
            startActivity(intent);
        }else {

            blink(tv_Title);


            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(getBaseContext(), RegisterAs.class));
                    finish();
                }
            }, 500);
        }
        setLocale("ar");


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
