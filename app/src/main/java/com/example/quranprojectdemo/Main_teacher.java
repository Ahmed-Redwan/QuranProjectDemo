package com.example.quranprojectdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

public class Main_teacher extends AppCompatActivity {

    ImageView  image_backe_teacher  , image_teacher  ;
    TextView  tv_teacher_name  ,  tv_teacher_name_ring  , tv_teacher_phone  ,tv_teacher_count_student;

    Toolbar toolbar_teacher ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_teacher);

        image_backe_teacher=findViewById(R.id.teacher_main_image_center);
        image_teacher=findViewById(R.id.teacher_main_image_teacher);
        tv_teacher_name=findViewById(R.id.teacher_main_tv_name_teacher);
        tv_teacher_name_ring=findViewById(R.id.teacher_main_tv_name_ring);
        tv_teacher_phone=findViewById(R.id.teacher_main_tv_phone);
        tv_teacher_count_student=findViewById(R.id.teacher_main_tv_count_student);

        toolbar_teacher=findViewById(R.id.teacher_main_tool);
        toolbar_teacher.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.MenuTeacherHomeAddStudent:
                        return true;
                    case R.id.MenuTeacherHomeShowInfo:
                        return true;
                    case R.id.MenuTeacherHomeSettings:
                        return true;
                    case R.id.MenuTeacherHomeAbout:
                        return true;
                    case R.id.MenuTeacherHomeExit:
                        return true;
                }
                return false;
            }
        });
    }
}