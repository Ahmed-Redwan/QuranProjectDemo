package com.example.quranprojectdemo.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quranprojectdemo.Other.Recycler_student;
import com.example.quranprojectdemo.Other.Student_data;
import com.example.quranprojectdemo.Other.Student_data1;
import com.example.quranprojectdemo.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class StudentDetails extends AppCompatActivity {
    TextView tv_student_name, tv_student_name_ring, tv_student_phone, tv_student_identity;
    ImageView image_backe_student, image_student;
    final ArrayList<Student_data1> student_data = new ArrayList<>();
    RecyclerView StudentDetails_recycler;
    Recycler_student recycler_student;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_details);
        StudentDetails_recycler = findViewById(R.id.StudentDetails_recycler);
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.StudentDetails_toolBar);
        image_backe_student = findViewById(R.id.StudentDetails_image_center);
        image_student = findViewById(R.id.StudentDetails_image_student);
        tv_student_name = findViewById(R.id.StudentDetails_tv_name_student);
        tv_student_name_ring = findViewById(R.id.StudentDetails_tv_name_ring);
        tv_student_phone = findViewById(R.id.StudentDetails_tv_phone);
        tv_student_identity = findViewById(R.id.StudentDetails_identity);
        getInRealTimeUsers();

        Intent getExtrasIntent = getIntent();
        String name = getExtrasIntent.getStringExtra("name");
        tv_student_name.setText(name);
        toolbar.setTitle(name);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.GroupListMenu_back) {
                    finish();
                    return true;
                }
                return false;
            }
        });

    }

    @Override
    protected void onStart() {
        recycler_student = new Recycler_student(student_data);
        RecyclerView.LayoutManager l = new GridLayoutManager(this, 1);
        StudentDetails_recycler.setHasFixedSize(true);
        StudentDetails_recycler.setLayoutManager(l);
        StudentDetails_recycler.setAdapter(recycler_student);

        super.onStart();


    }

    public void getInRealTimeUsers() {

        FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
        DatabaseReference reference = rootNode.getReference("CenterUsers").child("Center name")
                .child("Groups").child("groupN name").child("Students").child("Student name");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot c : dataSnapshot.getChildren()) {
                    if (!c.getKey().equals("Student information")){
                    Student_data1 d = c.getValue(Student_data1.class);
                    Toast.makeText(getBaseContext(),d.getDate(),Toast.LENGTH_SHORT).show();
                    student_data.add(d);}
                }


            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
//                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

    }//جلب البيانات

}
