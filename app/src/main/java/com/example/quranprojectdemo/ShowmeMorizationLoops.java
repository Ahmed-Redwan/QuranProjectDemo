package com.example.quranprojectdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

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
        data.add(new Group(R.drawable.empty_circle,"ابو بكر الصديق","احمد عبد الغفور"));
        data.add(new Group(R.drawable.empty_circle,"ابو بكر الصديق","احمد عبد الغفور"));
        data.add(new Group(R.drawable.empty_circle,"ابو بكر الصديق","احمد عبد الغفور"));
        data.add(new Group(R.drawable.empty_circle,"ابو بكر الصديق","احمد عبد الغفور"));

        CustomGroupRecyclerView customGroupRecyclerView=new CustomGroupRecyclerView(data);

        rv_List.setHasFixedSize(true);
        rv_List.setAdapter(customGroupRecyclerView);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(this);
        rv_List.setLayoutManager(layoutManager);

    }
}
