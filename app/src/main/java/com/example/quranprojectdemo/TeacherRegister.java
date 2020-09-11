package com.example.quranprojectdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class TeacherRegister extends AppCompatActivity {
    EditText et_TeacherName, et_HalakaName, et_Phone, et_Email, et_Password,et_halakaNum;
    TextView tv_newAccount, tv_I_Have_A_A, tv_Login;
    Button btn_CreateNewA;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_register);

        et_TeacherName=findViewById(R.id.TeacherReg_et_TeacherName);
        et_HalakaName=findViewById(R.id.TeacherReg_et_halakaName);
        et_Phone=findViewById(R.id.TeacherReg_et_PhoneNum);
        et_Email=findViewById(R.id.TeacherReg_et_Email);
        et_halakaNum=findViewById(R.id.TeacherReg_et_halakaNum);
        et_Password=findViewById(R.id.TeacherReg_et_Password);

        tv_newAccount=findViewById(R.id.TeacherReg_tv_newAccount);
        tv_I_Have_A_A=findViewById(R.id.TeacherReg_tv_iHaveAnAccount);
        tv_Login=findViewById(R.id.TeacherReg_tv_Login);

        btn_CreateNewA=findViewById(R.id.TeacherReg_btn_CreateNewAcc);


        EditText_EditFont(et_TeacherName, "Hacen_Tunisia.ttf");
        EditText_EditFont(et_HalakaName, "Hacen_Tunisia.ttf");
        EditText_EditFont(et_Email, "Hacen_Tunisia.ttf");
        EditText_EditFont(et_Password, "Hacen_Tunisia.ttf");
        EditText_EditFont(et_Phone, "Hacen_Tunisia.ttf");
        EditText_EditFont(et_halakaNum, "Hacen_Tunisia.ttf");

        TextView_EditFont(tv_newAccount, "Hacen_Tunisia_Bold.ttf");
        TextView_EditFont(tv_Login, "Hacen_Tunisia.ttf");
        TextView_EditFont(tv_I_Have_A_A, "Hacen_Tunisia.ttf");

        btn_CreateNewA.setTypeface(Typeface.createFromAsset(getAssets(), "Hacen_Tunisia.ttf"));

        btn_CreateNewA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(),Main_teacher.class));
                finish();
            }
        });
        tv_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(),TeacherLogin.class));
                finish();
            }
        });
    }

    //change font type for textview.
    public void TextView_EditFont(TextView textView, String path) {
        textView.setTypeface(Typeface.createFromAsset(getAssets(), path));
    }
    //change font type for edittext.
    public void EditText_EditFont(EditText editText, String path) {
        editText.setTypeface(Typeface.createFromAsset(getAssets(), path));
    }
}
