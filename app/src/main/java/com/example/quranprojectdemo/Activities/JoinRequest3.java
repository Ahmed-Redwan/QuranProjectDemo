package com.example.quranprojectdemo.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.quranprojectdemo.R;

public class JoinRequest3 extends AppCompatActivity {
    TextView tv_JoinRequest;
    Button btn_JoinRequest;
    EditText et_studentName, et_studentId, et_Phone, et_Email, et_Grade, et_Year, et_Month, et_Day;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_request3);

        tv_JoinRequest = findViewById(R.id.request3_tv_SendRequest);
        et_studentName = findViewById(R.id.request3_et_StudentName);
        et_studentId = findViewById(R.id.request3_et_StudentId);
        et_Email = findViewById(R.id.request3_et_Email);
        et_Phone = findViewById(R.id.request3_et_PhoneNum);
        et_Grade = findViewById(R.id.request3_et_Grade);
        et_Year = findViewById(R.id.request3_et_year);
        et_Month = findViewById(R.id.request3_et_month);
        et_Day = findViewById(R.id.request3_et_day);
        btn_JoinRequest = findViewById(R.id.request3_btn_SendRequest);


        TextView_EditFont(tv_JoinRequest, "Hacen_Tunisia_Bold.ttf");
        EditText_EditFont(et_studentName, "Hacen_Tunisia.ttf");
        EditText_EditFont(et_studentId, "Hacen_Tunisia.ttf");
        EditText_EditFont(et_Email, "Hacen_Tunisia.ttf");
        EditText_EditFont(et_Grade, "Hacen_Tunisia.ttf");
        EditText_EditFont(et_Phone, "Hacen_Tunisia.ttf");
        EditText_EditFont(et_Month, "Hacen_Tunisia.ttf");
        EditText_EditFont(et_Year, "Hacen_Tunisia.ttf");
        EditText_EditFont(et_Day, "Hacen_Tunisia.ttf");

        btn_JoinRequest.setTypeface(Typeface.createFromAsset(getAssets(), "Hacen_Tunisia.ttf"));
    }


    public void TextView_EditFont(TextView textView, String path) {
        textView.setTypeface(Typeface.createFromAsset(getAssets(), path));
    }

    //change font type for edittext.
    public void EditText_EditFont(EditText editText, String path) {
        editText.setTypeface(Typeface.createFromAsset(getAssets(), path));
    }

}
