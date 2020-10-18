package com.example.quranprojectdemo.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quranprojectdemo.Other.CheckInternet;
import com.example.quranprojectdemo.Other.Group_Info;
import com.example.quranprojectdemo.Other.Student_Info;
import com.example.quranprojectdemo.Other.Student_data;
import com.example.quranprojectdemo.Other.Student_data_cash;
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
import io.realm.RealmResults;

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
    Group_Info group_info;
    CheckInternet checkInternet;

    private CheckBox saveLoginCheckBox;
    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;
    private Boolean saveLogin;
    private Realm realm;

    private boolean checkInternet() {
        checkInternet = new CheckInternet();
        if (checkInternet.isConnected(this)) {
            return true;
        }
        return false;
    }


    private void upload_save_to_firaBase() {

        sp = getSharedPreferences(INFO_TEACHER, MODE_PRIVATE);
        String id_center = sp.getString(ID_LOGIN_TEC_CENTER, "-1");
        String id_groubsp = sp.getString(ID_LOGIN_TEACHER, "-1");

        Realm.init(getBaseContext());
        realm = Realm.getDefaultInstance();
        if (!realm.isInTransaction())
            realm.beginTransaction();
        RealmResults<Student_data_cash> realmResults = realm.where(Student_data_cash.class).findAll();/// this is


        FirebaseDatabase rootNode = FirebaseDatabase.getInstance();

        DatabaseReference reference = rootNode.getReference("CenterUsers");//already found
        DatabaseReference my_center = reference.child(id_center);//already found
        DatabaseReference my_center_groups = my_center.child("groups");//already found or not
        DatabaseReference my_group = my_center_groups.child(id_groubsp);// add new group

        DatabaseReference my_student_group = my_group.child("student_group");
        for (int i = 0; i < realmResults.size(); i++) {
            DatabaseReference student = my_student_group.child(realmResults.get(i).getId_student());


            DatabaseReference student_save = student.child("student_save").child(String.valueOf(realmResults.get(i).getTime_save()));
            Student_data s = new Student_data(realmResults.get(i).getDate__student(), realmResults.get(i).getDay_student()
                    , realmResults.get(i).getSave_student(), realmResults.get(i).getReview_student()
                    , realmResults.get(i).getAttendess_student(), realmResults.get(i).getCounnt_page_save(), realmResults.get(i).getCounnt_page_review()
                    , realmResults.get(i).getMonth_save(), realmResults.get(i).getYear_save(), realmResults.get(i).getTime_save(), realmResults.get(i).getId_student(), realmResults.get(i).getDate__student(), realmResults.get(i).getId_group());

            student_save.setValue(s);


        }
        realm.close();
        realm = Realm.getDefaultInstance();
        if (!realm.isInTransaction())
            realm.beginTransaction();
        realm.delete(Student_data_cash.class);
        realm.commitTransaction();
        realm.close();
//            startActivity(new Intent(getBaseContext(), Main_teacher.class));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_login);
        mAuth = FirebaseAuth.getInstance();
        sp = getSharedPreferences(INFO_TEACHER, MODE_PRIVATE);
        editor = sp.edit();
        Realm.init(getBaseContext());
        realm = Realm.getDefaultInstance();


        final RealmResults<Group_Info> realmResults = realm.where(Group_Info.class).findAll();
        if (!realmResults.isEmpty()) {
            if (checkInternet()) {


                upload_save_to_firaBase();
                getGroups_Student_Saves();


            } else {
                startActivity(new Intent(getBaseContext(), Main_teacher.class));
                finish();


            }
//            startActivity(new Intent(getBaseContext(), Main_teacher.class));
        }

        tv_Login = findViewById(R.id.TeacheLogin_tv_login);
        et_Email = findViewById(R.id.TeacheLogin_et_EmailOrphone);
        et_password = findViewById(R.id.TeacheLogin_et_Password);
        btn_Login = findViewById(R.id.TeacheLogin_btn_Login);

        saveLoginCheckBox = findViewById(R.id.TeacherLogin_Cb_remmemberme);
        loginPreferences = getSharedPreferences("loginPrefsTeacher", MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();

        saveLogin = loginPreferences.getBoolean("saveLogin", false);
        if (saveLogin == true) {
            et_Email.setText(loginPreferences.getString("username", ""));
            et_password.setText(loginPreferences.getString("password", ""));
            saveLoginCheckBox.setChecked(true);
            TextView_EditFont(tv_Login, "Hacen_Tunisia_Bold.ttf");

            btn_Login.setTypeface(Typeface.createFromAsset(getAssets(), "Hacen_Tunisia.ttf"));

            EditText_EditFont(et_Email, "Hacen_Tunisia.ttf");
            EditText_EditFont(et_password, "Hacen_Tunisia.ttf");

        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        btn_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //  if (et_Email.getText().toString().isEmpty())
                if (!Patterns.EMAIL_ADDRESS.matcher(et_Email.getText().toString()).matches()) {
                    et_Email.setError("يجب ادخال الايميل ادخالا صحيحاً");
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
//                            getInfoTeacher("D6jM6n7JHpQoL3UdsUGfo3ykm5x2", user.getDisplayName());
                            editor.putString(ID_LOGIN_TEACHER, user.getPhotoUrl().toString());
                            editor.putString(ID_LOGIN_TEC_CENTER, user.getDisplayName());
                            editor.commit();
//                            Log.d("****************", user.getEmail() + "******************");

                            Toast.makeText(TeacherLogin.this, user.getEmail(), Toast.LENGTH_SHORT).show();
                            getGroups_Student_Saves();
                            FancyToast.makeText(getBaseContext(), "تم تسجيل الدخول بنجاح.", FancyToast.LENGTH_LONG,
                                    FancyToast.SUCCESS, false).show();

                        } else {
                            et_Email.setError("تأكد من الإيميل و كلمة المرور.");
                            et_password.setError("تأكد من الإيميل و كلمة المرور.");
                            FancyToast.makeText(getBaseContext(), "فشل في تسجيل الدخول.", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();//
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


    private void getGroups_Student_Saves() {
        final String id_group = sp.getString(TeacherLogin.ID_LOGIN_TEACHER, "a");
        final String id_center = sp.getString(TeacherLogin.ID_LOGIN_TEC_CENTER, "a");
        FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
        final DatabaseReference reference = rootNode.getReference("CenterUsers").child(id_center)
                .child("groups").child(id_group);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Group_Info g = snapshot.child("group_info").getValue(Group_Info.class);

                if (g != null) {
                    realm = Realm.getDefaultInstance();
                    if (!realm.isInTransaction())
                        realm.beginTransaction();
                    realm.insertOrUpdate(g);
                    realm.commitTransaction();
                    realm.close();
                }
                final RealmResults<Student_Info> realmResults = realm.where(Student_Info.class).findAll();

                DataSnapshot snapshot_std = snapshot.child("student_group");

                if (snapshot_std.getChildrenCount() > realmResults.size()) {
                    for (DataSnapshot snapshot1 : snapshot_std.getChildren()) {
                        Student_Info s = snapshot1.child("student_info").getValue(Student_Info.class);

                        if (s != null) {

                            realm = Realm.getDefaultInstance();
                            if (!realm.isInTransaction())
                                realm.beginTransaction();
                            realm.insertOrUpdate(s);
                            realm.commitTransaction();
                            realm.close();
                        }

                        DataSnapshot dataSnapshot1 = snapshot1.child("student_save");

                        for (DataSnapshot snapshot2 : dataSnapshot1.getChildren()) {
                            Student_data student_data = snapshot2.getValue(Student_data.class);

                            if (student_data != null) {
                                realm = Realm.getDefaultInstance();
                                if (!realm.isInTransaction())
                                    realm.beginTransaction();
                                realm.insertOrUpdate(student_data);
                                realm.commitTransaction();
                                realm.close();
                            }
                        }


                    }
                }

                reference.removeEventListener(this);
                finish();
                startActivity(new Intent(getBaseContext(), Main_teacher.class));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }


}
