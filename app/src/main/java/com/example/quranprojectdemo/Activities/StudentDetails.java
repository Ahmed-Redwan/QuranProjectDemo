package com.example.quranprojectdemo.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.quranprojectdemo.R;

public class StudentDetails extends AppCompatActivity {
    TextView tv_student_name  ,  tv_student_name_ring  , tv_student_phone  ,tv_student_identity;
    ImageView image_backe_student  , image_student  ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_details);

       androidx.appcompat.widget.Toolbar toolbar=findViewById(R.id.StudentDetails_toolBar);
        image_backe_student=findViewById(R.id.StudentDetails_image_center);
        image_student=findViewById(R.id.StudentDetails_image_student);
        tv_student_name=findViewById(R.id.StudentDetails_tv_name_student);
        tv_student_name_ring=findViewById(R.id.StudentDetails_tv_name_ring);
        tv_student_phone=findViewById(R.id.StudentDetails_tv_phone);
        tv_student_identity=findViewById(R.id.StudentDetails_identity);

        Intent getExtrasIntent = getIntent();
        String name=getExtrasIntent.getStringExtra("name");
        tv_student_name.setText(name);
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
