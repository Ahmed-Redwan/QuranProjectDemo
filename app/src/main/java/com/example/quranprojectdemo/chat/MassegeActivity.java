package com.example.quranprojectdemo.chat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import com.example.quranprojectdemo.R;
import com.example.quranprojectdemo.fireBase.PushNotification;
import com.example.quranprojectdemo.realm.RealmDataBaseItems;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.PushbackInputStream;
import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class MassegeActivity extends AppCompatActivity {

    Toolbar toolbar;
    CircleImageView profile_image;
    TextView user_name;
    EditText send_massege;
    ImageButton btn_send;
    RecyclerView recyclerMassege;
    String studentToken;
    String id_center, id_group, id_student;
    String id_center_c, id_group_c, id_student_c;
    private SharedPreferences sp;
    String key;
    DatabaseReference reference1;
    ValueEventListener seenListener;


    ArrayList<Chat> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_massege);

        toolbar = findViewById(R.id.massege_tool);
        profile_image = findViewById(R.id.massege_image);
        user_name = findViewById(R.id.massege_tv_username);
        recyclerMassege = findViewById(R.id.recycler_massege);
        send_massege = findViewById(R.id.et_massege);
        btn_send = findViewById(R.id.icon_image);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        {
            recyclerMassege.setHasFixedSize(true);
            LinearLayoutManager layoutManager = new LinearLayoutManager(MassegeActivity.this);
            layoutManager.setStackFromEnd(true);
            recyclerMassege.setLayoutManager(layoutManager);
        }

        sp = getSharedPreferences(com.example.quranprojectdemo.Activities.logIn.TeacherLogin.INFO_TEACHER, MODE_PRIVATE);


        Intent i = getIntent();
        id_group = sp.getString(com.example.quranprojectdemo.Activities.logIn.TeacherLogin.ID_LOGIN_TEACHER, "a");
        id_center = sp.getString(com.example.quranprojectdemo.Activities.logIn.TeacherLogin.ID_LOGIN_TEC_CENTER, "a");
        key = i.getStringExtra("key");
        if (key.equals("teacher")) {

            id_student_c = i.getStringExtra("id_student");
            id_group_c = i.getStringExtra("id_group");
            id_center_c = i.getStringExtra("id_center");
            studentToken = i.getStringExtra("id_token");
            if (id_student_c != null) {
                id_group = id_group_c;
                id_center = id_center_c;
                id_student = id_student_c;
            }
        } else {
            id_student = i.getStringExtra("id_student");
            id_group = i.getStringExtra("id_group");
            id_center = i.getStringExtra("id_center");
            Log.d("ccccc", id_student + "g" + id_group + "c" + id_center);

            if (id_student_c != null) {
                id_group = id_group_c;
                id_center = id_center_c;
                id_student = id_student_c;
                Log.d("ccccc", id_student + "g" + id_group + "c" + id_center);
            }
        }


        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (!send_massege.getText().toString().equals("")) {
                    if (key.equals("teacher")) {
                        sendMassege(user.getUid(), id_student, send_massege.getText().toString());
                        PushNotification.sendNotification(studentToken,"رسالة جديدة",send_massege.getText().toString());

                    } else {
                        sendMassege(user.getUid(), id_group, send_massege.getText().toString());
                    }

                } else {
                    Toast.makeText(MassegeActivity.this, "You can't send empty massege", Toast.LENGTH_SHORT).show();
                }
                send_massege.setText("");
            }
        });
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (key.equals("teacher")) {
             seenMassege(user.getUid());
        } else {
            seenMassege(user.getUid());
        }

        showMassege();


    }

    private void sendMassege(String sender, String reciver, String massege) {


        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("CenterUsers").child(id_center)
                .child("groups").child(id_group).child("student_group").child(id_student);
        Chat chats = new Chat(sender, reciver, massege, false);
        reference.child("chats").push().setValue(chats);
    }

    //                            final String my_id ,final String user_id ,final  String image
    public void showMassege() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("CenterUsers").child(id_center)
                .child("groups").child(id_group).child("student_group").child(id_student).child("chats");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList = new ArrayList<>();
                arrayList.clear();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Chat chats = dataSnapshot.getValue(Chat.class);

                    //                    if (chats.getSender().equals(my_id)&&chats.getReciver().equals(user_id) ||
                    //                           chats.getSender().equals(user_id)&&chats.getReciver().equals(my_id))
                    arrayList.add(chats);

                }
                RecyclerMassege adapter = new RecyclerMassege(MassegeActivity.this, arrayList);
                recyclerMassege.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    public void seenMassege(final String uid) {

        reference1 = FirebaseDatabase.getInstance().getReference("CenterUsers").child(id_center)
                .child("groups").child(id_group).child("student_group").child(id_student).child("chats");
        seenListener = reference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Chat chats = dataSnapshot.getValue(Chat.class);

                    if (chats.getSender().equals(uid)) {

                    } else {
                        //                        Toast.makeText(MassegeActivity.this, "massege:"+chats.getMassege(), Toast.LENGTH_SHORT).show();
                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put("seen", true);

                        dataSnapshot.getRef().updateChildren(hashMap);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }


    @Override
    protected void onPause() {
        super.onPause();
        reference1.removeEventListener(seenListener);
        Toast.makeText(this, "pause", Toast.LENGTH_SHORT).show();
    }
}