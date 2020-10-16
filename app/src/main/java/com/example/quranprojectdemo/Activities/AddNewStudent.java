package com.example.quranprojectdemo.Activities;

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

import com.example.quranprojectdemo.Other.Group_Info;
import com.example.quranprojectdemo.Other.Student_Info;
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

import io.realm.Realm;


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

    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_student);
        mAuth = FirebaseAuth.getInstance();
        sp = getSharedPreferences(TeacherLogin.INFO_TEACHER, MODE_PRIVATE);

        Realm.init(getBaseContext());

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
        {
            TextView_EditFont(tv_Add, "Hacen_Tunisia_Bold.ttf");
            EditText_EditFont(et_studentName, "Hacen_Tunisia.ttf");
            EditText_EditFont(et_studentId, "Hacen_Tunisia.ttf");
            EditText_EditFont(et_Email, "Hacen_Tunisia.ttf");
            EditText_EditFont(et_Grade, "Hacen_Tunisia.ttf");
            EditText_EditFont(et_Phone, "Hacen_Tunisia.ttf");
            EditText_EditFont(et_Month, "Hacen_Tunisia.ttf");
            EditText_EditFont(et_Year, "Hacen_Tunisia.ttf");
            EditText_EditFont(et_Day, "Hacen_Tunisia.ttf");
        }
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
                            getAutoStudentID(id_group, id_center, user);

//                            FirebaseAuth.getInstance().signOut();
                            FancyToast.makeText(getBaseContext(), "تم إضافة طالب جديد.", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();


                        } else {
                            Log.w("TAG", "createUserWithEmail:failure", task.getException());
                            FancyToast.makeText(getBaseContext(), "فشل في إضافة الطالب.", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();

                        }

                    }
                });


    }//للتسجيل


    private void updatename(FirebaseUser user, String id_center1, String id_group1, String auto_student_id1) {// space = 32
        UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder()
                .setDisplayName(id_center1).setPhotoUri(Uri.parse(id_group1 + " " + auto_student_id1))
                .build();
        user.updateProfile(profileUpdate)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                });

    }

    public void create_new_student(Student_Info info, String id_student, String id_groub1, String id_center) {
        String birth_day = et_Day.getText().toString() + "/" + et_Month.getText().toString() + "/" + et_Year.getText().toString();
        FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
        DatabaseReference reference = rootNode.getReference("CenterUsers");//already found
        DatabaseReference center = reference.child(id_center);//already found
        DatabaseReference center_groups = center.child("groups");//already found or not
        DatabaseReference new_group = center_groups.child(id_groub1);// add new group

        DatabaseReference student_group = new_group.child("student_group");
        DatabaseReference new_student = student_group.child(id_student);

        DatabaseReference student_info = new_student.child("student_info");
        student_info.setValue(info);
        if (info != null) {
            realm = Realm.getDefaultInstance();
            if (!realm.isInTransaction())
                realm.beginTransaction();
            realm.copyToRealm(info);
            realm.commitTransaction();
            if (!realm.isClosed())
                realm.close();
        } else Toast.makeText(getBaseContext(), "لم تتم الاضافة بنجاح", Toast.LENGTH_SHORT).show();

    }


    public void getAutoStudentID(final String id_group, final String id_center, final FirebaseUser user) {
        FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
        final DatabaseReference reference = rootNode.getReference("CenterUsers").child(id_center)
                .child("groups").child(id_group).child("group_info");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Group_Info val = dataSnapshot.getValue(Group_Info.class);
                auto_student_id = val.getAuto_sutdent_id();

                int id_student = Integer.parseInt(auto_student_id) + 1;
                String new_id_student = "";
                if (id_student < 10) {
                    new_id_student = "0" + id_student;

                } else {

                    new_id_student = "" + id_student;

                }
                val.setAuto_sutdent_id(new_id_student);
                Student_Info s = new Student_Info(et_studentName.getText().toString(),
                        et_studentId.getText().toString(),
                        et_Phone.getText().toString(),
                        et_Email.getText().toString(),
                        et_Grade.getText().toString(),
                        et_Day.getText().toString(), null, id_center, id_group, new_id_student);
                create_new_student(s, auto_student_id, id_group, id_center);
                updatename(user, id_center, id_group, new_id_student);
                save_new_id_group(val, id_center, id_group);
                reference.removeEventListener(this);

                finish();

            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }


        });
    }

    private void save_new_id_group(Group_Info group_info, String center_id, String group_id) {
        FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
        final DatabaseReference reference = rootNode.getReference("CenterUsers").child(center_id).
                child("groups").child(group_id).child("group_info");
        reference.setValue(group_info);

    }
}