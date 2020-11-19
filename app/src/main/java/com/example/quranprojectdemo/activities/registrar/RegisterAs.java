package com.example.quranprojectdemo.activities.registrar;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.quranprojectdemo.activities.joinRequsers.JoinRequest1;
import com.example.quranprojectdemo.activities.logIn.GuardianLogin;
import com.example.quranprojectdemo.activities.logIn.TeacherLogin;
import com.example.quranprojectdemo.R;
import com.example.quranprojectdemo.activities.logIn.QuranCenter_Login;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

public class RegisterAs extends AppCompatActivity {

    TextView tv_MakeAccount, tv_QuranCenter, tv_Teacher, tv_guardian, tv_AddRequest, tv_or;
    CardView crV_QuranCenter, crV_Teacher, crV_guardian, crVAddRequest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_as);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel ch1 = new NotificationChannel("MyNotifications","MyNotifications", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(ch1);
        }
        FirebaseMessaging.getInstance().subscribeToTopic("general")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = "successful";
                        if (!task.isSuccessful()) {
                            msg ="failed";
                        }
                        //Log.d(TAG, msg);
                        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                    }
                });



        tv_MakeAccount = findViewById(R.id.RegAs_tv_MakeAnAccount);
        tv_AddRequest = findViewById(R.id.RegAs_tv_AddRequest);
        tv_guardian = findViewById(R.id.RegAs_tv_guardian);
        tv_QuranCenter = findViewById(R.id.RegAs_tv_QuranCenter);
        tv_Teacher = findViewById(R.id.RegAs_tv_teacher);
        tv_or = findViewById(R.id.RegAs_tv_Or);
        crV_QuranCenter = findViewById(R.id.RegAs_cvieew_QuranCenter);
        crV_Teacher = findViewById(R.id.RegAs_cvieew_teacher);
        crV_guardian = findViewById(R.id.RegAs_cvieew_guardian);
        crVAddRequest = findViewById(R.id.RegAs_cvieew_AddRequest);

        //change font type for textview.
        TextView_EditFont(tv_MakeAccount, "Hacen_Tunisia_Bold.ttf");
        TextView_EditFont(tv_AddRequest, "Hacen_Tunisia.ttf");
        TextView_EditFont(tv_guardian, "Hacen_Tunisia.ttf");
        TextView_EditFont(tv_Teacher, "Hacen_Tunisia.ttf");
        TextView_EditFont(tv_QuranCenter, "Hacen_Tunisia.ttf");
        TextView_EditFont(tv_or, "Hacen_Tunisia.ttf");

        //move to QuranCenter_Reg.class.
        crV_QuranCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(), QuranCenter_Login.class));
            }
        });
        crV_Teacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(), TeacherLogin.class));
            }
        });
        crV_guardian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(), GuardianLogin.class));
            }
        });
        crVAddRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(), JoinRequest1.class));
            }
        });

    }


    //change font type for textview.
    public void TextView_EditFont(TextView textView, String path) {
        textView.setTypeface(Typeface.createFromAsset(getAssets(), path));
    }
}
