package com.example.quranprojectdemo.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.quranprojectdemo.Other.CustomGroupRecyclerView;
import com.example.quranprojectdemo.Other.Group;
import com.example.quranprojectdemo.R;

import java.util.ArrayList;

public class ShowmeMorizationLoops extends AppCompatActivity {
    TextView tv_ShowMemorizationLoops;
    RecyclerView rv_List;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_memorization_loops);
        toolbar = findViewById(R.id.ShowMemorizationLoops_ToolBar);
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

        tv_ShowMemorizationLoops = findViewById(R.id.ShowMemorizationLoops_tv_ShowMemorizationLoops);
        rv_List = findViewById(R.id.ShowMemorizationLoops_Rv_List);

        tv_ShowMemorizationLoops.setTypeface(Typeface.createFromAsset(getAssets(), "Hacen_Tunisia_Bold.ttf"));

        ArrayList<Group> data = new ArrayList<>();
        data.add(new Group(R.drawable.arabian, "ابو بكر الصديق", "احمد عبد الغفور"));
        data.add(new Group(R.drawable.student, "عمر بن الخطاب", "أحمد اليعقوبي"));
        data.add(new Group(R.drawable.student2, "ابو بكر الصديق", "مصطفى الأسطل"));
        data.add(new Group(R.drawable.ic_person, "ابو بكر الصديق", "معتز ماضي"));

        final CustomGroupRecyclerView customGroupRecyclerView = new CustomGroupRecyclerView(data);

        rv_List.setHasFixedSize(true);
        rv_List.setAdapter(customGroupRecyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rv_List.setLayoutManager(layoutManager);

    }


}
