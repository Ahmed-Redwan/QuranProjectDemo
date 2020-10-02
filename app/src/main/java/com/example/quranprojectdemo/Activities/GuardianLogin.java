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
import com.example.quranprojectdemo.Other.CheckInternet;
import com.example.quranprojectdemo.Other.Student_Info;
import com.example.quranprojectdemo.Other.Student_data;
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

import java.security.spec.ECField;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class GuardianLogin extends AppCompatActivity {
    private static final String INFO_STUDENT_LOGIN = "std_info";
    private static final String STD_ID_STUDENT = "std_id";
    private static final String STD_ID_GROUP = "std_group_id";
    private static final String STD_ID_CENTER = "std_center_id";
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
    Student_Info user;
    Realm realm;
    CheckInternet checkInternet;
    long count = -1;

    private boolean checkInternet() {
        checkInternet = new CheckInternet();
        if (checkInternet.isConnected(this)) {
            return true;
        }
        return false;
    }

    private void save_student_to_realm() {

        realm.beginTransaction();
        try {
            realm.copyToRealmOrUpdate(user);
        } catch (Exception e) {


        }
        realm.commitTransaction();

    }

    public void getSavesStudent(String id_center, String id_group, String id_student, final long time) {

        FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
        DatabaseReference reference = rootNode.getReference("CenterUsers").child(id_center)
                .child("groups").child(id_group).child("student_group").child(id_student).child("student_save");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot c : dataSnapshot.getChildren()) {
                    if (!c.getKey().equals("student_info")) {
                        Student_data d = c.getValue(Student_data.class);

                        if (d.getTime_save() > time) {

                            realm.beginTransaction();
                            try {
                                realm.copyToRealmOrUpdate(d);
                            } catch (Exception e) {


                            }
                            realm.commitTransaction();
                        }
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

    }//جلب البيانات

    private void get_student_info() {
        sp = getSharedPreferences(INFO_STUDENT_LOGIN, MODE_PRIVATE);

        if (checkInternet()) {

            FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
            DatabaseReference reference = rootNode.getReference("CenterUsers").child(sp.getString(STD_ID_CENTER, "0"))
                    .child("groups").child(sp.getString(STD_ID_GROUP, "-1")).child("student_group").child(
                            sp.getString(STD_ID_STUDENT, "-1")).child("student_save");

            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    try {
                        count = dataSnapshot.getChildrenCount();

                    } catch (Exception e) {
                    }


                }

                @Override
                public void onCancelled(DatabaseError error) {
                }
            });
            Number query = realm.where(Student_data.class).max("time");
            if (query.longValue() < count) {
                getSavesStudent(sp.getString(STD_ID_CENTER, "0"),
                        sp.getString(STD_ID_GROUP, "-1"), sp.getString(STD_ID_STUDENT, "-1"), query.longValue());

            }
            realm.beginTransaction();

            realm.copyToRealmOrUpdate(user);

            realm.commitTransaction();

        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guardian_login);
        mAuth = FirebaseAuth.getInstance();
        tv_Login = findViewById(R.id.GuardianLogin_tv_login);
        et_Email = findViewById(R.id.GuardianLogin_et_EmailOrphone);
        get_student_info();
        et_password = findViewById(R.id.GuardianLogin_et_Password);
        btn_Login = findViewById(R.id.GuardianLogin_btn_Login);
        Realm.init(getBaseContext());
        realm = Realm.getDefaultInstance();
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

        btn_Login.setTypeface(Typeface.createFromAsset(getAssets(), "Hacen_Tunisia.ttf"));

        EditText_EditFont(et_Email, "Hacen_Tunisia.ttf");
        EditText_EditFont(et_password, "Hacen_Tunisia.ttf");

        btn_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (et_Email.getText().toString().isEmpty()) {
                    et_Email.setError("يجب ادخال الايميل أو رقم الهاتف.");
                    return;
                } else if (et_password.getText().toString().isEmpty()) {
                    et_password.setError("يجب ادخال كلمة المرور.");
                    return;
                } else if (et_password.getText().toString().length() < 7) {
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
                            String ids = user.getPhotoUrl().toString();
                            String id_group = ids.substring(0, 2);
                            String id_sutdent = ids.substring(3, ids.length());
                            sp = getSharedPreferences(INFO_STUDENT_LOGIN, MODE_PRIVATE);
                            editor = sp.edit();
                            editor.putString(STD_ID_STUDENT, id_sutdent);
                            editor.putString(STD_ID_GROUP, id_group);
                            editor.putString(STD_ID_CENTER, user.getDisplayName());
                            editor.commit();
                            FancyToast.makeText(getBaseContext(), "تم تسجيل الدخول بنجاح.", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
                            getStudnetInfo(user.getDisplayName(), id_group, id_sutdent);
                            save_student_to_realm();
                            startActivity(new Intent(getBaseContext(), Main_student.class));
                        } else {
                            et_Email.setError("تأكد من الإيميل و كلمة المرور.");
                            et_password.setError("تأكد من الإيميل و كلمة المرور.");
                            FancyToast.makeText(getBaseContext(), "فشل في تسجيل الدخول.", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();//
                            b = false;
                        }
                    }
                });
    }//للدخول

    public Student_Info getStudnetInfo(String id_center, String id_group, String id_student) {

        FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
        DatabaseReference reference = rootNode.getReference("CenterUsers").child(id_center)
                .child("groups").child(id_group).child("student_group").child(
                        id_student).child("student_info");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Student_Info studentInfo = dataSnapshot.getValue(Student_Info.class);
                user = studentInfo;
            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });
        return user;
    }//جلب البيانات


}
