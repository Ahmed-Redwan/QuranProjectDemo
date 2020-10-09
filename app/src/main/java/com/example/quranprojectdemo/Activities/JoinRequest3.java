package com.example.quranprojectdemo.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quranprojectdemo.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.shashank.sony.fancytoastlib.FancyToast;

public class JoinRequest3 extends AppCompatActivity {
    public static final String INFO_CENTER_REG = "info_reg";
    public static final String ID_CENTER_REG = "id_center_reg";
    TextView tv_JoinRequest;
    Button btn_JoinRequest;
    EditText et_studentName, et_studentId, et_Phone, et_Email, et_Grade, et_Year, et_Month, et_Day;
    SharedPreferences sp;
    private FirebaseAuth mAuth;
    SharedPreferences.Editor editor;
    String centerId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_request3);

        mAuth = FirebaseAuth.getInstance();


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

        Intent getIntent = getIntent();
        centerId = getIntent.getStringExtra("CenterId");


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

                if (et_studentName.getText().toString().isEmpty() || et_studentId.getText().toString().isEmpty() || et_Email.getText().toString().isEmpty() || et_Phone.getText().toString().isEmpty() || et_Grade.getText().toString().isEmpty() || et_Day.getText().toString().isEmpty() || et_Month.getText().toString().isEmpty() || et_Year.getText().toString().isEmpty()) {
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

                FancyToast.makeText(getBaseContext(),"تم إرسال طلبك للمركز بنجاح ,يرجى الإنتظار حتى يتم قبولك.",FancyToast.LENGTH_LONG,FancyToast.SUCCESS,false).show();

                //startActivity(new Intent(getBaseContext(),RegisterAs.class));
                setInRealTimeUsers(centerId);
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


    public void setInRealTimeUsers(String CenterId) {


        FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
        final DatabaseReference reference = rootNode.getReference("CenterUsers");
        final DatabaseReference reference2 = rootNode.getReference();

//int id, String name, int age, String address, String email, String phone
//        reference.child(CenterId).child("Requests").child(et_studentId.getText().toString()).setValue(
//                new Student_Info(centerId, et_studentName.getText().toString(),
//                    (et_studentId.getText().toString()),
//                        et_Phone.getText().toString(),
//                        et_Email.getText().toString(),
//                        et_Grade.getText().toString(),
//                        et_Day.getText().toString() + "/" + et_Month.getText().toString() + "/" + et_Year.getText().toString()));

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }//اضافة بيانات
}
