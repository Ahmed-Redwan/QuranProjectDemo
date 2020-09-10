package com.example.quranprojectdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {

    RecyclerView rv;
    Recycler_student recycler_student;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        rv=findViewById(R.id.student_rv);

        ArrayList<Student_data> student_data=new ArrayList<>();
        student_data.add
    (new Student_data("10/9/2020","asd","dsa","asd","55"));


        recycler_student =new Recycler_student(student_data);
        rv.setAdapter(recycler_student);
        RecyclerView.LayoutManager lm =new LinearLayoutManager(this);


    }
}