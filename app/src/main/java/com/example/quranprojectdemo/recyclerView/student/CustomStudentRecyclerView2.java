package com.example.quranprojectdemo.recyclerView.student;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quranprojectdemo.R;
import com.example.quranprojectdemo.models.students.Student_Info;

import java.util.ArrayList;

public class CustomStudentRecyclerView2 extends RecyclerView.Adapter<CustomStudentRecyclerView2.View_holder> {
    ArrayList<Student_Info> arrayList;
    Context context;

    public CustomStudentRecyclerView2(ArrayList<Student_Info> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context=context;
    }


    @NonNull
    @Override
    public View_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_horizintal_student, null, false);

        CustomStudentRecyclerView2.View_holder v = new CustomStudentRecyclerView2.View_holder(view);
        return v;
    }

    @Override
    public void onBindViewHolder(@NonNull final View_holder holder, final int position) {

        Student_Info student_info = arrayList.get(position);


        //holder.iv.setImageResource(group.getImg());
        holder.tv_GroupName.setText(student_info.getName());
        holder.tv_TeacherName.setText(student_info.getPhoneNo());

     /*   holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), position + "", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(view.getContext(), Show_group_student.class);
                i.putExtra("id_group", arrayList.get(position).getId_group());
                i.putExtra("id_center", arrayList.get(position).getId_center());
                view.getContext().startActivity(i);
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
//                Toast.makeText(view.getContext(), position + "", Toast.LENGTH_SHORT).show();
                return true;
            }
        });*/

    }


    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    class View_holder extends RecyclerView.ViewHolder {
        ImageView iv;
        TextView tv_GroupName;
        TextView tv_TeacherName;

        public View_holder(@NonNull View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.custom_horizintal_student_iv);
            tv_GroupName = itemView.findViewById(R.id.custom_horizintal_student_tv_CenterName);
            tv_TeacherName = itemView.findViewById(R.id.custom_horizintal_student_tv_PhoneNumber);

            tv_TeacherName.setTypeface(Typeface.createFromAsset(itemView.getContext().getAssets(), "Hacen_Tunisia.ttf"));
            tv_GroupName.setTypeface(Typeface.createFromAsset(itemView.getContext().getAssets(), "Hacen_Tunisia.ttf"));
        }
    }
}