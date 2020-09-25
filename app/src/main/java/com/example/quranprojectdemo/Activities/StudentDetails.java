package com.example.quranprojectdemo.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quranprojectdemo.Other.Recycler_student;
import com.example.quranprojectdemo.Other.Student_Info;
import com.example.quranprojectdemo.Other.Student_data;
import com.example.quranprojectdemo.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class StudentDetails extends AppCompatActivity {
    TextView tv_student_name, tv_student_name_ring, tv_student_phone, tv_student_identity;
    ImageView image_backe_student, image_student;
    final ArrayList<Student_data> student_data = new ArrayList<>();
    RecyclerView StudentDetails_recycler;
    Recycler_student recycler_student;
    private FirebaseAuth mAuth;
    String id_center, id_group, id_student;
    String id_center_c, id_group_c, id_student_c;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_details);
        mAuth = FirebaseAuth.getInstance();
        sp = getSharedPreferences(TeacherLogin.INFO_TEACHER, MODE_PRIVATE);


        id_group = sp.getString(TeacherLogin.ID_LOGIN_TEACHER, "a");
        id_center = sp.getString(TeacherLogin.ID_LOGIN_TEC_CENTER, "a");

        StudentDetails_recycler = findViewById(R.id.StudentDetails_recycler);
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.StudentDetails_toolBar);
        image_backe_student = findViewById(R.id.StudentDetails_image_center);
        image_student = findViewById(R.id.StudentDetails_image_student);
        tv_student_name = findViewById(R.id.StudentDetails_tv_name_student);
        tv_student_name_ring = findViewById(R.id.StudentDetails_tv_name_ring);
        tv_student_phone = findViewById(R.id.StudentDetails_tv_phone);
        tv_student_identity = findViewById(R.id.StudentDetails_identity);


        Intent i = getIntent();

        id_student_c = i.getStringExtra("id_student");
        id_group_c = i.getStringExtra("id_group");
        id_center_c = i.getStringExtra("id_center");
        if (!id_student_c.isEmpty()) {
            id_group = id_group_c;
            id_center = id_center_c;
            id_student = id_student_c;

        }

//        Intent getExtrasIntent = getIntent();
//        String name = getExtrasIntent.getStringExtra("name");
//        tv_student_name.setText(name);
//        toolbar.setTitle(name);
//        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                if (item.getItemId() == R.id.GroupListMenu_back) {
//                    finish();
//                    return true;
//                }
//                return false;
//            }
//        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        getStudnetInfo(id_center,id_group,id_student);
        getSavesStudent(id_center, id_group, id_student);


    }

//    public void getSavesStudent(String id_center, String id_group, String id_student) {
//
//        FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
//        DatabaseReference reference = rootNode.getReference("CenterUsers").child(id_center)
//                .child("groups").child(id_group).child("student_group").child(id_student).child("student_save");
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
//                for (DataSnapshot c : dataSnapshot.getChildren()) {
//                    if (!c.getKey().equals("student_info")) {
//                        Student_data d = c.getValue(Student_data.class);
//                        Toast.makeText(getBaseContext(), d.getDate__student(), Toast.LENGTH_SHORT).show();
//                        student_data.add(d);
//                    }
//                }
//
//                recycler_student = new Recycler_student(student_data);
//                RecyclerView.LayoutManager l = new GridLayoutManager(getBaseContext(), 1);
//                StudentDetails_recycler.setHasFixedSize(true);
//                StudentDetails_recycler.setLayoutManager(l);
//                StudentDetails_recycler.setAdapter(recycler_student);
//
//
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError error) {
//                // Failed to read value
////                Log.w(TAG, "Failed to read value.", error.toException());
//            }
//        });
//
//    }//جلب البيانات
    public void getStudnetInfo(String id_center, String id_group, String id_student) {

        FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
        DatabaseReference reference = rootNode.getReference("CenterUsers").child(id_center)
                .child("groups").child(id_group).child("student_group").child(
                        id_student).child("student_info");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Student_Info studentInfo = dataSnapshot.getValue(Student_Info.class);
                tv_student_name.setText(studentInfo.getName());
                tv_student_name_ring.setText(studentInfo.getEmail());
                tv_student_phone.setText(studentInfo.getPhoneNo());
                tv_student_identity.setText(studentInfo.getId_number()+"");
//                toolbar_student.setTitle(studentInfo.getName());


            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });

    }//جلب البيانات
    public void getSavesStudent(String id_center, String id_group, String id_student) {

        FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
        DatabaseReference reference = rootNode.getReference("CenterUsers").child(id_center)
                .child("groups").child(id_group).child("student_group").child(id_student).child("student_save");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                student_data.clear();

                for (DataSnapshot c : dataSnapshot.getChildren()) {
                    for (DataSnapshot c1 : c.getChildren()) {
                        for (DataSnapshot c3 : c1.getChildren()) {
                            Student_data d = c3.getValue(Student_data.class);
                            student_data.add(d);


                        }

                    }

                }
                recycler_student = new Recycler_student(student_data);
                RecyclerView.LayoutManager l = new GridLayoutManager(getBaseContext(), 1);
                StudentDetails_recycler.setHasFixedSize(true);
                StudentDetails_recycler.setLayoutManager(l);
                StudentDetails_recycler.setAdapter(recycler_student);


            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
//                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

    }//جلب البيانات

}
