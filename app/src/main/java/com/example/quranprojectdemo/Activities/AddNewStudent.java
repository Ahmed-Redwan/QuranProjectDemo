package com.example.quranprojectdemo.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.example.quranprojectdemo.Other.Student_Info;
import com.example.quranprojectdemo.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class AddNewStudent extends AppCompatActivity {
    TextView tv_Add;
    Button btn_Add, btn_Cancel;
    EditText et_studentName, et_studentId, et_Phone, et_Email, et_Grade, et_Year, et_Month, et_Day;
    private FirebaseAuth mAuth;
    private String center_name, id_group;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_student);
        mAuth = FirebaseAuth.getInstance();

        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null)
            center_name = mAuth.getCurrentUser().getUid();
//        center_name = mAuth.getCurrentUser().getUid();
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
//        HashMap<String, String> s = new HashMap();
//        s.put("name group1", "id group1");
//        s.put("name group2", "id group2");
//        s.put("name group2", "id group3");
//        s.put("name group4", "id group4");
//        s.put("name grou5", "id group5");
//        for (int i = 0; i < s.size(); i++) {
//            String ss = s.get()
//
//        }
//        String id = s.get("name");
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
                sign_up();
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
                            create_new_student(et_studentName.getText().toString(), id_group, center_name);
                            updatename(user);


                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(AddNewStudent.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });


    }//للتسجيل

    private void updatename(FirebaseUser user) {
//        PhoneAuthCredential credential = getPhoneAuthCredential(authToken, authSecret);
        //updating user's profile data
        UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder()
                .setDisplayName(center_name).setPhotoUri(Uri.parse(id_group))
                .build();
//        PhoneAuthCredential phoneAuthCredential = new PhoneAuthCredential.;
        user.updateProfile(profileUpdate)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                });

    }

    public void create_new_student(String name_student, String id_groub, String id_center) {
        String birth_day = et_Day.getText().toString() + "/" + et_Month.getText().toString() + "/" + et_Year.getText().toString();
        FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
        DatabaseReference reference = rootNode.getReference("CenterUsers");//already found
        DatabaseReference center = reference.child(id_center);//already found
        DatabaseReference center_groups = center.child("groups");//already found or not
        DatabaseReference new_group = center_groups.child(id_groub);// add new group

        DatabaseReference student_group = new_group.child("student_group");
        DatabaseReference new_student = student_group.child(name_student);

        DatabaseReference student_info = new_student.child("student_info");
        student_info.setValue(new Student_Info(et_studentName.getText().toString(), Integer.parseInt(et_studentId.getText().toString()),
                et_Phone.getText().toString(),
                et_Email.getText().toString(), et_Grade.getText().toString(), birth_day));


    }


}