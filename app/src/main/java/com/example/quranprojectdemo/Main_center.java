package com.example.quranprojectdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class Main_center extends AppCompatActivity {

    Toolbar toolbar_center;
    ImageView image_center;
 TextView  tv_center_name  ,  tv_center_name_maneger  , tv_center_phone  ,  tv_center_count_ring  ,tv_center_count_student;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_center);

        image_center=findViewById(R.id.center_main_image);
        tv_center_name=findViewById(R.id.center_main_tv_name_center);
        tv_center_name_maneger=findViewById(R.id.center_main_tv_name_maneger);
        tv_center_phone=findViewById(R.id.center_main_tv_phone);
        tv_center_count_ring=findViewById(R.id.center_main_tv_count_ring);
        tv_center_count_student=findViewById(R.id.center_main_tv_count_student);

        toolbar_center=findViewById(R.id.center_main_tool);
        setSupportActionBar(toolbar_center);

    }
}