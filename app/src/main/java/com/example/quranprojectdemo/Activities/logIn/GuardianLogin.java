package com.example.quranprojectdemo.Activities.logIn;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quranprojectdemo.Activities.mainActivity.Main_student;
import com.example.quranprojectdemo.fireBase.GetStudentData;
import com.example.quranprojectdemo.realm.RealmDataBaseItems;
import com.example.quranprojectdemo.models.students.Student_Info;
import com.example.quranprojectdemo.models.students.Student_data;
import com.example.quranprojectdemo.R;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.List;


public class GuardianLogin extends AppCompatActivity {
    public static final String INFO_STUDENT_LOGIN = "std_info";
    public static final String STD_ID_STUDENT = "std_id";
    public static final String STD_ID_GROUP = "std_group_id";
    public static final String STD_ID_CENTER = "std_center_id";
    private TextView tv_Login;
    private EditText et_Email, et_password;
    private Button btn_Login;
    private CheckBox saveLoginCheckBox;
    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;
    private Boolean saveLogin;
    private RealmDataBaseItems dataBaseItems;
    private GetStudentData getStudentData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guardian_login);
        dataBaseItems = RealmDataBaseItems.getInstance(getBaseContext());
        getStudentData = GetStudentData.getinstance(this);

        def();

        loginPreferences = getSharedPreferences("loginPrefsGuardian", MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();
        saveLogin = loginPreferences.getBoolean("saveLogin", false);
        if (saveLogin == true) {
            et_Email.setText(loginPreferences.getString("username", ""));
            et_password.setText(loginPreferences.getString("password", ""));
            saveLoginCheckBox.setChecked(true);
        }
        editText_Font();
        logIn();


    }

//    }

    private void logIn() {
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

                String username = et_Email.getText().toString();
                String password = et_password.getText().toString();

                if (saveLoginCheckBox.isChecked()) {
                    loginPrefsEditor.putBoolean("saveLogin", true);
                    loginPrefsEditor.putString("username", username);
                    loginPrefsEditor.putString("password", password);
                } else {
                    loginPrefsEditor.clear();
                }
                loginPrefsEditor.commit();


                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        final boolean[] islogin = {false};
                        islogin[0] = getStudentData.logInStudent(et_Email.getText().toString(), et_password.getText().toString());

                        if (!islogin[0]) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    et_Email.setError("تأكد من الإيميل و كلمة المرور.");
                                    et_password.setError("تأكد من الإيميل و كلمة المرور.");
                                    FancyToast.makeText(getBaseContext(), "فشل في تسجيل الدخول.", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();//

                                }
                            });
                        } else {

                            getAllStudentData();
                            finish();
                            startActivity(new Intent(getBaseContext(), Main_student.class));

                        }
                    }
                }).start();


            }
        });

    }

    private void def() {

        tv_Login = findViewById(R.id.GuardianLogin_tv_login);
        et_Email = findViewById(R.id.GuardianLogin_et_EmailOrphone);
        et_password = findViewById(R.id.GuardianLogin_et_Password);
        btn_Login = findViewById(R.id.GuardianLogin_btn_Login);
        saveLoginCheckBox = findViewById(R.id.GuardianLogin_Cb_remmemberme);
    }

    private void editText_Font() {
        et_Email.setTypeface(Typeface.createFromAsset(getAssets(), "Hacen_Tunisia.ttf"));
        et_password.setTypeface(Typeface.createFromAsset(getAssets(), "Hacen_Tunisia.ttf"));
        tv_Login.setTypeface(Typeface.createFromAsset(getAssets(), "Hacen_Tunisia_Bold.ttf"));
        btn_Login.setTypeface(Typeface.createFromAsset(getAssets(), "Hacen_Tunisia.ttf"));

    }

    private void getAllStudentData() {


        Student_Info student_info = getStudentData.getStudentInfo();
        addTokenId(student_info);
        dataBaseItems.insertObjectToDataToRealm(student_info, Student_Info.class);
        String nameType[] = {};
        String value[] = {};
        long[] maxMin = dataBaseItems.getMaxAndMinAndCountValue("time_save", nameType, value, Student_data.class);
        List<Student_data> studentDataList = getStudentData.getStudentSave(maxMin[1], maxMin[2]);
        if (studentDataList != null) {
            dataBaseItems.insertListDataToRealm(studentDataList);
        }


    }

    private void addTokenId(Student_Info student_info) {

        student_info.setTokenId("token");
        FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
        rootNode.getReference("CenterUsers").child(student_info.getId_center()).child("groups")
                .child(student_info.getId_group()).child("student_group")
                .child(student_info.getId_Student()).child("student_info").setValue(student_info);

    }

}
