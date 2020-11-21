package com.example.quranprojectdemo.Activities.showDetails;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quranprojectdemo.Activities.logIn.TeacherLogin;
import com.example.quranprojectdemo.realm.RealmDataBaseItems;
import com.example.quranprojectdemo.recyclerView.student.Recycler_student;
import com.example.quranprojectdemo.models.students.Student_Info;
import com.example.quranprojectdemo.models.students.Student_data;
import com.example.quranprojectdemo.R;
import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StudentDetails extends AppCompatActivity {
    TextView tv_student_name, tv_student_name_ring, tv_student_phone, tv_student_identity;
    ImageView image_backe_student, image_student;
    final ArrayList<Student_data> student_data = new ArrayList<>();
    RecyclerView StudentDetails_recycler;
    Recycler_student recycler_student;
    private FirebaseAuth mAuth;
    String id_center, id_group, id_student;
    String id_center_c, id_group_c, id_student_c;
    RealmDataBaseItems dataBaseItems;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_details);
        mAuth = FirebaseAuth.getInstance();
        sp = getSharedPreferences(TeacherLogin.INFO_TEACHER, MODE_PRIVATE);
        dataBaseItems = RealmDataBaseItems.getinstance(getBaseContext());
        id_group = sp.getString(TeacherLogin.ID_LOGIN_TEACHER, "a");
        id_center = sp.getString(TeacherLogin.ID_LOGIN_TEC_CENTER, "a");

        StudentDetails_recycler = findViewById(R.id.StudentDetails_recycler);
        Toolbar toolbar = findViewById(R.id.StudentDetails_toolBar);
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
        if (id_student_c != null) {
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
        getStudnetInfo(id_student, id_group);
        getSavesStudent(id_student, id_group);


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

//    public void getSavesStudent(String id_center, String id_group, String id_student) {
//
//        FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
//        DatabaseReference reference = rootNode.getReference("CenterUsers").child(id_center)
//                .child("groups").child(id_group).child("student_group").child(id_student).child("student_save");
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                student_data.clear();
//
//                for (DataSnapshot c : dataSnapshot.getChildren()) {
//                    for (DataSnapshot c1 : c.getChildren()) {
//                        for (DataSnapshot c3 : c1.getChildren()) {
//                            Student_data d = c3.getValue(Student_data.class);
//                            student_data.add(d);
//
//
//                        }
//
//                    }
//
//                }
//                recycler_student = new Recycler_student(student_data);
//                RecyclerView.LayoutManager l = new GridLayoutManager(getBaseContext(), 1);
//                StudentDetails_recycler.setHasFixedSize(true);
//                StudentDetails_recycler.setLayoutManager(l);
//                StudentDetails_recycler.setAdapter(recycler_student);
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


    public void getSavesStudent(String id_student, String id_group) {


        Date date = new Date();

        SimpleDateFormat yearForamt = new SimpleDateFormat("yyyy");
        int date_year = Integer.parseInt(yearForamt.format(date));

        SimpleDateFormat monthForamt = new SimpleDateFormat("MM");
        int date_month = Integer.parseInt(monthForamt.format(date));
        String typeName[] = {"year_save", "month_save", "id_student", "id_group"};
        String value[] = {String.valueOf(date_year), String.valueOf(date_month), id_student, id_group};
        List<Student_data> student_dataList = dataBaseItems.getDataWithAndStatement(typeName, value, Student_data.class);


//                    .equalTo("year_save", date_year).
//                            and().equalTo("month_save", date_month).and().equalTo("id_student", id_student)
//                    .and().equalTo("id_group", id_group).findAll();


//        List<Student_data> student_dataList = dataBaseItems.getStudentData(date_year, date_month, id_student, id_group);
        if (student_dataList != null) {

            recycler_student = new Recycler_student(student_dataList);
            RecyclerView.LayoutManager l = new GridLayoutManager(getBaseContext(), 1);
            StudentDetails_recycler.setHasFixedSize(true);
            StudentDetails_recycler.setLayoutManager(l);
            StudentDetails_recycler.setAdapter(recycler_student);
        }
    }


    public void getStudnetInfo(String id_student, String id_group) {
        String typeName[] = {"id_group", "id_Student"};
        String value[] = {id_group, id_student};

        Student_Info student_info = (Student_Info) dataBaseItems.getDataWithAndStatement(typeName, value, Student_Info.class).get(0);
        if (student_info != null) {
            Log.d("eer", id_center_c + " 0");
            Log.d("eer", id_group + " 2");
            Log.d("eer", id_student + " 1");

            tv_student_name.setText("الطالب " + student_info.getName());
            tv_student_name_ring.setText("الإيميل:" + student_info.getEmail());
            tv_student_phone.setText(student_info.getPhoneNo());
            tv_student_identity.setText("رقم الهوية:" + student_info.getId_number());
//
        }
    }
//    public void getStudnetInfo(String id_center, String id_group, String id_student) {
//
//        FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
//        DatabaseReference reference = rootNode.getReference("CenterUsers").child(id_center)
//                .child("groups").child(id_group).child("student_group").child(
//                        id_student).child("student_info");
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                Student_Info studentInfo = dataSnapshot.getValue(Student_Info.class);
//                tv_student_name.setText(studentInfo.getName());
//                tv_student_name_ring.setText(studentInfo.getEmail());
//                tv_student_phone.setText(studentInfo.getPhoneNo());
//                tv_student_identity.setText(studentInfo.getId_number() + "");
////                toolbar_student.setTitle(studentInfo.getName());
//
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError error) {
//            }
//        });
//
//    }//جلب البيانات

}
