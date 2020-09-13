package com.example.quranprojectdemo;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Recycler_show_group_student extends RecyclerView.Adapter<Recycler_show_group_student.View_holder> {


    ArrayList<Student_imageand_name> student_imageand_names;

    public Recycler_show_group_student(ArrayList<Student_imageand_name> student_imageand_names) {
        this.student_imageand_names = student_imageand_names;
    }

    @NonNull
    @Override
    public View_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_recycler_image_and_name,null,false);


     View_holder view_holder=new View_holder(view);

        return view_holder;
    }

    @Override
    public void onBindViewHolder(@NonNull View_holder holder, int position) {

        Student_imageand_name studentImageandName =student_imageand_names.get(position);

        holder.name.setText(studentImageandName.getName());

        if (studentImageandName.getImage()!=null)
        holder.imageView.setImageURI(Uri.parse(studentImageandName.getImage()));
    }

    @Override
    public int getItemCount() {
        return student_imageand_names.size();
    }

    public class View_holder extends RecyclerView.ViewHolder{

        ImageView imageView ;
        TextView name ;

        public View_holder(@NonNull View itemView) {
            super(itemView);

            imageView=itemView.findViewById(R.id.student_recycler_image_and_name_iv);
            name=itemView.findViewById(R.id.student_recycler_image_and_name_n);


        }
    }
}
