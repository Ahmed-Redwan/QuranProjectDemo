package com.example.quranprojectdemo.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
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
import com.shashank.sony.fancytoastlib.FancyToast;

public class GuardianLogin extends AppCompatActivity {
    TextView tv_Login, tv_iDontHaveAnAccount, tv_NewAccount;
    EditText et_Email, et_password;
    Button btn_Login;
    private FirebaseAuth mAuth;
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    boolean b = false;
    private CheckBox saveLoginCheckBox;
    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;
    private Boolean saveLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guardian_login);
        mAuth = FirebaseAuth.getInstance();
        tv_Login = findViewById(R.id.GuardianLogin_tv_login);
        tv_NewAccount = findViewById(R.id.GuardianLogin_tv_NewAccount);
        tv_iDontHaveAnAccount = findViewById(R.id.GuardianLogin_tv_iDontHaveAnAccount);
        et_Email = findViewById(R.id.GuardianLogin_et_EmailOrphone);
        et_password = findViewById(R.id.GuardianLogin_et_Password);
        btn_Login = findViewById(R.id.GuardianLogin_btn_Login);

        saveLoginCheckBox = findViewById(R.id.GuardianLogin_Cb_remmemberme);
        loginPreferences = getSharedPreferences("loginPrefsGuardian", MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();

        saveLogin = loginPreferences.getBoolean("saveLogin", false);
        if (saveLogin == true) {
            et_Email.setText(loginPreferences.getString("username", ""));
            et_password.setText(loginPreferences.getString("password", ""));
            saveLoginCheckBox.setChecked(true);
        }





        TextView_EditFont(tv_Login, "Hacen_Tunisia_Bold.ttf");
        TextView_EditFont(tv_NewAccount, "Hacen_Tunisia.ttf");
        TextView_EditFont(tv_iDontHaveAnAccount, "Hacen_Tunisia.ttf");

        btn_Login.setTypeface(Typeface.createFromAsset(getAssets(), "Hacen_Tunisia.ttf"));

        EditText_EditFont(et_Email, "Hacen_Tunisia.ttf");
        EditText_EditFont(et_password, "Hacen_Tunisia.ttf");

        btn_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (et_Email.getText().toString().isEmpty())
                {
                    et_Email.setError("يجب ادخال الايميل أو رقم الهاتف.");
                    return;
                }  else if (et_password.getText().toString().isEmpty())
                {
                    et_password.setError("يجب ادخال كلمة المرور.");
                    return;
                }  else if (et_password.getText().toString().length()<7)
                {
                    et_password.setError("يجب أن تكون كلمة المرور أكثر من 7 حروف.");
                    return;
                }

                log_in();


                String username = et_Email.getText().toString();
                String password = et_password.getText().toString();

                if (saveLoginCheckBox.isChecked()) {
                    loginPrefsEditor.putBoolean("saveLogin", true);
                    loginPrefsEditor.putString("username", username);
                    loginPrefsEditor.putString("password", password);
                    loginPrefsEditor.commit();
                } else {
                    loginPrefsEditor.clear();
                    loginPrefsEditor.commit();
                }

//               finish();
            }
        });
        tv_NewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(), GuardianReg.class));
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

    private void log_in() {

        mAuth.signInWithEmailAndPassword(et_Email.getText().toString(), et_password.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            FancyToast.makeText(getBaseContext(),"Login is successful",FancyToast.LENGTH_LONG,FancyToast.SUCCESS,true).show();
                            startActivity(new Intent(getBaseContext(), Main_student.class));
                        } else {
                            et_Email.setError("تأكد من الإيميل و كلمة المرور.");
                            et_password.setError("تأكد من الإيميل و كلمة المرور.");//
                            FancyToast.makeText(getBaseContext(),"Login failed",FancyToast.LENGTH_LONG,FancyToast.CONFUSING,true).show();
                         //   Toast.makeText(GuardianLogin.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                            b = false;
                        }
                    }
                });
    }//للدخول

}
