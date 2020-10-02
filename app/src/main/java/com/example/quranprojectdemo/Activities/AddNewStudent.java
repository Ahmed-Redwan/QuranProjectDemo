package com.example.quranprojectdemo.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quranprojectdemo.Other.CenterUser;
import com.example.quranprojectdemo.Other.Group;
import com.example.quranprojectdemo.Other.Group_Info;
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

import com.example.quranprojectdemo.Other.Student_Info;
import com.example.quranprojectdemo.R;
import com.google.firebase.database.ValueEventListener;
import com.shashank.sony.fancytoastlib.FancyToast;


public class AddNewStudent extends AppCompatActivity {
    TextView tv_Add;
    Button btn_Add, btn_Cancel;
    EditText et_studentName, et_studentId, et_Phone, et_Email, et_Grade, et_Year, et_Month, et_Day;
    private String id_group;
    private String id_center;
    SharedPreferences sp;
    Spinner spinner;
    private FirebaseAuth mAuth;
    private String auto_student_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_student);
        mAuth = FirebaseAuth.getInstance();
        sp = getSharedPreferences(TeacherLogin.INFO_TEACHER, MODE_PRIVATE);


        id_group = sp.getString(TeacherLogin.ID_LOGIN_TEACHER, "a");
        id_center = sp.getString(TeacherLogin.ID_LOGIN_TEC_CENTER, "a");


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

        TextView_EditFont(tv_Add, "Hacen_Tunisia_Bold.ttf");
        EditText_EditFont(et_studentName, "Hacen_Tunisia.ttf");
        EditText_EditFont(et_studentId, "Hacen_Tunisia.ttf");
        EditText_EditFont(et_Email, "Hacen_Tunisia.ttf");
        EditText_EditFont(et_Grade, "Hacen_Tunisia.ttf");
        EditText_EditFont(et_Phone, "Hacen_Tunisia.ttf");
        EditText_EditFont(et_Month, "Hacen_Tunisia.ttf");
        EditText_EditFont(et_Year, "Hacen_Tunisia.ttf");
        EditText_EditFont(et_Day, "Hacen_Tunisia.ttf");

        btn_Add.setTypeface(Typeface.createFromAsset(getAssets(), "Hacen_Tunisia.ttf"));
        btn_Cancel.setTypeface(Typeface.createFromAsset(getAssets(), "Hacen_Tunisia.ttf"));

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
        btn_Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (et_studentName.getText().toString().isEmpty() || et_studentId.getText().toString().isEmpty() || et_Email.getText().toString().isEmpty() || et_Phone.getText().toString().isEmpty() || et_Grade.getText().toString().isEmpty() || et_Day.getText().toString().isEmpty() || et_Month.getText().toString().isEmpty() || et_Year.getText().toString().isEmpty()) {
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
                sign_up();
                try {
                    Thread.sleep(2500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finish();
            }
        });

    }

    public void TextView_EditFont(TextView textView, String path) {
        textView.setTypeface(Typeface.createFromAsset(getAssets(), path));
    }

    //change font type for edittext.
    public void EditText_EditFont(EditText editText, String path) {
        editText.setTypeface(Typeface.createFromAsset(getAssets(), path));
    }

    private void sign_up() {

        mAuth.createUserWithEmailAndPassword(et_Email.getText().toString(), et_Phone.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            getAutoStudentID(id_group, id_center);
                            create_new_student(auto_student_id, id_group, id_center);
                            updatename(user);

                            FirebaseAuth.getInstance().signOut();
                            FancyToast.makeText(getBaseContext(), "تم إضافة طالب جديد.", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();


                        } else {
                            Log.w("TAG", "createUserWithEmail:failure", task.getException());
                            FancyToast.makeText(getBaseContext(), "فشل في إضافة الطالب.", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();

                        }

                    }
                });


    }//للتسجيل


    private void updatename(FirebaseUser user) {// space = 32
        UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder()
                .setDisplayName(id_center).setPhotoUri(Uri.parse(id_group + " " + auto_student_id))
                .build();
        user.updateProfile(profileUpdate)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                });

    }

    public void create_new_student(String id_student, String id_groub, String id_center) {
        String birth_day = et_Day.getText().toString() + "/" + et_Month.getText().toString() + "/" + et_Year.getText().toString();
        FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
        DatabaseReference reference = rootNode.getReference("CenterUsers");//already found
        DatabaseReference center = reference.child(id_center);//already found
        DatabaseReference center_groups = center.child("groups");//already found or not
        DatabaseReference new_group = center_groups.child(id_groub);// add new group

        DatabaseReference student_group = new_group.child("student_group");
        DatabaseReference new_student = student_group.child(id_student);

        DatabaseReference student_info = new_student.child("student_info");
        student_info.setValue(new Student_Info(et_studentName.getText().toString(),
                et_studentId.getText().toString(),
                et_Phone.getText().toString(),
                et_Email.getText().toString(),
                et_Grade.getText().toString(),
                birth_day, null, id_center, id_group));


    }


    public void getAutoStudentID(String id_group, String id_center) {
        FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
        final DatabaseReference reference = rootNode.getReference("CenterUsers").child(id_center)
                .child("groups").child(id_group).child("group_info");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Group_Info val = dataSnapshot.getValue(Group_Info.class);
                auto_student_id = val.getAuto_sutdent_id();

                int id_group = Integer.parseInt(auto_student_id) + 1;
                String new_id_group = "";
                if (id_group < 10) {
                    new_id_group = "0" + id_group;

                } else {

                    new_id_group = "" + id_group;

                }
                val.setAuto_sutdent_id(new_id_group);
                reference.setValue(val);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }


        });
    }
}