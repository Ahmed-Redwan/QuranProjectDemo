package com.example.quranprojectdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ShowmeMorizationLoops extends AppCompatActivity {
TextView tv_ShowMemorizationLoops;
RecyclerView rv_List;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_memorization_loops);

        tv_ShowMemorizationLoops=findViewById(R.id.ShowMemorizationLoops_tv_ShowMemorizationLoops);
        rv_List=findViewById(R.id.ShowMemorizationLoops_Rv_List);

        ArrayList<Group> data =new ArrayList<>();
        data.add(new Group(R.drawable.main_center_image,"ابو بكر الصديق","احمد عبد الغفور"));
        data.add(new Group(R.drawable.fill_circle,"عمر بن الخطاب","أحمد اليعقوبي"));
        data.add(new Group(R.drawable.btn_background2,"ابو بكر الصديق","مصطفى الأسطل"));
        data.add(new Group(R.drawable.empty_circle,"ابو بكر الصديق","معتز ماضي"));

        final CustomGroupRecyclerView customGroupRecyclerView=new CustomGroupRecyclerView(data);

        rv_List.setHasFixedSize(true);
        rv_List.setAdapter(customGroupRecyclerView);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(this);
        rv_List.setLayoutManager(layoutManager);

    }


}
