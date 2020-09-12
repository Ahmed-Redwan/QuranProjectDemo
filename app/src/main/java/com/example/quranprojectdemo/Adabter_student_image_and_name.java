package com.example.quranprojectdemo;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adabter_student_image_and_name extends BaseAdapter {

    Context context;
    int resourse;
    ArrayList<Student_imageand_name>student_imageand_names;


    public Adabter_student_image_and_name(Context context, int resourse, ArrayList<Student_imageand_name> student_imageand_names) {
        this.context = context;
        this.resourse = resourse;
        this.student_imageand_names = student_imageand_names;
    }

    @Override
    public int getCount() {
        return student_imageand_names.size();
    }

    @Override
    public Student_imageand_name getItem(int position) {
        return student_imageand_names.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view =convertView;

        if (view==null){
            view=LayoutInflater.from(context).inflate(resourse,null,false);
        }

        TextView tv_name =view.findViewById(R.id.student_recycler_image_and_name_n);
        ImageView image =view.findViewById(R.id.student_recycler_image_and_name_iv);

        Student_imageand_name studentImageandName =getItem(position);

        tv_name.setText(studentImageandName.getName());
        if (studentImageandName.getImage()!=null)
        image.setImageURI(Uri.parse(studentImageandName.getImage()));

        return view;
    }
}



//ArrayList<Student_imageand_name> arrayList_student_image;
//    int resourse ;
//
//
//    public Recycler_student(Context context,int resourse , ArrayList<Student_imageand_name> arrayList_student) {
//        this.arrayList_student = arrayList_student;
//        this.context=context;
//        this.resourse=resourse;
//    }
//
//
//    @Override
//    public int getCount() {
//        return arrayList_student.size();
//    }
//
//    @Override
//    public Student_imageand_name getItem(int position) {
//        return arrayList_student.get(position);
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return 0;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        View view=convertView;
//        if (view==null)
//            view=LayoutInflater.from(context).inflate(resourse,null,false);
//
//
//        TextView  tv_name= view.findViewById(R.id.student_recycler_image_and_name_n);
//        ImageView image_student=view.findViewById(R.id.student_recycler_image_and_name_iv);
//
//
//        Student_imageand_name  student = getItem(position);
//        tv_name.setText(student.getName());
//        image_student.setImageURI(Uri.parse(student.getImage()));
//
//        return view;
//    }


//**********************************************************************************************************
//    ArrayList<Student_imageand_name> arrayList;
//
//    public Recycler_student_image_and_name(ArrayList<Student_imageand_name> arrayList) {
//        this.arrayList = arrayList;
//    }
//
//    @NonNull
//    @Override
//    public View_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//
//   View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_recycler_image_and_name,null,false);
//
//        View_holder view_holder =new View_holder(view);
//
//        return view_holder;
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull View_holder holder, int position) {
//
//
//        Student_imageand_name studentImageandName=arrayList.get(position);
//
//        holder.name.setText(studentImageandName.getName());
//    }
//
//    @Override
//    public int getItemCount() {
//        return arrayList.size();
//    }
//
//    public class View_holder extends RecyclerView.ViewHolder {
//
//        ImageView image ;
//        TextView name ;
//
//        public View_holder(@NonNull View itemView) {
//            super(itemView);
//
////            image=itemView.findViewById(R.id.student_recycler_image_and_name_iv);
//            name=itemView.findViewById(R.id.student_recycler_image_and_name_n);
//
//
//        }
//    }


