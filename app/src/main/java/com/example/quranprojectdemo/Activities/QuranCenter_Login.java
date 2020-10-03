package com.example.quranprojectdemo.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quranprojectdemo.Other.CenterUser;
import com.example.quranprojectdemo.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shashank.sony.fancytoastlib.FancyToast;

import io.realm.Realm;

public class QuranCenter_Login extends AppCompatActivity {
    public static final String ID_CENTER_LOGIN = "id_center_log";
    public static final String INFO_CENTER_LOGIN = "Info_center";
    TextView tv_Login, tv_iDontHaveAnAccount, tv_NewAccount;
    EditText et_Email, et_password;
    Button btn_Login;
    public FirebaseAuth mAuth;
    SharedPreferences sp;
    SharedPreferences.Editor editor;

    private CheckBox saveLoginCheckBox;
    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;
    private Boolean saveLogin;
    private CenterUser user;
    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Realm.init(getBaseContext());
        realm = Realm.getDefaultInstance();

        setContentView(R.layout.activity_quran_center__login);
//        FirebaseAuth.getInstance().signOut();
        mAuth = FirebaseAuth.getInstance();

        tv_Login = findViewById(R.id.QuranCenterLogin_tv_login);
        tv_NewAccount = findViewById(R.id.QuranCenterLogin_tv_NewAccount);
        tv_iDontHaveAnAccount = findViewById(R.id.QuranCenterLogin_tv_iDontHaveAnAccount);
        et_Email = findViewById(R.id.QuranCenterLogin_et_EmailOrphone);
        et_password = findViewById(R.id.QuranCenterLogin_et_Password);
        btn_Login = findViewById(R.id.QuranCenterLogin_btn_Login);
        saveLoginCheckBox = findViewById(R.id.QuranCenterLogin_Cb_remmemberme);
        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
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
                if (et_Email.getText().toString().isEmpty()) {
                    et_Email.setError("يجب ادخال الايميل أو رقم الهاتف.");
                    return;
                } else if (et_password.getText().toString().isEmpty()) {
                    et_password.setError("يجب ادخال كلمة المرور.");
                    return;
                } else if (et_password.getText().toString().length() < 5) {
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
                            sp = getSharedPreferences(INFO_CENTER_LOGIN, MODE_PRIVATE);
                            editor = sp.edit();
                            editor.putString(ID_CENTER_LOGIN, user.getUid());
                            editor.apply();
                            sp = getSharedPreferences(QuranCenter_Reg.INFO_CENTER_REG, MODE_PRIVATE);
                            editor = sp.edit();
                            editor.clear();
                            editor.apply();
                            add_info_center_to_realm(user.getUid());

                            FancyToast.makeText(getBaseContext(), "تم تسجيل الدخول بنجاح.", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
                            startActivity(new Intent(getBaseContext(), Main_center.class));
//
                        } else {
                            et_Email.setError("تأكد من الإيميل و كلمة المرور.");
                            et_password.setError("تأكد من الإيميل و كلمة المرور.");
                            FancyToast.makeText(getBaseContext(), "فشل في تسجيل الدخول.", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();//
                        }
                    }
                });
    }//للدخول

    public void add_info_center_to_realm(String center_id) {


        realm.beginTransaction();

        realm.copyToRealm(getInRealTimeUsers(center_id));
        realm.commitTransaction();


    }//اضافة بيانات

    public CenterUser getInRealTimeUsers(String centerId) {
        FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
        final DatabaseReference reference = rootNode.getReference("CenterUsers").child(centerId).child("Center information");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                CenterUser value = dataSnapshot.getValue(CenterUser.class);
                user = value;

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });
        return user;
    }//جلب البيانات

}
