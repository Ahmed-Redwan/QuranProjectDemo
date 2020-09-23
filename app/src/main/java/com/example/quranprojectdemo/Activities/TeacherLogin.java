package com.example.quranprojectdemo.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quranprojectdemo.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class TeacherLogin extends AppCompatActivity {
    public static final String INFO_TEACHER = "info_tet";
    public static final String ID_LOGIN_TEACHER = "tet_id";
    public static final String ID_LOGIN_TEC_CENTER = "tet_log_center_id";
    TextView tv_Login, tv_iDontHaveAnAccount, tv_NewAccount;
    EditText et_Email, et_password;
    Button btn_Login;
    public FirebaseAuth mAuth;
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    CheckBox cb_remmemberMe;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_login);
        mAuth = FirebaseAuth.getInstance();

        tv_Login = findViewById(R.id.TeacheLogin_tv_login);
        tv_NewAccount = findViewById(R.id.TeacheLogin_tv_NewAccount);
        tv_iDontHaveAnAccount = findViewById(R.id.TeacheLogin_tv_iDontHaveAnAccount);
        et_Email = findViewById(R.id.TeacheLogin_et_EmailOrphone);
        et_password = findViewById(R.id.TeacheLogin_et_Password);
        btn_Login = findViewById(R.id.TeacheLogin_btn_Login);
        cb_remmemberMe=findViewById(R.id.GuardianLogin_Cb_remmemberme);


        TextView_EditFont(tv_NewAccount, "Hacen_Tunisia.ttf");
        TextView_EditFont(tv_iDontHaveAnAccount, "Hacen_Tunisia.ttf");
        TextView_EditFont(tv_Login, "Hacen_Tunisia_Bold.ttf");

        btn_Login.setTypeface(Typeface.createFromAsset(getAssets(), "Hacen_Tunisia.ttf"));

        EditText_EditFont(et_Email, "Hacen_Tunisia.ttf");
        EditText_EditFont(et_password, "Hacen_Tunisia.ttf");


        tv_NewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(), TeacherRegister.class));
                finish();
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        sp = getSharedPreferences(INFO_TEACHER, MODE_PRIVATE);
        editor = sp.edit();
        btn_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              //  if (et_Email.getText().toString().isEmpty())
                    if (!Patterns.EMAIL_ADDRESS.matcher(et_Email.getText().toString()).matches())
                    {
                    et_Email.setError("يجب ادخال الايميل ادخالا صحيحاً");
                    return;
                }  else if (et_password.getText().toString().isEmpty())
                {
                    et_password.setError("يجب ادخال كلمة المرور.");
                    return;
                }
                else if (et_password.getText().toString().length()<7)
                {
                    et_password.setError("يجب أن تكون كلمة المرور أكثر من 7 حروف.");
                    return;
                }



                log_in();
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            /*    if (cb_remmemberMe.isChecked()){
                    editor.putString("Sp_Email",et_Email.getText().toString());
                    editor.putString("Sp_password",et_Email.getText().toString());
                    editor.apply();
                    et_Email.setText(sp.getString("Sp_Email",""));
                    et_Email.setText(sp.getString("Sp_password",""));
                }*/


//                startActivity(new Intent(getBaseContext(), Main_teacher.class));
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
                            editor.putString(ID_LOGIN_TEACHER, user.getUid());
                            editor.putString(ID_LOGIN_TEC_CENTER, user.getDisplayName());
                            editor.apply();

                            startActivity(new Intent(getBaseContext(), Main_teacher.class));

                        } else {
                            et_Email.setError("تأكد من الإيميل و كلمة المرور.");
                            et_password.setError("تأكد من الإيميل و كلمة المرور.");
                            Toast.makeText(TeacherLogin.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }//للدخول

    //change font type for textview.
    public void TextView_EditFont(TextView textView, String path) {
        textView.setTypeface(Typeface.createFromAsset(getAssets(), path));
    }

    //change font type for edittext.
    public void EditText_EditFont(EditText editText, String path) {
        editText.setTypeface(Typeface.createFromAsset(getAssets(), path));
    }
}