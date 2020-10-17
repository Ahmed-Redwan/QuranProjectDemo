package com.example.quranprojectdemo.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quranprojectdemo.Other.Center;
import com.example.quranprojectdemo.Other.CenterUser;
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
    ArrayList<Center> centers;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String country, city;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_request2);
        mAuth = FirebaseAuth.getInstance();
        tv_ListOfCenters = findViewById(R.id.request2_tv_listOfCenters);

        sharedPreferences = getSharedPreferences("Request_SP", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        progressBar = findViewById(R.id.request2_progressBar);

        progressBar.setVisibility(View.VISIBLE);


        Intent getintent = getIntent();
        country = getintent.getStringExtra("Country");
        city = getintent.getStringExtra("City");


        centers = new ArrayList<>();
        //getCenters(country, city);
        rv = findViewById(R.id.request2_rv_listOfCenters);

       /* centers.add(new Center(R.drawable.arabian, "مركز جنود الفتح القادم", "0594114029", "0"));
        centers.add(new Center(R.drawable.arabian, "مركز جنود الفتح القادم", "0594114029", "1"));
        centers.add(new Center(R.drawable.arabian, "مركز جنود الفتح القادم", "0594114029", "2"));
        centers.add(new Center(R.drawable.arabian, "مركز جنود الفتح القادم", "0594114029", "3"));
*/


        tv_ListOfCenters.setTypeface(Typeface.createFromAsset(getAssets(), "Hacen_Tunisia_Bold.ttf"));

    }

    @Override
    protected void onStart() {
        super.onStart();

        getCenters(country, city);
        progressBar.setVisibility(View.GONE);
    }

    public void getCenters(final String Country, final String City) {

        FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
        DatabaseReference reference = rootNode.getReference("Countries").child(Country).child(City);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (centers.size() == 0) {
                    for (DataSnapshot c : dataSnapshot.getChildren()) {
                        centers.add(new Center(R.drawable.ic_masged,
                                c.getValue(CenterUser.class).getCenterName(),
                                c.getValue(CenterUser.class).getPhone(), c.getKey()));


                        customRecyclerviewCenters customRecyclerviewCenters = new customRecyclerviewCenters(centers, getBaseContext());
                        rv.setAdapter(customRecyclerviewCenters);
                        //LinearLayoutManager layoutManager=new LinearLayoutManager(getBaseContext(),LinearLayoutManager.HORIZONTAL,false);
                        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getBaseContext(), 2);
                        rv.setLayoutManager(layoutManager);
                        rv.setHasFixedSize(true);

                    }

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
