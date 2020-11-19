package com.example.quranprojectdemo.recyclerView.reportRequest;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.quranprojectdemo.activities.joinRequsers.JoinRequest3;
import com.example.quranprojectdemo.R;
import com.example.quranprojectdemo.models.centers.Center;

import java.util.ArrayList;

public class CustomRecyclerviewCenters extends RecyclerView.Adapter<CustomRecyclerviewCenters.View_holder> {

    private ArrayList<Center>centers;
    Context context;
    public CustomRecyclerviewCenters(ArrayList<Center>centers, Context context){
        this.centers=centers;
        this.context=context;
    }



    @NonNull
    @Override
    public CustomRecyclerviewCenters.View_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_center_item,null,false);

        CustomRecyclerviewCenters.View_holder view_holder=new CustomRecyclerviewCenters.View_holder(view);

        return view_holder;
    }
public void addNewCenter(Center centers){
            this.centers.add(centers);
            notifyDataSetChanged();
}
    @Override
    public void onBindViewHolder(@NonNull final CustomRecyclerviewCenters.View_holder holder, final int position) {

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
