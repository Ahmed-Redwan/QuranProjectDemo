package com.example.quranprojectdemo.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.quranprojectdemo.R;
import com.example.quranprojectdemo.Other.Recycler_show_group_student;
import com.example.quranprojectdemo.Other.Student_imageand_name;

import java.util.ArrayList;

public class Show_group_student extends AppCompatActivity {
//show
    //show asd
    RecyclerView rv;
    TextView tv_show;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_group_student);
        toolbar=findViewById(R.id.StudentsList_ToolBar);
        rv=findViewById(R.id.recycler_show_group_student);
        tv_show=findViewById(R.id.ShowStudentsList_tv_show);
        tv_show.setTypeface(Typeface.createFromAsset(getAssets(), "Hacen_Tunisia_Bold.ttf"));

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId()==R.id.GroupListMenu_back){
                    finish();
                    return true;
                }
                return false;
            }
        });

        ArrayList<Student_imageand_name> arrayList=new ArrayList<>();

        arrayList.add(new Student_imageand_name("مصطفى محمد الاسطل"));
        arrayList.add(new Student_imageand_name("أحمد عبد الغفور"));
        arrayList.add(new Student_imageand_name("محمد الاغا"));
        arrayList.add(new Student_imageand_name("عبد الرحيم شراب"));
        arrayList.add(new Student_imageand_name("أحمد اليعقوبي"));
        arrayList.add(new Student_imageand_name("معتز ماضي"));


        Recycler_show_group_student recycler_show_group_student=new Recycler_show_group_student(arrayList);
        rv.setAdapter(recycler_show_group_student);
        RecyclerView.LayoutManager lm =new LinearLayoutManager(this);
        rv.setLayoutManager(lm);



    }
}

