package com.example.quranprojectdemo.Activities.logIn;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.print.PrintJob;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quranprojectdemo.Activities.mainActivity.Main_teacher;
import com.example.quranprojectdemo.fireBase.GetGroupData;
import com.example.quranprojectdemo.fireBase.GetStudentData;
import com.example.quranprojectdemo.fireBase.SetStudentData;
import com.example.quranprojectdemo.realm.RealmDataBaseItems;
import com.example.quranprojectdemo.models.CheckInternet;
import com.example.quranprojectdemo.models.groups.Group_Info;
import com.example.quranprojectdemo.models.students.Student_Info;
import com.example.quranprojectdemo.models.students.Student_data;
import com.example.quranprojectdemo.models.students.Student_data_cash;
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

    CheckInternet checkInternet;
    private GetStudentData getStudentData;
    private GetGroupData getGroupData;
    private SetStudentData setStudentData;
    private CheckBox saveLoginCheckBox;
    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;
    private Boolean saveLogin;
    private RealmDataBaseItems dataBaseItems;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_login);

        dataBaseItems = RealmDataBaseItems.getinstance(getBaseContext());
        getGroupData = GetGroupData.getinstance(getBaseContext());
        getStudentData = GetStudentData.getinstance(getBaseContext());
        def();
//        final List<Group_Info> group_infos = dataBaseItems.getAllDataFromRealm(Group_Info.class);
//        if (group_infos != null) {
//            if (!group_infos.isEmpty()) {
//                if (checkInternet()) {
//
//                    List<Student_data_cash> student_data_cashes = dataBaseItems.getAllDataFromRealm(Student_data_cash.class);
//                    if (student_data_cashes != null && !student_data_cashes.isEmpty()) {
//                        boolean uploaded = setStudentData.uploadNewSave(student_data_cashes);
//                        if (uploaded) {
//                            dataBaseItems.deleteCashData();
//
//                        }
//                    }
//
//                    getNewData();
//
//                } else {
//                    startActivity(new Intent(getBaseContext(), Main_teacher.class));
//                    finish();
//
//
//                }
//            }
//        }

        loginPreferences = getSharedPreferences("loginPrefsTeacher", MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();

        saveLogin = loginPreferences.getBoolean("saveLogin", false);
        if (saveLogin == true) {
            et_Email.setText(loginPreferences.getString("username", ""));
            et_password.setText(loginPreferences.getString("password", ""));
            saveLoginCheckBox.setChecked(true);
            textViewEditFont();

            btn_Login.setTypeface(Typeface.createFromAsset(getAssets(), "Hacen_Tunisia.ttf"));

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
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean isLoged = getGroupData.log_in(et_Email.getText().toString(), et_password.getText().toString());
                if (!isLoged) {
                    et_Email.setError("تأكد من الإيميل و كلمة المرور.");
                    et_password.setError("تأكد من الإيميل و كلمة المرور.");
                    FancyToast.makeText(getBaseContext(), "فشل في تسجيل الدخول.", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();//
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            getAllDataToLogIn();

                        }
                    });

                }
            }
        }).start();
    }


    public void textViewEditFont() {


        tv_Login.setTypeface(Typeface.createFromAsset(getAssets(), "Hacen_Tunisia_Bold.ttf"));
        et_Email.setTypeface(Typeface.createFromAsset(getAssets(), "Hacen_Tunisia.ttf"));
        et_password.setTypeface(Typeface.createFromAsset(getAssets(), "Hacen_Tunisia.ttf"));
    }


    private void getAllDataToLogIn() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Group_Info group_info = getGroupData.getGroupInfo();
                dataBaseItems.copyObjectToDataToRealm(group_info, Group_Info.class);

                List<Student_Info> infoList = getStudentData.getAllStudentInfoToGroup();
                dataBaseItems.insertListDataToRealm(infoList);


                List<Student_data> dataList = getStudentData.getAllStudentSaveToGroup();
                dataBaseItems.insertListDataToRealm(dataList);
                finish();
                startActivity(new Intent(getBaseContext(), Main_teacher.class));

            }
        }).start();


    }

    private void getNewData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Group_Info group_info = getGroupData.getGroupInfo();
                dataBaseItems.insertObjectToDataToRealm(group_info, Group_Info.class);
                String typeName[] = {};
                String value[] = {};

                long maxMin[] = dataBaseItems.getMaxAndMinAndCountValue("id_Student", typeName, value, Student_Info.class);
                int max = (int) maxMin[1];
                int count = (int) maxMin[2];

                List<Student_Info> infoList = getStudentData.getNewStudentInfoToGroup(count, max);
                dataBaseItems.insertListDataToRealm(infoList);

                maxMin = dataBaseItems.getMaxAndMinAndCountValue("time_save", typeName, value, Student_data.class);
                max = (int) maxMin[1];
                count = (int) maxMin[2];

                List<Student_data> dataList = getStudentData.getNewStudentsSaveToGroup(max, count);
                dataBaseItems.insertListDataToRealm(dataList);
                finish();
                startActivity(new Intent(getBaseContext(), Main_teacher.class));

            }
        }).start();


    }

    private void def() {

        saveLoginCheckBox = findViewById(R.id.TeacherLogin_Cb_remmemberme);

        tv_Login = findViewById(R.id.TeacheLogin_tv_login);
        et_Email = findViewById(R.id.TeacheLogin_et_EmailOrphone);
        et_password = findViewById(R.id.TeacheLogin_et_Password);
        btn_Login = findViewById(R.id.TeacheLogin_btn_Login);

    }
}
