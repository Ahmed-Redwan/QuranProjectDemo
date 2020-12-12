package com.example.quranprojectdemo.recyclerView.student;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quranprojectdemo.Activities.showDetails.StudentDetails;
import com.example.quranprojectdemo.R;
import com.example.quranprojectdemo.models.students.Student_Info;

import java.util.ArrayList;

public class Recycler_show_group_student extends RecyclerView.Adapter<Recycler_show_group_student.View_holder> {

    ArrayList<Student_Info> student_imageand_names;


    public Recycler_show_group_student(ArrayList<Student_Info> student_imageand_names) {
        this.student_imageand_names = student_imageand_names;
    }


    @NonNull
    @Override
    public View_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_recycler_image_and_name, null, false);


        View_holder view_holder = new View_holder(view);

        return view_holder;
    }


    @Override
    public void onBindViewHolder(@NonNull final View_holder holder, final int position) {

        Student_Info studentImageandName = student_imageand_names.get(position);

        holder.name.setText(studentImageandName.getName());

        if (studentImageandName.getImg_student() != null)
            holder.imageView.setImageURI(Uri.parse(studentImageandName.getImg_student()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), position + "", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(view.getContext(), StudentDetails.class);
                i.putExtra("id_center",student_imageand_names.get(position).getId_center());
                i.putExtra("id_group",student_imageand_names.get(position).getId_group());
                i.putExtra("id_student",student_imageand_names.get(position).getId_Student()    );
                view.getContext().startActivity(i);

            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Toast.makeText(view.getContext(), position + "", Toast.LENGTH_SHORT).show();
                return true;
            }
        });

    }


    @Override
    public int getItemCount() {
        return student_imageand_names.size();
    }


    public class View_holder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView name;

        public View_holder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.student_recycler_image_and_name_iv);
            name = itemView.findViewById(R.id.student_recycler_image_and_name_n);
            name.setTypeface(Typeface.createFromAsset(itemView.getContext().getAssets(), "Hacen_Tunisia.ttf"));


        }

    }
}
