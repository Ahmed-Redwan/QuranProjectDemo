package com.example.quranprojectdemo.chat;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.quranprojectdemo.R;
import com.example.quranprojectdemo.chat.Chat;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class RecyclerMassege extends RecyclerView.Adapter<RecyclerMassege.ViewHolder> {

    public static final int MSG_TYPE_RAIGT=1;
    public static final int MSG_TYPE_LEFT=0;
    Context context;
    ArrayList<Chat>arrayList;
//    String imageUrl;
//                                                                      , String imageUrl
    public RecyclerMassege(Context context, ArrayList<Chat> arrayList                     ){
        this.context = context;
        this.arrayList = arrayList;
//        this.imageUrl = imageUrl;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType==MSG_TYPE_RAIGT){
            View view= LayoutInflater.from(context).inflate(R.layout.avtivity_massege_right,null,false);
            return new ViewHolder(view);
        }else {
            View view= LayoutInflater.from(context).inflate(R.layout.avtivity_massege_left,null,false);
            return new ViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Chat chats=arrayList.get(position);
        holder.text_massege.setText(chats.getMassege());
        if (position == (arrayList.size()- 1)){
                if (chats.isSeen()){
                    holder.text_seen.setText("seen");
                }else {
                    holder.text_seen.setText("no watch");
                }
        }else {
            holder.text_seen.setVisibility(View.GONE);
        }

//        if (imageUrl.equals("default")){
//            holder.profile_image.setImageResource(R.drawable.ic_baseline_person_24);
//            } else {
//                Glide.with(context).load(imageUrl).into(holder.profile_image);
//            }


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView text_massege;
        TextView text_seen;
//        ImageView profile_image;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            text_massege=itemView.findViewById(R.id.text_massege);
            text_seen=itemView.findViewById(R.id.tv_seen);
//            profile_image =itemView.findViewById(R.id.profile_image);
        }
    }

    @Override
    public int getItemViewType(int position) {
        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();

        if (arrayList.get(position).getSender().equals(user.getUid())){
            return MSG_TYPE_RAIGT;
        }else {
            return MSG_TYPE_LEFT;
        }
    }
}