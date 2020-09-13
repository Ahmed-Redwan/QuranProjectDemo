package com.example.quranprojectdemo.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.quranprojectdemo.R;

public class QuranCenter_Login extends AppCompatActivity {
TextView tv_Login,tv_iDontHaveAnAccount,tv_NewAccount;
EditText et_Email,et_password;
Button btn_Login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quran_center__login);

        tv_Login=findViewById(R.id.QuranCenterLogin_tv_login);
        tv_NewAccount=findViewById(R.id.QuranCenterLogin_tv_NewAccount);
        tv_iDontHaveAnAccount=findViewById(R.id.QuranCenterLogin_tv_iDontHaveAnAccount);
        et_Email=findViewById(R.id.QuranCenterLogin_et_EmailOrphone);
        et_password=findViewById(R.id.QuranCenterLogin_et_Password);
        btn_Login=findViewById(R.id.QuranCenterLogin_btn_Login);

        TextView_EditFont(tv_Login, "Hacen_Tunisia_Bold.ttf");
        TextView_EditFont(tv_NewAccount, "Hacen_Tunisia.ttf");
        TextView_EditFont(tv_iDontHaveAnAccount, "Hacen_Tunisia.ttf");

        btn_Login.setTypeface(Typeface.createFromAsset(getAssets(), "Hacen_Tunisia.ttf"));

        EditText_EditFont(et_Email, "Hacen_Tunisia.ttf");
        EditText_EditFont(et_password, "Hacen_Tunisia.ttf");

        btn_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(),Main_center.class));
                finish();
            }
        });

        tv_NewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(),QuranCenter_Reg.class));
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
