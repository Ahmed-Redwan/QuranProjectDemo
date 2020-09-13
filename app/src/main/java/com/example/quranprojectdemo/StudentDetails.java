package com.example.quranprojectdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class StudentDetails extends AppCompatActivity {
    TextView tv_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_details);
        tv_name=findViewById(R.id.StudentDetails_tv_Name);

        Intent getExtrasIntent = getIntent();
        tv_name.setText(getExtrasIntent.getStringExtra("name"));
    }
}
