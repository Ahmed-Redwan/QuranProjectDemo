package com.example.quranprojectdemo.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import com.example.quranprojectdemo.Other.Adabter_student_image_and_name;
import com.example.quranprojectdemo.Other.Student_Info;
import com.example.quranprojectdemo.Other.Student_data;
import com.example.quranprojectdemo.R;
import com.example.quranprojectdemo.Other.Student_imageand_name;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

//asd
public class Add_a_new_save extends AppCompatActivity {
    //jhklhkljh
    android.widget.Spinner spinner_saves, spinner_save_from, spinner_save_too;
    android.widget.Spinner spinner_reviews, spinner_reviews_from, spinner_reviews_too;
    android.widget.Spinner spinner_select_student;
    Button btn_addSave;
    private EditText et_numOfSavePages, et_numOfRevPages;
    private String id_center;
    private String id_student = "Hunrz1JDBLMBK1fvWGCvbPL8ID93";
    private String id_group;
    //    private FirebaseAuth mAuth;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_add_a_new_save);

        et_numOfRevPages = findViewById(R.id.student_add_new_save_et_numOfSavePages);
        et_numOfRevPages = findViewById(R.id.student_add_new_save_et_numOfSavePages);
        sp = getSharedPreferences(TeacherLogin.INFO_TEACHER, MODE_PRIVATE);

        id_group = sp.getString(TeacherLogin.ID_LOGIN_TEACHER, "a");
        id_center = sp.getString(TeacherLogin.ID_LOGIN_TEC_CENTER, "a");


//        mAuth = FirebaseAuth.getInstance();
//        id_center = mAuth.getCurrentUser().getDisplayName();
//        id_group = mAuth.getCurrentUser().getUid();
//        name_group = mAuth.getCurrentUser().getPhotoUrl().toString();
        spinner_select_student = findViewById(R.id.spinner_selection_student);
        btn_addSave = findViewById(R.id.student_add_new_save_btn_addSave);
        ArrayList<Student_imageand_name> student_imageand_names = new ArrayList<>();
        student_imageand_names.add(new Student_imageand_name("ahmed mohammed"));
        student_imageand_names.add(new Student_imageand_name("ali"));
        student_imageand_names.add(new Student_imageand_name(" mohammed"));


        Adabter_student_image_and_name adabter = new Adabter_student_image_and_name(this,
                R.layout.student_recycler_image_and_name, student_imageand_names);

        spinner_select_student.setAdapter(adabter);


        spinner_saves = findViewById(R.id.spinner_save);
        spinner_save_from = findViewById(R.id.spinner_save_from);
        spinner_save_too = findViewById(R.id.spinner_save_to);

        spinner_reviews = findViewById(R.id.spinner_review);
        spinner_reviews_from = findViewById(R.id.spinner_review_from);
        spinner_reviews_too = findViewById(R.id.spinner_review_to);

        ArrayList<String> save = new ArrayList<>();
        save.add("السورة");
        save.add("الفاتحة");
        save.add("البقرة");
        save.add("آل عمران");
        ArrayAdapter<String> adapter_save = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, save);
        spinner_saves.setAdapter(adapter_save);


        ArrayList<String> save_from = new ArrayList<>();
        save_from.add("من");
        save_from.add("2");
        save_from.add("3");
        save_from.add("250");
        ArrayAdapter<String> adapter_save_from = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, save_from);
        spinner_save_from.setAdapter(adapter_save_from);


        ArrayList<String> save_to = new ArrayList<>();
        save_to.add("إلى");
        save_to.add("1");
        save_to.add("2");
        save_to.add("3");
        save_to.add("250");
        ArrayAdapter<String> adapter_save_to = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, save_to);
        spinner_save_too.setAdapter(adapter_save_to);


        ArrayList<String> review = new ArrayList<>();
        review.add("السورة");
        review.add("الفاتحة");
        review.add("البقرة");
        review.add("آل عمران");
        ArrayAdapter<String> adapter_review = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, review);
        spinner_reviews.setAdapter(adapter_review);


        ArrayList<String> review_from = new ArrayList<>();
        review_from.add("من");
        review_from.add("2");
        review_from.add("3");
        review_from.add("250");
        ArrayAdapter<String> adapter_review_from = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, review_from);
        spinner_reviews_from.setAdapter(adapter_review_from);


        ArrayList<String> review_to = new ArrayList<>();
        review_to.add("إلى");
        review_to.add("1");
        review_to.add("2");
        review_to.add("3");
        review_to.add("250");
        ArrayAdapter<String> adapter_review_to = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, review_to);
        spinner_reviews_too.setAdapter(adapter_review_to);


    }

    @Override
    protected void onStart() {
        btn_addSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (et_numOfRevPages.getText().toString().isEmpty() || et_numOfSavePages.getText().toString().isEmpty()) {
//                    et_numOfSavePages.setError("يجب إدخال عدد صفحات الحفظ.");
//                    et_numOfRevPages.setError("يجب إدخال عدد صفحات المراجعة.");
//                    return;
//                }
                insert_new_save(id_student, id_group, id_center);


            }
        });
        super.onStart();
    }

    public void insert_new_save(String id_student, String id_groub, String id_center) {
        FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
        DatabaseReference reference = rootNode.getReference("CenterUsers");//already found
        DatabaseReference my_center = reference.child(id_center);//already found
        DatabaseReference my_center_groups = my_center.child("groups");//already found or not
        DatabaseReference my_group = my_center_groups.child(id_groub);// add new group
        String save = "";
        String rev = "";

        DatabaseReference my_student_group = my_group.child("student_group");


        DatabaseReference student = my_student_group.child(id_student);


        DatabaseReference student_save = student.child("student_save");
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        String date_day = formatter.format(date);

        DatabaseReference save1 = student_save.child(date_day);
        save1.setValue(new Student_data(date_day, "day", "not yet", "not yet", "not yet"));
//  save1.setValue(new Det(2, "hello", 21, "rer", "Rer", "rer"));
//        save1.setValue(o);
        my_center_groups.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot d : snapshot.getChildren()) {
                    String student_id = d.getKey();
                    String student_name = d.child("student_info").getValue(Student_Info.class).getName();


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


}