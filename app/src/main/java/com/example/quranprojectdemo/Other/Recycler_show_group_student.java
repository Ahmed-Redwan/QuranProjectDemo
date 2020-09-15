package com.example.quranprojectdemo.Other;

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

import com.example.quranprojectdemo.Activities.StudentDetails;
import com.example.quranprojectdemo.R;

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
    public void onBindViewHolder(@NonNull final View_holder holder, final int position) {

        Student_imageand_name studentImageandName =student_imageand_names.get(position);

        holder.name.setText(studentImageandName.getName());

        if (studentImageandName.getImage()!=null)
        holder.imageView.setImageURI(Uri.parse(studentImageandName.getImage()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(),position+"", Toast.LENGTH_SHORT).show();
     view.getContext().startActivity(new Intent(view.getContext(), StudentDetails.class).putExtra("name",holder.name.getText()));
               /* ShowmeMorizationLoops showmeMorizationLoops=new ShowmeMorizationLoops();
                showmeMorizationLoops.intent(JoinRequest3.class);*/
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Toast.makeText(view.getContext(),position+"", Toast.LENGTH_SHORT).show();
                return true;
            }
        });

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
            name.setTypeface(Typeface.createFromAsset(itemView.getContext().getAssets(),"Hacen_Tunisia.ttf"));


        }

    }
}
