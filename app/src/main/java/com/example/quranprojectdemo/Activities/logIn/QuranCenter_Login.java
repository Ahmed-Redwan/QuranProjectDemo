package com.example.quranprojectdemo.Activities.logIn;

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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quranprojectdemo.Activities.mainActivity.Main_center;
import com.example.quranprojectdemo.Activities.registrar.QuranCenter_Reg;
import com.example.quranprojectdemo.fireBase.GetCenterData;
import com.example.quranprojectdemo.fireBase.GetStudentData;
import com.example.quranprojectdemo.realm.RealmDataBaseItems;
import com.example.quranprojectdemo.models.centers.CenterUser;
import com.example.quranprojectdemo.models.CheckInternet;
import com.example.quranprojectdemo.models.groups.Group_Info;
import com.example.quranprojectdemo.models.students.Student_Info;
import com.example.quranprojectdemo.models.students.Student_data;
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


public class QuranCenter_Login extends AppCompatActivity {
    public static final String ID_CENTER_LOGIN = "id_center_log";
    public static final String INFO_CENTER_LOGIN = "Info_center";
    TextView tv_Login, tv_iDontHaveAnAccount, tv_NewAccount;
    EditText et_Email, et_password;
    Button btn_Login;
    public FirebaseAuth mAuth;
    SharedPreferences sp;
    private GetCenterData getCenterData;
    private GetStudentData getStudentData;
    private CheckBox saveLoginCheckBox;
    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;
    private Boolean saveLogin;
    RealmDataBaseItems dataBaseItems;

    private boolean checkInternet() {
        CheckInternet checkInternet;
        checkInternet = new CheckInternet();
        if (checkInternet.isConnected(this)) {
            return true;
        }
        return false;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quran_center__login);
        mAuth = FirebaseAuth.getInstance();
        dataBaseItems = RealmDataBaseItems.getinstance(getBaseContext());
        getCenterData = GetCenterData.getinstance(getBaseContext());
        getStudentData = GetStudentData.getinstance(getBaseContext());
//        List<CenterUser> centerUserList = dataBaseItems.getAllDataFromRealm(CenterUser.class);
//        if (centerUserList != null) {
//            if (!centerUserList.isEmpty()) {
//                sp = getSharedPreferences(INFO_CENTER_LOGIN, MODE_PRIVATE);
//                getNewDataToCenter();
//            }
//        }
        def();
        viewFont();
        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();
        saveLogin = loginPreferences.getBoolean("saveLogin", false);
        if (saveLogin == true) {
            et_Email.setText(loginPreferences.getString("username", ""));
            et_password.setText(loginPreferences.getString("password", ""));
            saveLoginCheckBox.setChecked(true);
        }


        tv_NewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(), QuranCenter_Reg.class));
                finish();
            }
        });


    }

    private void viewFont() {
        tv_Login.setTypeface(Typeface.createFromAsset(getAssets(), "Hacen_Tunisia_Bold.ttf"));
        tv_NewAccount.setTypeface(Typeface.createFromAsset(getAssets(), "Hacen_Tunisia.ttf"));
        tv_iDontHaveAnAccount.setTypeface(Typeface.createFromAsset(getAssets(), "Hacen_Tunisia.ttf"));
        btn_Login.setTypeface(Typeface.createFromAsset(getAssets(), "Hacen_Tunisia.ttf"));
        et_Email.setTypeface(Typeface.createFromAsset(getAssets(), "Hacen_Tunisia.ttf"));
        et_password.setTypeface(Typeface.createFromAsset(getAssets(), "Hacen_Tunisia.ttf"));

    }

    private void def() {
        tv_Login = findViewById(R.id.QuranCenterLogin_tv_login);
        tv_NewAccount = findViewById(R.id.QuranCenterLogin_tv_NewAccount);
        tv_iDontHaveAnAccount = findViewById(R.id.QuranCenterLogin_tv_iDontHaveAnAccount);
        et_Email = findViewById(R.id.QuranCenterLogin_et_EmailOrphone);
        et_password = findViewById(R.id.QuranCenterLogin_et_Password);
        btn_Login = findViewById(R.id.QuranCenterLogin_btn_Login);
        saveLoginCheckBox = findViewById(R.id.QuranCenterLogin_Cb_remmemberme);

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
        new Thread(new Runnable() {
            @Override
            public void run() {

                String idCenter = getCenterData.logIn(et_Email.getText().toString(), et_password.getText().toString());
                if (idCenter != null) {
                    Log.d("wwww", idCenter + "null");

                    CenterUser centerUser = getCenterData.getCenterInfo(idCenter);
                    if (centerUser != null)
                        dataBaseItems.insertObjectToDataToRealm(centerUser, CenterUser.class);
                    List<Group_Info> groupInfos = getStudentData.getAllGroupInfoToCenter();
                    if (groupInfos != null)
                        dataBaseItems.insertListDataToRealm(groupInfos);
                    List<Student_Info> studentInfos = getStudentData.getAllStudentInfoToCenter();
                    if (studentInfos != null)
                        dataBaseItems.insertListDataToRealm(studentInfos);
                    List<Student_data> studentData = getStudentData.getAllStudentSavesToCenter();
                    if (studentData != null)
                        dataBaseItems.insertListDataToRealm(studentData);
                    finish();
                    startActivity(new Intent(getBaseContext(), Main_center.class));
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            et_Email.setError("تأكد من الإيميل و كلمة المرور.");
                            et_password.setError("تأكد من الإيميل و كلمة المرور.");
                            FancyToast.makeText(getBaseContext(), "فشل في تسجيل الدخول.", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();//

                        }
                    });


                }

            }
        }).start();

    }


    private void getNewDataToCenter() {
        if (checkInternet()) {
            new Thread(new Runnable() {
                @Override
                public void run() {

                    List<Student_Info> studentInfos = getStudentData.getNewStudentInfoToCenter();
                    if (studentInfos != null && !studentInfos.isEmpty())
                        dataBaseItems.insertListDataToRealm(studentInfos);


                    List<Student_data> studentData = getStudentData.getNewStudentsSaveToCenter();
                    if (studentData != null && !studentData.isEmpty())

                        dataBaseItems.insertListDataToRealm(studentData);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            finish();
                            startActivity(new Intent(getBaseContext(), Main_center.class));
                        }
                    });
                }
            }).start();
        }
    }
}
