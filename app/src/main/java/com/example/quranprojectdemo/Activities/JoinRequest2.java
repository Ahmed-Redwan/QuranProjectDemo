package com.example.quranprojectdemo.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.quranprojectdemo.Other.Center;
import com.example.quranprojectdemo.Other.customRecyclerviewCenters;
import com.example.quranprojectdemo.R;

import java.util.ArrayList;

public class JoinRequest2 extends AppCompatActivity {
    TextView tv_ListOfCenters;
    Button btn_Next;
    RecyclerView rv;
    ArrayList<Center>centers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_request2);

        tv_ListOfCenters = findViewById(R.id.request2_tv_listOfCenters);

        rv=findViewById(R.id.request2_rv_listOfCenters);
        centers=new ArrayList<>();
        centers.add(new Center(R.drawable.arabian,"مركز جنود الفتح القادم","0594114029"));
        centers.add(new Center(R.drawable.student,"مركز الياسين لتعليم القرآن الكريم","0595565213"));
        centers.add(new Center(R.drawable.student2,"مركز أبو بكر الصديق","059875645656"));
        centers.add(new Center(R.drawable.ic_masged,"مركز الحفاظ","0594668456"));
        centers.add(new Center(R.drawable.arabian,"مركز جنود الفتح القادم","0595466225"));

        customRecyclerviewCenters customRecyclerviewCenters=new customRecyclerviewCenters(centers);

        rv.setAdapter(customRecyclerviewCenters);
        RecyclerView.LayoutManager layoutManager=new GridLayoutManager(this,2);
        rv.setLayoutManager(layoutManager);
        rv.setHasFixedSize(true);



        tv_ListOfCenters.setTypeface(Typeface.createFromAsset(getAssets(),"Hacen_Tunisia_Bold.ttf"));

        btn_Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(), JoinRequest3.class));
            }
        });
    }


}
