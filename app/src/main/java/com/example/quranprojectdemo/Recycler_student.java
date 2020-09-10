package com.example.quranprojectdemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Recycler_student  extends RecyclerView.Adapter<Recycler_student.View_holder> {

    ArrayList<Student_data> arrayList_student;


    public Recycler_student (ArrayList<Student_data> arrayList_student){
        this.arrayList_student=arrayList_student;
    }


    @NonNull
    @Override
    public View_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_recycler,null,false);

        View_holder v=new View_holder(view);

        return v;
    }

    @Override
    public void onBindViewHolder(@NonNull View_holder holder, int position) {

        Student_data student_data= arrayList_student.get(position);

        holder.tv_date.setText(student_data.getDate__student());
        holder.tv_day.setText(student_data.getDay_student());
        holder.tv_attendess.setText(student_data.getAttendess_student());
        holder.tv_review.setText(student_data.getReview_student());
        holder.tv_save.setText(student_data.getSave_student());


    }

    @Override
    public int getItemCount() {
        return arrayList_student.size();
    }



    public  class  View_holder extends RecyclerView.ViewHolder{

        TextView  tv_date  , tv_day  ,tv_attendess  ,tv_review  ,tv_save ;
        public View_holder(@NonNull View itemView) {
            super(itemView);

            tv_date=itemView.findViewById(R.id.student_recycler_date);
            tv_day=itemView.findViewById(R.id.student_recycler_day);
            tv_attendess=itemView.findViewById(R.id.student_recycler_attendess);
            tv_review=itemView.findViewById(R.id.student_recycler_review);
            tv_save=itemView.findViewById(R.id.student_recycler_save);
        }
    }
}
