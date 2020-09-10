package com.example.quranprojectdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class GuardianReg extends AppCompatActivity {
    EditText et_StudentName, et_StudentId, et_Phone, et_Email, et_Password,et_HalakaNum;
    TextView tv_newAccount, tv_I_Have_A_A, tv_Login;
    Button btn_CreateNewA;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guardian_reg);

        et_StudentName=findViewById(R.id.GurdianReg_et_StudentName);
        et_StudentId=findViewById(R.id.GurdianReg_et_StudentId);
        et_Email=findViewById(R.id.GurdianReg_et_Email);
        et_Phone=findViewById(R.id.GurdianReg_et_PhoneNum);
        et_HalakaNum=findViewById(R.id.GurdianReg_et_halakaNum);
        et_Password=findViewById(R.id.GurdianReg_et_Password);

        tv_newAccount=findViewById(R.id.GurdianReg_tv_newAccount);
        tv_I_Have_A_A=findViewById(R.id.GurdianReg_tv_iHaveAnAccount);
        tv_Login=findViewById(R.id.GurdianReg_tv_Login);

        btn_CreateNewA=findViewById(R.id.GurdianReg_btn_CreateNewAcc);

        EditText_EditFont(et_StudentName, "Hacen_Tunisia.ttf");
        EditText_EditFont(et_StudentId, "Hacen_Tunisia.ttf");
        EditText_EditFont(et_Email, "Hacen_Tunisia.ttf");
        EditText_EditFont(et_Password, "Hacen_Tunisia.ttf");
        EditText_EditFont(et_Phone, "Hacen_Tunisia.ttf");
        EditText_EditFont(et_HalakaNum, "Hacen_Tunisia.ttf");

        TextView_EditFont(tv_newAccount, "Hacen_Tunisia_Bold.ttf");
        TextView_EditFont(tv_Login, "Hacen_Tunisia.ttf");
        TextView_EditFont(tv_I_Have_A_A, "Hacen_Tunisia.ttf");

        btn_CreateNewA.setTypeface(Typeface.createFromAsset(getAssets(), "Hacen_Tunisia.ttf"));

        tv_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(),GuardianLogin.class));
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
