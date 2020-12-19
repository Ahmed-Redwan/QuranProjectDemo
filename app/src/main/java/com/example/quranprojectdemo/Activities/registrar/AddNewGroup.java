package com.example.quranprojectdemo.Activities.registrar;

import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quranprojectdemo.Activities.logIn.QuranCenter_Login;
import com.example.quranprojectdemo.fireBase.SetGroupData;
import com.example.quranprojectdemo.models.groups.Group_Info;
import com.example.quranprojectdemo.realm.RealmDataBaseItems;
import com.example.quranprojectdemo.R;
import com.google.firebase.auth.FirebaseAuth;


import static com.example.quranprojectdemo.Activities.logIn.QuranCenter_Login.INFO_CENTER_LOGIN;

public class AddNewGroup extends AppCompatActivity {
    TextView tv_AddNewGroup;
    EditText et_GroupName, et_TeacherName, et_TeacherEmail, et_TeacherPassword, et_TeacherPhone;
    Button btn_add, btn_Cancel;
    FirebaseAuth mAuth;
    private String id_center;
    SharedPreferences sp;

    RealmDataBaseItems dataBaseItems;
    SetGroupData setGroupData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_group);
        dataBaseItems = RealmDataBaseItems.getInstance(getBaseContext());
        setGroupData = SetGroupData.getinstance(this);
        mAuth = FirebaseAuth.getInstance();
        sp = getSharedPreferences(INFO_CENTER_LOGIN, MODE_PRIVATE);

        if (sp.getString(QuranCenter_Login.ID_CENTER_LOGIN, "a").equals("a")) {
            sp = getSharedPreferences(QuranCenter_Reg.INFO_CENTER_REG, MODE_PRIVATE);
            id_center = sp.getString(QuranCenter_Reg.ID_CENTER_REG, "a");

        } else {
            id_center = sp.getString(QuranCenter_Login.ID_CENTER_LOGIN, "a");
        }

        def();
        viewFont();


        btn_Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (et_GroupName.getText().toString().isEmpty() || et_TeacherName.getText().toString().isEmpty() || et_TeacherEmail.getText().toString().isEmpty()
                        || et_TeacherPassword.getText().toString().isEmpty() || et_TeacherPhone.getText().toString().isEmpty()) {
                    et_TeacherPhone.setError("يجب تعبئة جميع الحقول.");
                    et_TeacherPassword.setError("يجب تعبئة جميع الحقول.");
                    et_TeacherEmail.setError("يجب تعبئة جميع الحقول.");
                    et_TeacherName.setError("يجب تعبئة جميع الحقول.");
                    et_GroupName.setError("يجب تعبئة جميع الحقول.");
                    return;
                }
                setGroupData.sign_up(et_TeacherEmail.getText().toString(), et_TeacherPassword.getText().toString(), et_GroupName.getText().toString(), et_TeacherName.getText().toString()
                        , et_TeacherPhone.getText().toString(), id_center);

            }


        });
    }

    private void viewFont() {


        tv_AddNewGroup.setTypeface(Typeface.createFromAsset(getAssets(), "Hacen_Tunisia_Bold.ttf"));
        et_GroupName.setTypeface(Typeface.createFromAsset(getAssets(), "Hacen_Tunisia.ttf"));
        et_TeacherName.setTypeface(Typeface.createFromAsset(getAssets(), "Hacen_Tunisia.ttf"));
        btn_add.setTypeface(Typeface.createFromAsset(getAssets(), "Hacen_Tunisia.ttf"));
        btn_Cancel.setTypeface(Typeface.createFromAsset(getAssets(), "Hacen_Tunisia_Bold.ttf"));
    }

    private void def() {
        tv_AddNewGroup = findViewById(R.id.AddNewGroup_tv_Add);
        et_GroupName = findViewById(R.id.AddNewGroup_et_GroupName);
        et_TeacherName = findViewById(R.id.AddNewGroup_et_TeacherName);
        btn_add = findViewById(R.id.AddNewGroup_btn_Add);
        btn_Cancel = findViewById(R.id.AddNewGroup_btn_cancel);
        et_TeacherPassword = findViewById(R.id.AddNewGroup_et_TeacherPassword);
        et_TeacherPhone = findViewById(R.id.AddNewGroup_et_TeacherNumberPhone);
        et_TeacherEmail = findViewById(R.id.AddNewGroup_et_Teacher_Email);

    }


}

