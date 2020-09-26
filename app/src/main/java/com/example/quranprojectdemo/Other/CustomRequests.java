package com.example.quranprojectdemo.Other;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.media.Image;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quranprojectdemo.Activities.AddNewStudent;
import com.example.quranprojectdemo.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.concurrent.Executor;

public class CustomRequests extends RecyclerView.Adapter<CustomRequests.View_Holder> {


    private ArrayList<Request> requests;
    Context context;
    Request request;
    private FirebaseAuth mAuth;



    public CustomRequests(ArrayList<Request> requests, Context context) {
        this.requests = requests;
        this.context = context;
    }

    @NonNull
    @Override
    public View_Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.customrecyclerrequests, null, false);

        View_Holder view_holder = new View_Holder(view);

        return view_holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final View_Holder holder, int position) {
        request = requests.get(position);
        holder.tv_name.setText(request.getName());
        holder.tv_date.setText(request.getBirth_date());
        holder.tv_grade.setText(request.getAcademic_level());
        holder.tv_email.setText(request.getEmail());
        holder.tv_id.setText(request.getId_number());
        holder.tv_phone.setText(request.getPhoneNo());
        holder.iv_student.setImageResource(request.getImg());
        mAuth = FirebaseAuth.getInstance();

        holder.tv_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sign_up();


            }
        });
        holder.tv_refuse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        holder.iv_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                make_A_Call(request.getPhoneNo());
            }
        });
        holder.iv_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


    }
    private void sign_up() {

        mAuth.createUserWithEmailAndPassword(request.getEmail(), request.getPhoneNo())
                .addOnCompleteListener((Executor) this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            create_new_student(user.getUid(), request.getGroupid(), request.getCenterid());
                            updatename(user);

                            FirebaseAuth.getInstance().signOut();

                        } else {
                            Log.w("TAG", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(context.getApplicationContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });


    }//للتسجيل


    private void updatename(FirebaseUser user) {
        UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder()
                .setDisplayName(request.getCenterid()).setPhotoUri(Uri.parse(request.getGroupid()))
                .build();
        user.updateProfile(profileUpdate)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                });

    }
    public void create_new_student(String name_student, String id_groub, String id_center) {
        String birth_day = request.getDay()+ "/" + request.getMonth() + "/" + request.getYear();
        FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
        DatabaseReference reference = rootNode.getReference("CenterUsers");//already found
        DatabaseReference center = reference.child(id_center);//already found
        DatabaseReference center_groups = center.child("groups");//already found or not
        DatabaseReference new_group = center_groups.child(id_groub);// add new group

        DatabaseReference student_group = new_group.child("student_group");
        DatabaseReference new_student = student_group.child(name_student);

        DatabaseReference student_info = new_student.child("student_info");
        student_info.setValue(new Student_Info(request.getName(),
                Integer.parseInt(request.getId_number()),
                request.getPhoneNo(),
                request.getEmail(), request.getAcademic_level(), birth_day));


    }

    public void make_A_Call(String Number) {
        Intent i = new Intent(Intent.ACTION_CALL);
        i.setData(Uri.parse("tel:" + Number));

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        context.startActivity(i);
    }


    public void gmail_msg(String Email) {
        final Intent intent = new Intent(Intent.ACTION_VIEW)
                .setType("plain/text")
                .setData(Uri.parse(Email))
                .setClassName("com.google.android.gm", "com.google.android.gm.ComposeActivityGmail");
        context.startActivity(intent);
    }


    @Override
    public int getItemCount() {
        return requests.size();
    }

    class View_Holder extends RecyclerView.ViewHolder {
        ImageView iv_student,iv_call,iv_email;
        TextView tv_name,tv_id,tv_date,tv_grade,tv_email,tv_accept,tv_refuse,tv_phone;



        public View_Holder(@NonNull View itemView) {
            super(itemView);
            iv_student=itemView.findViewById(R.id.requests_iv_student);
            iv_call=itemView.findViewById(R.id.requests_iv_call);
            iv_email=itemView.findViewById(R.id.requests_iv_email);
            tv_name=itemView.findViewById(R.id.requests_tv_name);
            tv_id=itemView.findViewById(R.id.requests_tv_id);
            tv_date=itemView.findViewById(R.id.requests_tv_birthOfDate);
            tv_grade=itemView.findViewById(R.id.requests_tv_Grade);
            tv_email=itemView.findViewById(R.id.requests_tv_Email);
            tv_accept=itemView.findViewById(R.id.requests_tv_accept);
            tv_phone=itemView.findViewById(R.id.requests_tv_phone);
            tv_refuse=itemView.findViewById(R.id.requests_tv_Refuse);

            tv_phone.setTypeface(Typeface.createFromAsset(itemView.getContext().getAssets(),"Hacen_Tunisia.ttf"));
            tv_name.setTypeface(Typeface.createFromAsset(itemView.getContext().getAssets(),"Hacen_Tunisia.ttf"));
            tv_id.setTypeface(Typeface.createFromAsset(itemView.getContext().getAssets(),"Hacen_Tunisia.ttf"));
            tv_email.setTypeface(Typeface.createFromAsset(itemView.getContext().getAssets(),"Hacen_Tunisia.ttf"));
            tv_grade.setTypeface(Typeface.createFromAsset(itemView.getContext().getAssets(),"Hacen_Tunisia.ttf"));
            tv_date.setTypeface(Typeface.createFromAsset(itemView.getContext().getAssets(),"Hacen_Tunisia.ttf"));
            tv_refuse.setTypeface(Typeface.createFromAsset(itemView.getContext().getAssets(),"Hacen_Tunisia.ttf"));
            tv_accept.setTypeface(Typeface.createFromAsset(itemView.getContext().getAssets(),"Hacen_Tunisia.ttf"));

        }




    }


}