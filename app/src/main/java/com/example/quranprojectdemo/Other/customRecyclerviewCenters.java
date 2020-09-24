package com.example.quranprojectdemo.Other;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quranprojectdemo.Activities.JoinRequest2;
import com.example.quranprojectdemo.Activities.JoinRequest3;
import com.example.quranprojectdemo.Activities.Show_group_student;
import com.example.quranprojectdemo.R;

import java.util.ArrayList;

public class customRecyclerviewCenters extends RecyclerView.Adapter<customRecyclerviewCenters.View_holder> {

    private ArrayList<Center>centers;
    Context context;
    public customRecyclerviewCenters(ArrayList<Center>centers,Context context){
        this.centers=centers;
        this.context=context;
    }



    @NonNull
    @Override
    public customRecyclerviewCenters.View_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_center_item,null,false);

        customRecyclerviewCenters.View_holder view_holder=new customRecyclerviewCenters.View_holder(view);

        return view_holder;
    }
public void addNewCenter(Center centers){
            this.centers.add(centers);
            notifyDataSetChanged();
}
    @Override
    public void onBindViewHolder(@NonNull final customRecyclerviewCenters.View_holder holder, final int position) {

        final Center center=centers.get(position);

        holder.iv.setImageResource(center.getImg());
        holder.tv_phone.setText(center.getPhone());
        holder.tv_name.setText(center.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        view.getContext().startActivity(new Intent(view.getContext(), JoinRequest3.class).putExtra
                                ("CenterId",centers.get(position).getId()));
                    }
                });

            }
        });
    }


    @Override
    public int getItemCount() {
        return centers.size();
    }



    class View_holder extends RecyclerView.ViewHolder {
        ImageView iv;
        TextView tv_name;
        TextView tv_phone;
        public View_holder(@NonNull View itemView) {
            super(itemView);

            iv=itemView.findViewById(R.id.CustomCenterItem_iv);
            tv_name=itemView.findViewById(R.id.CustomCenterItem_tv_CenterName);
            tv_phone=itemView.findViewById(R.id.CustomCenterItem_tv_PhoneNumber);

            tv_name.setTypeface(Typeface.createFromAsset(itemView.getContext().getAssets(),"Hacen_Tunisia.ttf"));
            tv_phone.setTypeface(Typeface.createFromAsset(itemView.getContext().getAssets(),"Hacen_Tunisia.ttf"));

        }
    }
}
