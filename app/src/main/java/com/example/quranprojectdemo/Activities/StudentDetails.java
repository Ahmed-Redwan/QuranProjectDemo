package com.example.quranprojectdemo.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.quranprojectdemo.R;

public class StudentDetails extends AppCompatActivity {
    TextView tv_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_details);
        tv_name=findViewById(R.id.StudentDetails_tv_Name);
       androidx.appcompat.widget.Toolbar toolbar=findViewById(R.id.StudentDetails_toolBar);

        Intent getExtrasIntent = getIntent();
        String name=getExtrasIntent.getStringExtra("name");
        tv_name.setText(name);
        toolbar.setTitle(name);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId()==R.id.GroupListMenu_back) {
                    finish();
                    return true;
                }
                return false;
            }
        });

    }
}
