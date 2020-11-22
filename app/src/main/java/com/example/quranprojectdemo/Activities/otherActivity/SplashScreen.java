package com.example.quranprojectdemo.Activities.otherActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
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
import android.widget.TabHost;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quranprojectdemo.Activities.logIn.GuardianLogin;
import com.example.quranprojectdemo.Activities.logIn.QuranCenter_Login;
import com.example.quranprojectdemo.Activities.logIn.TeacherLogin;
import com.example.quranprojectdemo.R;
import com.example.quranprojectdemo.Activities.mainActivity.Main_center;
import com.example.quranprojectdemo.Activities.mainActivity.Main_student;
import com.example.quranprojectdemo.Activities.mainActivity.Main_teacher;
import com.example.quranprojectdemo.Activities.registrar.RegisterAs;
import com.example.quranprojectdemo.service.AlarmBroadcastReceiverToGetData;
import com.example.quranprojectdemo.service.GetDataService;

import java.util.Locale;

public class SplashScreen extends AppCompatActivity {
    TextView tv_Title;
    public static final String HOWISLOGGED = "nameLoggedNow";
    public static final String CHEACKHOWISLOGGED = "HOWISLOGGED";

    SharedPreferences sp;
    SharedPreferences.Editor editor;
    int howIsLogged = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        sp = getSharedPreferences(CHEACKHOWISLOGGED, MODE_PRIVATE);
        howIsLogged = sp.getInt(HOWISLOGGED, -1);

        Intent serviceIntent = new Intent(getBaseContext(), GetDataService.class);
        startService(serviceIntent);


        switch (howIsLogged) {
            case 0:
                finish();
                startActivity(new Intent(getBaseContext(), Main_center.class));

                break;
            case 1:
                finish();
                startActivity(new Intent(getBaseContext(), Main_teacher.class));
                break;
            case 2:
                finish();
                startActivity(new Intent(getBaseContext(), Main_student.class));
                break;
            default:
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                Intent IR = new Intent(getApplicationContext(), AlarmBroadcastReceiverToGetData.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),
                        0, IR, PendingIntent.FLAG_UPDATE_CURRENT);
                if (Build.VERSION.SDK_INT >= 23) {
                    alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), pendingIntent);
                } else if (Build.VERSION.SDK_INT >= 19) {
                    alarmManager.setExact(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), pendingIntent);
                } else {
                    alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), pendingIntent);
                }

                blink(tv_Title);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(getBaseContext(), RegisterAs.class));
                        finish();
                    }
                }, 1000);


                break;

        }


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
