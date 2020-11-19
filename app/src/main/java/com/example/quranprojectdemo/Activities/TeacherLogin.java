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

import com.example.quranprojectdemo.Activities.realm.RealmDataBaseItems;
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

import java.util.List;


public class TeacherLogin extends AppCompatActivity {
    public static final String INFO_TEACHER = "info_tet";
    public static final String ID_LOGIN_TEACHER = "tet_id";
    public static final String ID_LOGIN_TEC_CENTER = "tet_log_center_id";
    TextView tv_Login;
    EditText et_Email, et_password;
    Button btn_Login;
    public FirebaseAuth mAuth;
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    CheckInternet checkInternet;

    private CheckBox saveLoginCheckBox;
    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;
    private Boolean saveLogin;
    private RealmDataBaseItems dataBaseItems;

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

        List<Student_data_cash> data_cashes = dataBaseItems.getAllDataFromRealm(Student_data_cash.class);
        if (data_cashes != null) {
            if (!data_cashes.isEmpty()) {
                FirebaseDatabase rootNode = FirebaseDatabase.getInstance();

                DatabaseReference reference = rootNode.getReference("CenterUsers");//already found
                DatabaseReference my_center = reference.child(id_center);//already found
                DatabaseReference my_center_groups = my_center.child("groups");//already found or not
                DatabaseReference my_group = my_center_groups.child(id_groubsp);// add new group

                DatabaseReference my_student_group = my_group.child("student_group");
                for (int i = 0; i < data_cashes.size(); i++) {
                    DatabaseReference student = my_student_group.child(data_cashes.get(i).getId_student());


                    DatabaseReference student_save = student.child("student_save").child(String.valueOf(data_cashes.get(i).getTime_save()));
                    Student_data s = new Student_data(data_cashes.get(i).getDate__student(), data_cashes.get(i).getDay_student()
                            , data_cashes.get(i).getSave_student(), data_cashes.get(i).getReview_student()
                            , data_cashes.get(i).getAttendess_student(), data_cashes.get(i).getCounnt_page_save(), data_cashes.get(i).getCounnt_page_review()
                            , String.valueOf(data_cashes.get(i).getMonth_save()), String.valueOf(data_cashes.get(i).getYear_save()), data_cashes.get(i).getTime_save(), data_cashes.get(i).getId_student(), data_cashes.get(i).getDate__student(), data_cashes.get(i).getId_group());

                    student_save.setValue(s);


                }
            }
        }
//            startActivity(new Intent(getBaseContext(), Main_teacher.class));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_login);
        mAuth = FirebaseAuth.getInstance();
        sp = getSharedPreferences(INFO_TEACHER, MODE_PRIVATE);
        editor = sp.edit();
        dataBaseItems = RealmDataBaseItems.getinstance(getBaseContext());


        final List<Group_Info> group_infos = dataBaseItems.getAllDataFromRealm(Group_Info.class);
        if (group_infos != null) {
            if (!group_infos.isEmpty()) {
                if (checkInternet()) {


                    upload_save_to_firaBase();
                    getGroups_Student_Saves();


                } else {
                    startActivity(new Intent(getBaseContext(), Main_teacher.class));
                    finish();


                }
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

                    dataBaseItems.insertObjectToDataToRealm(g, Group_Info.class);

                }
                final List<Student_Info> studentInfos = dataBaseItems.getAllDataFromRealm(Student_Info.class);
                if (studentInfos != null) {
                    DataSnapshot snapshot_std = snapshot.child("student_group");

                    if (snapshot_std.getChildrenCount() > studentInfos.size()) {
                        for (DataSnapshot snapshot1 : snapshot_std.getChildren()) {
                            Student_Info s = snapshot1.child("student_info").getValue(Student_Info.class);

                            if (s != null) {

                                dataBaseItems.insertObjectToDataToRealm(s, Student_Info.class);

                            }

                            DataSnapshot dataSnapshot1 = snapshot1.child("student_save");

                            for (DataSnapshot snapshot2 : dataSnapshot1.getChildren()) {
                                Student_data student_data = snapshot2.getValue(Student_data.class);

                                if (student_data != null) {

                                    dataBaseItems.insertObjectToDataToRealm(student_data, Student_data.class);

                                }
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
