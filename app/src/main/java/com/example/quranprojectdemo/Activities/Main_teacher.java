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

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class Main_teacher extends AppCompatActivity {

    ImageView image_backe_teacher, image_teacher;
    TextView tv_teacher_name, tv_teacher_name_ring, tv_teacher_phone, tv_teacher_count_student;
    FirebaseAuth mAuth;
    Toolbar toolbar_teacher;
    Realm realm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_teacher);
        mAuth = FirebaseAuth.getInstance();
        Realm.init(getBaseContext());
         realm = Realm.getDefaultInstance();
//     Caused by: java.lang.NullPointerException: Attempt to invoke virtual method
//     'java.lang.String com.google.firebase.auth.FirebaseUser.getUid()' on a null object reference
 //       getInfoTeacher();
        image_backe_teacher = findViewById(R.id.teacher_main_image_center);
        /*image_teacher = findViewById(R.id.teacher_main_image_teacher);*/
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

    public void getInfoTeacher() {

        RealmQuery<Group_Info> query = realm.where(Group_Info.class);
        Group_Info val = query.findFirst();

        tv_teacher_name.setText("المحفظ " + val.getTeacher_name());
        tv_teacher_phone.setText("رقم الهاتف:" + val.getPhone());
        tv_teacher_name_ring.setText("حلقة " + val.getGroup_name());
        toolbar_teacher.setTitle("حلقة " + val.getGroup_name());
        tv_teacher_count_student.setText(val.getAuto_sutdent_id());
//        RealmQuery<Student_Info> query1 = realm.where(Student_Info.class);
//
//        tv_teacher_count_student.setText("عدد طلاب الحلقة:" + query1.count());

    }



}

