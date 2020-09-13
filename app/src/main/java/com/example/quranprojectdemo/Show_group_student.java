package com.example.quranprojectdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class Show_group_student extends AppCompatActivity {
//show
    RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_group_student);

        rv=findViewById(R.id.recycler_show_group_student);

        ArrayList<Student_imageand_name> arrayList=new ArrayList<>();

        arrayList.add(new Student_imageand_name("مصطغى محمد الاسطل"));
        arrayList.add(new Student_imageand_name("مصطغى محمد الاسطل"));
        arrayList.add(new Student_imageand_name("مصطغى محمد الاسطل"));
        arrayList.add(new Student_imageand_name("مصطغى محمد الاسطل"));
        arrayList.add(new Student_imageand_name("مصطغى محمد الاسطل"));
        arrayList.add(new Student_imageand_name("مصطغى محمد الاسطل"));
        arrayList.add(new Student_imageand_name("مصطغى محمد الاسطل"));
        arrayList.add(new Student_imageand_name("مصطغى محمد الاسطل"));
        arrayList.add(new Student_imageand_name("مصطغى محمد الاسطل"));
        arrayList.add(new Student_imageand_name("مصطغى محمد الاسطل"));
        arrayList.add(new Student_imageand_name("مصطغى محمد الاسطل"));
        arrayList.add(new Student_imageand_name("مصطغى محمد الاسطل"));
        arrayList.add(new Student_imageand_name("مصطغى محمد الاسطل"));

        Recycler_show_group_student recycler_show_group_student=new Recycler_show_group_student(arrayList);
        rv.setAdapter(recycler_show_group_student);
        RecyclerView.LayoutManager lm =new LinearLayoutManager(this);
        rv.setLayoutManager(lm);



    }
}

