package com.example.quranprojectdemo;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import static androidx.core.content.ContextCompat.getNoBackupFilesDir;
import static androidx.core.content.ContextCompat.startActivity;

public class CustomGroupRecyclerView extends RecyclerView.Adapter<CustomGroupRecyclerView.View_holder> {
    ArrayList<Group> arrayList;



    public CustomGroupRecyclerView(ArrayList<Group> arrayList) {
        this.arrayList = arrayList;
    }


    @NonNull
    @Override
    public View_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.customgroup_recyclerview,null,false);

        CustomGroupRecyclerView.View_holder v=new CustomGroupRecyclerView.View_holder(view);
        return v;
    }

    @Override
    public void onBindViewHolder(@NonNull final View_holder holder, final int position) {

        Group group=arrayList.get(position);

        holder.iv.setImageResource(group.getImg());
        holder.tv_GroupName.setText(group.getGroupName());
        holder.tv_TeacherName.setText(group.getTeacherName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(),position+"", Toast.LENGTH_SHORT).show();
                view.getContext().startActivity(new Intent(view.getContext(),Show_group_student.class));
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
        return arrayList.size();
    }


    class View_holder extends RecyclerView.ViewHolder {
        ImageView iv;
        TextView tv_GroupName;
        TextView tv_TeacherName;

        public View_holder(@NonNull View itemView) {
            super(itemView);
             iv=itemView.findViewById(R.id.CustomGroup_iv_Image);
             tv_GroupName=itemView.findViewById(R.id.CustomGroup_tv_CircleName);
             tv_TeacherName=itemView.findViewById(R.id.CustomGroup_tv_TeacherName);


        }

    }
}
