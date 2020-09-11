package com.example.quranprojectdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class JoinRequest2 extends AppCompatActivity {
    TextView tv_ListOfCenters;
    Button btn_Next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_request2);

        btn_Next = findViewById(R.id.request2_btn_Next);
        tv_ListOfCenters = findViewById(R.id.request2_tv_listOfCenters);



        btn_Next.setTypeface(Typeface.createFromAsset(getAssets(),"Hacen_Tunisia.ttf"));
        tv_ListOfCenters.setTypeface(Typeface.createFromAsset(getAssets(),"Hacen_Tunisia_Bold.ttf"));

        btn_Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(), JoinRequest3.class));
            }
        });
    }


}
