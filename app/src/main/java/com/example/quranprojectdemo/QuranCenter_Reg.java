package com.example.quranprojectdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class QuranCenter_Reg extends AppCompatActivity {

    EditText et_centerName, et_ManagerName, et_Phone, et_Email, et_Password,et_country,et_city;
    TextView tv_newAccount, tv_I_Have_A_A, tv_Login;
    Button btn_CreateNewA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quran_center__reg);


        et_centerName = findViewById(R.id.QuranCenter_et_CenterName);
        et_ManagerName = findViewById(R.id.QuranCenter_et_ManagerName);
        et_Email = findViewById(R.id.QuranCenter_et_Email);
        et_Phone = findViewById(R.id.QuranCenter_et_PhoneNum);
        et_country=findViewById(R.id.QuranCenter_et_Country);
        et_city=findViewById(R.id.QuranCenter_et_City);
        et_Password = findViewById(R.id.QuranCenter_et_Password);
        btn_CreateNewA = findViewById(R.id.QuranCenter_btn_CreateNewAcc);
        tv_Login = findViewById(R.id.QuranCenter_tv_Login);
        tv_newAccount = findViewById(R.id.QuranCenter_tv_newAccount);
        tv_I_Have_A_A = findViewById(R.id.QuranCenter_tv_iHaveAnAccount);

        EditText_EditFont(et_centerName, "Hacen_Tunisia.ttf");
        EditText_EditFont(et_ManagerName, "Hacen_Tunisia.ttf");
        EditText_EditFont(et_Email, "Hacen_Tunisia.ttf");
        EditText_EditFont(et_Password, "Hacen_Tunisia.ttf");
        EditText_EditFont(et_Phone, "Hacen_Tunisia.ttf");
        EditText_EditFont(et_country, "Hacen_Tunisia.ttf");
        EditText_EditFont(et_city, "Hacen_Tunisia.ttf");

        TextView_EditFont(tv_newAccount, "Hacen_Tunisia_Bold.ttf");
        TextView_EditFont(tv_Login, "Hacen_Tunisia.ttf");
        TextView_EditFont(tv_I_Have_A_A, "Hacen_Tunisia.ttf");

        btn_CreateNewA.setTypeface(Typeface.createFromAsset(getAssets(), "Hacen_Tunisia.ttf"));

        btn_CreateNewA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(),Main_center.class));
                finish();
            }
        });

        tv_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(),QuranCenter_Login.class));
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
