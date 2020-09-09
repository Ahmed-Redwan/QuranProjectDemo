package com.example.quranprojectdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RegisterAs extends AppCompatActivity {
    Button btn_QuranCenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_as);

        btn_QuranCenter=findViewById(R.id.RegAs_btn_QuranCenter);
        btn_QuranCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(),QuranCenter_Reg.class));
            }
        });
    }
}
