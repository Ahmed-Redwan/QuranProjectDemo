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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddNewGroup extends AppCompatActivity {
    TextView tv_AddNewGroup;
    EditText et_GroupName, et_TeacherName;
    Button btn_add, btn_Cancel;
    FirebaseAuth mAuth;
    private String name_center;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_group);

        tv_AddNewGroup = findViewById(R.id.AddNewGroup_tv_Add);
        et_GroupName = findViewById(R.id.AddNewGroup_et_GroupName);
        et_TeacherName = findViewById(R.id.AddNewGroup_et_TeacherName);
        btn_add = findViewById(R.id.AddNewGroup_btn_Add);
        btn_Cancel = findViewById(R.id.AddNewGroup_btn_cancel);

        TextView_EditFont(tv_AddNewGroup, "Hacen_Tunisia_Bold.ttf");

        EditText_EditFont(et_GroupName, "Hacen_Tunisia.ttf");
        EditText_EditFont(et_TeacherName, "Hacen_Tunisia.ttf");

        Button_EditFont(btn_add, "Hacen_Tunisia.ttf");
        Button_EditFont(btn_Cancel, "Hacen_Tunisia.ttf");


        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sign_up(et_TeacherName.getText().toString() + et_GroupName.getText().toString()
                        + "@gmail.com", et_TeacherName.getText().toString());
                finish();
            }
        });
        btn_Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

    private void sign_up(final String email, final String password) {

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();

                            create_new_group(et_GroupName.getText().toString()
                                    , name_center, email, password, null,
                                    et_TeacherName.getText().toString());
                        } else {
                            Log.w("TAG", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(AddNewGroup.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });


    }

    public void create_new_group(String name_groub, String center_name, String email, String password, String phone
            , String teacher_name) {
        FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
        DatabaseReference reference = rootNode.getReference("Center_users");//already found
        DatabaseReference center1 = reference.child(center_name);//already found
        DatabaseReference center1_groups = center1.child("groups");//already found or not
        DatabaseReference new_group = center1_groups.child(name_groub);// add new group

        DatabaseReference group_info = new_group.child("group_info");//add a new info group
        group_info.setValue(new Group_Info(email, name_groub, password, null, teacher_name));//add info As value
        //..

        //add new students
        //..
        //..
        //..

    }


}

