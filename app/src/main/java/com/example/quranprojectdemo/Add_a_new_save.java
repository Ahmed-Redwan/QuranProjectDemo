 package com.example.quranprojectdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
//asd
public class Add_a_new_save extends AppCompatActivity {
//jhklhkljh
     android.widget.Spinner spinner_saves   ,spinner_save_from   ,spinner_save_too;
    android.widget.Spinner spinner_reviews   ,spinner_reviews_from   ,spinner_reviews_too;
     android.widget.Spinner spinner_select_student;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_add_a_new_save);

       spinner_select_student=findViewById(R.id.spinner_selection_student);

        ArrayList<Student_imageand_name> student_imageand_names=new ArrayList<>();
        student_imageand_names.add(new Student_imageand_name("ahmed mohammed"));
        student_imageand_names.add(new Student_imageand_name("ali"));
        student_imageand_names.add(new Student_imageand_name(" mohammed"));


        Recycler_student_image_and_name recycler =new Recycler_student_image_and_name(this,
                R.layout.student_recycler_image_and_name,student_imageand_names);

        spinner_select_student.setAdapter(recycler);








        spinner_saves=findViewById(R.id.spinner_save);
        spinner_save_from=findViewById(R.id.spinner_save_from);
        spinner_save_too=findViewById(R.id.spinner_save_to);

        spinner_reviews=findViewById(R.id.spinner_review);
        spinner_reviews_from=findViewById(R.id.spinner_review_from);
        spinner_reviews_too=findViewById(R.id.spinner_review_to);

        ArrayList<String> save=new ArrayList<>();
        save.add("السورة");
        save.add("الفاتحة");
        save.add("البقرة");
        save.add("آل عمران");
        ArrayAdapter<String> adapter_save =new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,save);
        spinner_saves.setAdapter(adapter_save);


        ArrayList<String> save_from=new ArrayList<>();
        save_from.add("من");
        save_from.add("2");
        save_from.add("3");
        save_from.add("250");
        ArrayAdapter<String> adapter_save_from =new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,save_from);
        spinner_save_from.setAdapter(adapter_save_from);


        ArrayList<String> save_to=new ArrayList<>();
        save_to.add("إلى");
        save_to.add("1");
        save_to.add("2");
        save_to.add("3");
        save_to.add("250");
        ArrayAdapter<String> adapter_save_to =new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,save_to);
        spinner_save_too.setAdapter(adapter_save_to);




        ArrayList<String> review=new ArrayList<>();
        review.add("السورة");
        review.add("الفاتحة");
        review.add("البقرة");
        review.add("آل عمران");
        ArrayAdapter<String> adapter_review =new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,review);
        spinner_reviews.setAdapter(adapter_review);


        ArrayList<String> review_from=new ArrayList<>();
        review_from.add("من");
        review_from.add("2");
        review_from.add("3");
        review_from.add("250");
        ArrayAdapter<String> adapter_review_from =new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,review_from);
        spinner_reviews_from.setAdapter(adapter_review_from);


        ArrayList<String> review_to=new ArrayList<>();
        review_to.add("إلى");
        review_to.add("1");
        review_to.add("2");
        review_to.add("3");
        review_to.add("250");
        ArrayAdapter<String> adapter_review_to =new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,review_to);
        spinner_reviews_too.setAdapter(adapter_review_to);


    }
}