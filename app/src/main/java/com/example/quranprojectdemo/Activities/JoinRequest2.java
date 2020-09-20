package com.example.quranprojectdemo.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quranprojectdemo.Other.Center;
import com.example.quranprojectdemo.Other.CenterUser;
import com.example.quranprojectdemo.Other.Group_Info;
import com.example.quranprojectdemo.Other.customRecyclerviewCenters;
import com.example.quranprojectdemo.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class JoinRequest2 extends AppCompatActivity {
    TextView tv_ListOfCenters;
    Button btn_Next;
    RecyclerView rv;
    //
    FirebaseAuth mAuth;
    private String country, city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_request2);
        mAuth = FirebaseAuth.getInstance();
        tv_ListOfCenters = findViewById(R.id.request2_tv_listOfCenters);
        getCenters();
//        rv = findViewById(R.id.request2_rv_listOfCenters);
//        ArrayList<Center> centers = new ArrayList<>();
//        centers.add(new Center(R.drawable.arabian, "مركز جنود الفتح القادم", "0594114029"));
//        centers.add(new Center(R.drawable.student, "مركز الياسين لتعليم القرآن الكريم", "0595565213"));
//        centers.add(new Center(R.drawable.student2, "مركز أبو بكر الصديق", "059875645656"));
//        centers.add(new Center(R.drawable.ic_masged, "مركز الحفاظ", "0594668456"));
//        centers.add(new Center(R.drawable.arabian, "مركز جنود الفتح القادم", "0595466225"));
//
//        customRecyclerviewCenters customRecyclerviewCenters = new customRecyclerviewCenters(centers, getBaseContext());
//
//        rv.setAdapter(customRecyclerviewCenters);
//        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
//        rv.setLayoutManager(layoutManager);
//        rv.setHasFixedSize(true);
//
//
//        tv_ListOfCenters.setTypeface(Typeface.createFromAsset(getAssets(), "Hacen_Tunisia_Bold.ttf"));
//

    }

    public void getCenters() {

        FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
        DatabaseReference reference = rootNode.getReference("CenterUsers");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot c : dataSnapshot.getChildren()) {
                    DataSnapshot c1=c.child("Center information");

                    for (DataSnapshot c11 : c1.getChildren()){
                    if (country.equalsIgnoreCase(c11.getValue(CenterUser.class).getCountry())
                            && city.equalsIgnoreCase(c11.getValue(CenterUser.class).getCity())
                    ) {
                        String id_center = c11.getKey();
                        String name_center = c11.getValue(CenterUser.class).getCenterName();
                        String phone_cecnter = c11.getValue(CenterUser.class).getPhone();
                        Toast.makeText(getBaseContext(), id_center + name_center + phone_cecnter, Toast.LENGTH_LONG)
                                .show();
                    }}


                }


            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
//                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

    }//جلب البيانات


}
