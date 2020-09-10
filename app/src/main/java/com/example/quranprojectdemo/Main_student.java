package com.example.quranprojectdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class Main_student extends AppCompatActivity {

    Toolbar toolbar_student;

    ImageView image_backe_student  , image_student  ;
    TextView tv_student_name  ,  tv_student_name_ring  , tv_student_phone  ,tv_student_identity;

    TextView tv_date  ,tv_day  ,tv_attendess;
    RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_student);

        image_backe_student=findViewById(R.id.student_main_image_center);
        image_student=findViewById(R.id.student_main_image_student);
        tv_student_name=findViewById(R.id.student_main_tv_name_student);
        tv_student_name_ring=findViewById(R.id.student_main_tv_name_ring);
        tv_student_phone=findViewById(R.id.student_main_tv_phone);
        tv_student_identity=findViewById(R.id.student_main_tv_identity);

//        toolbar_student=findViewById(R.id.student_main_tool);
//        setSupportActionBar(toolbar_student);


        rv=findViewById(R.id.student_main_recycler);


        ArrayList<Student_data> datass =new ArrayList<>();

datass.add(new Student_data("10/9/2020","الخميس","حاضر","الانسان","المدثر"));

        for (Student_data c : datass) {
 Log.d(c.getDate__student(),c.getDay_student()+c.getAttendess_student()+c.getReview_student()+c.getSave_student());
        }

        Recycler_student r_s=new Recycler_student(datass);
        rv.setAdapter(r_s);
        RecyclerView.LayoutManager lm =new LinearLayoutManager(this);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(lm);






    }
}