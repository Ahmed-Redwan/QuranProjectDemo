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
import android.widget.Toast;

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

        btn_JoinRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (et_studentName.getText().toString().isEmpty()||et_studentId.getText().toString().isEmpty()||et_Email.getText().toString().isEmpty()||et_Phone.getText().toString().isEmpty()||et_Grade.getText().toString().isEmpty()||et_Day.getText().toString().isEmpty()||et_Month.getText().toString().isEmpty()||et_Year.getText().toString().isEmpty()){
                    et_Month.setError("يجب تعبئة جميع الحقول.");
                    et_Year.setError("يجب تعبئة جميع الحقول.");
                    et_Day.setError("يجب تعبئة جميع الحقول.");
                    et_Phone.setError("يجب تعبئة جميع الحقول.");
                    et_Email.setError("يجب تعبئة جميع الحقول.");
                    et_studentId.setError("يجب تعبئة جميع الحقول.");
                    et_studentName.setError("يجب تعبئة جميع الحقول.");
                    et_Grade.setError("يجب تعبئة جميع الحقول.");
                    return;
                }

                Toast.makeText(view.getContext(), "لفد تم إرسال طلبك بنجاح " +
                        "سيتم الرد عليك في أقرب وقت", Toast.LENGTH_SHORT).show();
                //startActivity(new Intent(getBaseContext(),RegisterAs.class));
                finish();
            }
        });
    }


    public void TextView_EditFont(TextView textView, String path) {
        textView.setTypeface(Typeface.createFromAsset(getAssets(), path));
    }

    //change font type for edittext.
    public void EditText_EditFont(EditText editText, String path) {
        editText.setTypeface(Typeface.createFromAsset(getAssets(), path));
    }

}
