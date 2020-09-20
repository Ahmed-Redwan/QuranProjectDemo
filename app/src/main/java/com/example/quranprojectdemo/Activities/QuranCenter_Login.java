package com.example.quranprojectdemo.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quranprojectdemo.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class QuranCenter_Login extends AppCompatActivity {
    TextView tv_Login, tv_iDontHaveAnAccount, tv_NewAccount;
    EditText et_Email, et_password;
    Button btn_Login;
    public  FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quran_center__login);
//        FirebaseAuth.getInstance().signOut();
        mAuth = FirebaseAuth.getInstance();

        tv_Login = findViewById(R.id.QuranCenterLogin_tv_login);
        tv_NewAccount = findViewById(R.id.QuranCenterLogin_tv_NewAccount);
        tv_iDontHaveAnAccount = findViewById(R.id.QuranCenterLogin_tv_iDontHaveAnAccount);
        et_Email = findViewById(R.id.QuranCenterLogin_et_EmailOrphone);
        et_password = findViewById(R.id.QuranCenterLogin_et_Password);
        btn_Login = findViewById(R.id.QuranCenterLogin_btn_Login);

        TextView_EditFont(tv_Login, "Hacen_Tunisia_Bold.ttf");
        TextView_EditFont(tv_NewAccount, "Hacen_Tunisia.ttf");
        TextView_EditFont(tv_iDontHaveAnAccount, "Hacen_Tunisia.ttf");

        btn_Login.setTypeface(Typeface.createFromAsset(getAssets(), "Hacen_Tunisia.ttf"));

        EditText_EditFont(et_Email, "Hacen_Tunisia.ttf");
        EditText_EditFont(et_password, "Hacen_Tunisia.ttf");



        tv_NewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(), QuranCenter_Reg.class));
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

    @Override
    protected void onStart() {
        super.onStart();
        btn_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                log_in();
                startActivity(new Intent(getBaseContext(), Main_center.class));
//                finish();
            }
        });

    }

    private void log_in() {

        mAuth.signInWithEmailAndPassword(et_Email.getText().toString(), et_password.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                Toast.makeText(getBaseContext(),user.getUid()+"aa",Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(QuranCenter_Login.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }//للدخول

}
