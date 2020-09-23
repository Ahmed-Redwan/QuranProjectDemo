package com.example.quranprojectdemo.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.quranprojectdemo.Activities.Add_a_new_save;
import com.example.quranprojectdemo.Other.Group_Info;
import com.example.quranprojectdemo.Other.Student_Info;
import com.example.quranprojectdemo.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Main_teacher extends AppCompatActivity {

    ImageView image_backe_teacher, image_teacher;
    TextView tv_teacher_name, tv_teacher_name_ring, tv_teacher_phone, tv_teacher_count_student;
    FirebaseAuth mAuth;
    Toolbar toolbar_teacher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_teacher);
        mAuth = FirebaseAuth.getInstance();
//     Caused by: java.lang.NullPointerException: Attempt to invoke virtual method
//     'java.lang.String com.google.firebase.auth.FirebaseUser.getUid()' on a null object reference
        getInfoTeacher(mAuth.getCurrentUser().getUid(), mAuth.getCurrentUser().getDisplayName());
        image_backe_teacher = findViewById(R.id.teacher_main_image_center);
        image_teacher = findViewById(R.id.teacher_main_image_teacher);
        tv_teacher_name = findViewById(R.id.teacher_main_tv_name_teacher);
        tv_teacher_name_ring = findViewById(R.id.teacher_main_tv_name_ring);
        tv_teacher_phone = findViewById(R.id.teacher_main_tv_phone);
        tv_teacher_count_student = findViewById(R.id.teacher_main_tv_count_student);

        toolbar_teacher = findViewById(R.id.teacher_main_tool);
        toolbar_teacher.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.MenuTeacherHomeAddNewSave:
                        startActivity(new Intent(getBaseContext(), Add_a_new_save.class));
                        return true;
                    case R.id.MenuTeacherHomeAddStudent:
                        startActivity(new Intent(getBaseContext(), AddNewStudent.class));
                        return true;
                    case R.id.MenuTeacherHomeShowInfo:
                        startActivity(new Intent(getBaseContext(), Show_group_student.class));
                        return true;
                    case R.id.MenuTeacherHomeSettings:
                        return true;
                    case R.id.MenuTeacherHomeAbout:
                        startActivity(new Intent(getBaseContext(), AboutApp.class));
                        return true;
                    case R.id.MenuTeacherHomeExit:
                        finish();
                        return true;
                }
                return false;
            }
        });


        TextView_EditFont(tv_teacher_count_student, "Hacen_Tunisia.ttf");
        TextView_EditFont(tv_teacher_name, "Hacen_Tunisia.ttf");
        TextView_EditFont(tv_teacher_name_ring, "Hacen_Tunisia.ttf");
        TextView_EditFont(tv_teacher_phone, "Hacen_Tunisia.ttf");
    }

    //change font type for textview.
    public void TextView_EditFont(TextView textView, String path) {
        textView.setTypeface(Typeface.createFromAsset(getAssets(), path));
    }

    public void getInfoTeacher(String id_group, String id_center) {

        FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
        final DatabaseReference reference = rootNode.getReference("CenterUsers").child(id_center)
                .child("groups").child(id_group).child("group_info");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            //tv_teacher_name, tv_teacher_name_ring, tv_teacher_phone, tv_teacher_count_student;
            public void onDataChange(DataSnapshot dataSnapshot) {
                Group_Info val = dataSnapshot.getValue(Group_Info.class);
                try {
                    val.getEmail();
                    tv_teacher_name.setText(val.getTeacher_name()+"");
                    tv_teacher_phone.setText(val.getPhone()+"");
                    tv_teacher_name_ring.setText(val.getGroup_name()+"");
                } catch (Exception c) {

                }

//                DatabaseReference d = reference.getParent().child("st");


            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
//                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
        DatabaseReference reference1 = rootNode.getReference("CenterUsers").child(id_center)
                .child("groups").child(id_group).child("student_group");
        reference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                tv_teacher_count_student.setText(snapshot.getChildrenCount() + "");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }//جلب البيانات

}