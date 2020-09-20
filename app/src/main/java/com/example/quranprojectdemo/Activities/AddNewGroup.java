package com.example.quranprojectdemo.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quranprojectdemo.Other.Group_Info;
import com.example.quranprojectdemo.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddNewGroup extends AppCompatActivity {
    TextView tv_AddNewGroup;
    EditText et_GroupName, et_TeacherName, et_TeacherEmail, et_TeacherPassword, et_TeacherPhone;
    Button btn_add, btn_Cancel;
    FirebaseAuth mAuth;
    private String id_center;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_group);

        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null)
            id_center = mAuth.getCurrentUser().getUid();
        Toast.makeText(getBaseContext(), id_center, Toast.LENGTH_SHORT).show();
        tv_AddNewGroup = findViewById(R.id.AddNewGroup_tv_Add);
        et_GroupName = findViewById(R.id.AddNewGroup_et_GroupName);
        et_TeacherName = findViewById(R.id.AddNewGroup_et_TeacherName);
        btn_add = findViewById(R.id.AddNewGroup_btn_Add);
        btn_Cancel = findViewById(R.id.AddNewGroup_btn_cancel);
        et_TeacherPassword = findViewById(R.id.AddNewGroup_et_TeacherPassword);
        et_TeacherPhone = findViewById(R.id.AddNewGroup_et_TeacherNumberPhone);
        et_TeacherEmail = findViewById(R.id.AddNewGroup_et_Teacher_Email);
        TextView_EditFont(tv_AddNewGroup, "Hacen_Tunisia_Bold.ttf");

        EditText_EditFont(et_GroupName, "Hacen_Tunisia.ttf");
        EditText_EditFont(et_TeacherName, "Hacen_Tunisia.ttf");

        Button_EditFont(btn_add, "Hacen_Tunisia.ttf");
        Button_EditFont(btn_Cancel, "Hacen_Tunisia.ttf");


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


                sign_up(et_TeacherEmail.getText().toString(), et_TeacherPassword.getText().toString());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finish();

            }


        });
    }

    //change font type for textview.
    public void TextView_EditFont(TextView textView, String path) {
        textView.setTypeface(Typeface.createFromAsset(getAssets(), path));
    }

    //change font type for edittext.
    public void EditText_EditFont(EditText editText, String path) {
        editText.setTypeface(Typeface.createFromAsset(getAssets(), path));
    }

    //change font type for button.
    public void Button_EditFont(Button button, String path) {
        button.setTypeface(Typeface.createFromAsset(getAssets(), path));
    }

    private void sign_up(String email, String password) {

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            updatename(user, id_center);
                            create_new_group(user.getUid(), et_GroupName.getText().toString()
                                    , id_center, et_TeacherEmail.getText().toString(),
                                    et_TeacherPassword.getText().toString(), et_TeacherPhone.getText().toString(),
                                    et_TeacherName.getText().toString());
                            Toast.makeText(AddNewGroup.this, id_center,
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Log.w("TAG", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(AddNewGroup.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        FirebaseAuth.getInstance().signOut();

                    }
                });


    }

    private void updatename(FirebaseUser user, String id_center) {
        //updating user's profile data
        UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder()
                .setDisplayName(id_center)
                .build();

        user.updateProfile(profileUpdate)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                });

    } //لادخال بيانات المستخدم

    public void create_new_group(String id_group, String name_groub, String center_name, String email, String password, String phone
            , String teacher_name) {
        FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
        DatabaseReference reference = rootNode.getReference("CenterUsers").
                child(center_name).child("groups").child(id_group).child("group_info");//already found

        reference.setValue(new Group_Info(email, name_groub, password, phone, teacher_name));//add info As value


    }


}

