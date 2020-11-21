package com.example.quranprojectdemo.Activities.registrar;

import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quranprojectdemo.Activities.logIn.TeacherLogin;
import com.example.quranprojectdemo.fireBase.SetNewStudent;
import com.example.quranprojectdemo.realm.RealmDataBaseItems;
import com.example.quranprojectdemo.models.groups.Group_Info;
import com.example.quranprojectdemo.models.students.Student_Info;
import com.example.quranprojectdemo.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shashank.sony.fancytoastlib.FancyToast;


public class AddNewStudent extends AppCompatActivity {
    TextView tv_Add;
    Button btn_Add, btn_Cancel;
    EditText et_studentName, et_studentId, et_Phone, et_Email, et_Grade, et_Year, et_Month, et_Day;
        Spinner spinner;
    RealmDataBaseItems dataBaseItems;
    SetNewStudent setNewStudent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_student);
        dataBaseItems = RealmDataBaseItems.getinstance(getBaseContext());
        setNewStudent = SetNewStudent.getInstance(getBaseContext());
        def();
        viewFont();


        btn_Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btn_Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = et_studentName.getText().toString();
                String id_number = et_studentId.getText().toString();
                String phoneNo = et_Phone.getText().toString();
                String email = et_Email.getText().toString();
                String academic_level = et_Grade.getText().toString();
                String birth_date = et_Day.getText().toString() + "//" + et_Month.getText().toString() + "//" + et_Year.getText().toString();

                Student_Info studentInfo = new Student_Info(name, id_number, phoneNo, email, academic_level, birth_date, null, null, null, null);
                if (name.isEmpty() || id_number.isEmpty() || email.isEmpty() || phoneNo.isEmpty() || academic_level.isEmpty() || birth_date.isEmpty()) {
                    et_Month.setError("يجب تعبئة جميع الحقول.");
                    et_Year.setError("يجب تعبئة جميع الحقول.");
                    et_Day.setError("يجب تعبئة جميع الحقول.");
                    et_Phone.setError("يجب تعبئة جميع الحقول.");
                    et_Email.setError("يجب تعبئة جميع الحقول.");
                    et_studentId.setError("يجب تعبئة جميع الحقول.");
                    et_studentName.setError("يجب تعبئة جميع الحقول.");
                    et_Grade.setError("يجب تعبئة جميع الحقول.");
                    return;
                }
                if (!et_studentId.getText().toString().isEmpty()) {
                    if (dataBaseItems.idNumberFound(et_studentId.getText().toString())) {
                        et_studentId.setText(null);
                        et_studentId.setError("رقم الهوية مستخدم ");
                        FancyToast.makeText(getBaseContext(), "رقم الهوية مستخدم ادخل رقم اخر", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                        return;
                    }

                }
                setNewStudent.sign_up(email, phoneNo, studentInfo);

            }
        });
    }

    private void def() {
        spinner = findViewById(R.id.addNewStudent_sp);
        tv_Add = findViewById(R.id.AddNewStudent_tv_AddStudent);
        et_studentName = findViewById(R.id.AddNewStudent_et_StudentName);
        et_studentId = findViewById(R.id.AddNewStudent_et_StudentId);
        et_Email = findViewById(R.id.AddNewStudent_et_Email);
        et_Phone = findViewById(R.id.AddNewStudent_et_PhoneNum);
        et_Grade = findViewById(R.id.AddNewStudent_et_Grade);
        et_Year = findViewById(R.id.AddNewStudent_et_year);
        et_Month = findViewById(R.id.AddNewStudent_et_month);
        et_Day = findViewById(R.id.AddNewStudent_et_day);
        btn_Add = findViewById(R.id.AddNewStudent_btn_AddNewStudent);
        btn_Cancel = findViewById(R.id.AddNewStudent_btn_cancel);

    }

    public void viewFont() {
        et_studentName.setTypeface(Typeface.createFromAsset(getAssets(), "Hacen_Tunisia.ttf"));
        et_studentId.setTypeface(Typeface.createFromAsset(getAssets(), "Hacen_Tunisia.ttf"));
        et_Email.setTypeface(Typeface.createFromAsset(getAssets(), "Hacen_Tunisia.ttf"));
        et_Grade.setTypeface(Typeface.createFromAsset(getAssets(), "Hacen_Tunisia.ttf"));
        et_Phone.setTypeface(Typeface.createFromAsset(getAssets(), "Hacen_Tunisia.ttf"));
        et_Month.setTypeface(Typeface.createFromAsset(getAssets(), "Hacen_Tunisia.ttf"));
        et_Year.setTypeface(Typeface.createFromAsset(getAssets(), "Hacen_Tunisia.ttf"));
        et_Day.setTypeface(Typeface.createFromAsset(getAssets(), "Hacen_Tunisia.ttf"));
        tv_Add.setTypeface(Typeface.createFromAsset(getAssets(), "Hacen_Tunisia_Bold.ttf"));

        btn_Add.setTypeface(Typeface.createFromAsset(getAssets(), "Hacen_Tunisia.ttf"));
        btn_Cancel.setTypeface(Typeface.createFromAsset(getAssets(), "Hacen_Tunisia.ttf"));
    }


}