package com.example.quranprojectdemo.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.example.quranprojectdemo.Other.Adabter_student_image_and_name;
import com.example.quranprojectdemo.Other.Student_Info;
import com.example.quranprojectdemo.Other.Student_data;
import com.example.quranprojectdemo.R;
import com.example.quranprojectdemo.Other.Student_imageand_name;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

//asd
public class Add_a_new_save extends AppCompatActivity {
    //jhklhkljh
    android.widget.Spinner spinner_saves, spinner_save_from, spinner_save_too;
    android.widget.Spinner spinner_reviews, spinner_reviews_from, spinner_reviews_too;
    android.widget.Spinner spinner_select_student;
    Button btn_addSave;
    private String name_center;
    private String name_student;
    private String name_group;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_add_a_new_save);


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
                insert_new_save(name_student, name_group, name_center);

            }
        });
        super.onStart();
    }

    public void insert_new_save(String my_name_stduent, String name_groub, String name_center) {
        FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
        DatabaseReference reference = rootNode.getReference("Center_users");//already found
        DatabaseReference my_center = reference.child(name_center);//already found
        DatabaseReference my_center_groups = my_center.child("groups");//already found or not
        DatabaseReference my_group = my_center_groups.child(name_groub);// add new group
        String save = "";
        String rev = "";

        DatabaseReference my_student_group = my_group.child("student_group");
        DatabaseReference student = my_student_group.child(my_name_stduent);


        DatabaseReference student_save = student.child("student_save");
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        String date_day = formatter.format(date);

        DatabaseReference save1 = student_save.child(date_day);
        save1.setValue(new Student_data(date_day, "day", "not yet", "not yet", "not yet"));
//  save1.setValue(new Det(2, "hello", 21, "rer", "Rer", "rer"));
//        save1.setValue(o);


    }


}