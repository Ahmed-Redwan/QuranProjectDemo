package com.example.quranprojectdemo.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.quranprojectdemo.R;

public class Main_center extends AppCompatActivity {

    Toolbar toolbar_center;
    ImageView image_center;
    TextView tv_center_name, tv_center_name_maneger, tv_center_phone, tv_center_count_ring, tv_center_count_student;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_center);

        image_center = findViewById(R.id.center_main_image);
        tv_center_name = findViewById(R.id.center_main_tv_name_center);
        tv_center_name_maneger = findViewById(R.id.center_main_tv_name_maneger);
        tv_center_phone = findViewById(R.id.center_main_tv_phone);
        tv_center_count_ring = findViewById(R.id.center_main_tv_count_ring);
        tv_center_count_student = findViewById(R.id.center_main_tv_count_student);

        toolbar_center = findViewById(R.id.center_main_tool);
        toolbar_center.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.MenuCentreHomeAddGroub:
                        startActivity(new Intent(getBaseContext(), AddNewGroup.class));
                        return true;
                    case R.id.MenuCentreHomeAddStudent:
                        startActivity(new Intent(getBaseContext(), AddNewStudent.class));
                        return true;
                    case R.id.MenuCentreHomeShowInfo:
                        startActivity(new Intent(getBaseContext(), ShowmeMorizationLoops.class));
                        return true;
                    case R.id.MenuCentreHomeRequestsList:
                        startActivity(new Intent(getBaseContext(), JoinRequests.class));
                        return true;
                    case R.id.MenuCenterHomeExit:
                        finish();
                        return true;
                    case R.id.MenuCenterHomeSettings:
                        return true;
                    case R.id.MenuCenterHomeAbout:
                        return true;
                }
                return false;
            }
        });

//
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }
}