package com.example.quranprojectdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class RegisterAs extends AppCompatActivity {

    TextView tv_MakeAccount, tv_QuranCenter, tv_Teacher, tv_guardian, tv_AddRequest, tv_or;
    CardView crV_QuranCenter, crV_Teacher, crV_guardian, crVAddRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_as);

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
                startActivity(new Intent(getBaseContext(),QuranCenter_Reg.class));
            }
        });
        crV_Teacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(),TeacherRegister.class));
            }
        });
        crV_guardian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(),GuardianReg.class));
            }
        });
        crVAddRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(getBaseContext(),JoinRequest1.class);
              startActivity(intent);
            }
        });

    }

//  startActivity(new Intent(getBaseContext(),JoinRequest1.class));
    //change font type for textview.
    public void TextView_EditFont(TextView textView, String path) {
        textView.setTypeface(Typeface.createFromAsset(getAssets(), path));
    }
}
