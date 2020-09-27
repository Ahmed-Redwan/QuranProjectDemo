package com.example.quranprojectdemo.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.example.quranprojectdemo.Other.Center;
import com.example.quranprojectdemo.Other.CenterUser;
import com.example.quranprojectdemo.Other.CustomGroupRecyclerView;
import com.example.quranprojectdemo.Other.CustomRequests;
import com.example.quranprojectdemo.Other.Group;
import com.example.quranprojectdemo.Other.Group_Info;
import com.example.quranprojectdemo.Other.Request;
import com.example.quranprojectdemo.Other.Student_Info;
import com.example.quranprojectdemo.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.ArrayList;

import static android.Manifest.permission.CALL_PHONE;

public class JoinRequests extends AppCompatActivity {
    RecyclerView rv;
    ArrayList<Request> requests;
    String Centerid;
    Button btn_ok;
    SearchableSpinner spinner;
    ArrayList<String>groupsID;
    ArrayList<String>groupsName;
    int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_requests);

        rv = findViewById(R.id.requests_rv);
        btn_ok = findViewById(R.id.requests_btn_ok);
        checkPermission("", 1);
        spinner=findViewById(R.id.requests_searchSpinner);

        groupsName=new ArrayList<>();
        groupsID=new ArrayList<>();


        requests = new ArrayList<>();
       /* requests.add(new Request(R.drawable.mustafa,"Mustafa Muhammed Alastal","451234567","25/9/2000"
        ,"mustafa@gmail.com","third year","0597654321"));
        requests.add(new Request(R.drawable.ahmed_ali,"Ahmed Ali Alyaqubi","546879522","18/6/2000"
        ,"AhmedAli@gmail.com","third year","0591234567"));
        requests.add(new Request(R.drawable.ahmed_abd,"Ahmed Abdelghafoor","407069780","23/3/2001"
        ,"ahmedabd@gmail.com","third year","0594114029"));
        requests.add(new Request(R.drawable.mustafa,"Mustafa Muhammed Alastal","451234567","25/9/2000"
        ,"mustafa@gmail.com","third year","0597654321"));
        requests.add(new Request(R.drawable.ahmed_ali,"Ahmed Ali Alyaqubi","546879522","18/6/2000"
        ,"AhmedAli@gmail.com","third year","0591234567"));
        requests.add(new Request(R.drawable.ahmed_abd,"Ahmed Abdelghafoor","407069780","23/3/2001"
        ,"ahmedabd@gmail.com","third year","0594114029"));
       */
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        Intent getIntent = getIntent();
        Centerid = getIntent.getStringExtra("CenterId");

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    requests.get(i).setCenterid(groupsID.get(i));

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });




    }

    @Override
    protected void onStart() {
        super.onStart();
        getRequests(Centerid);
        getInRealTimeUsers();
        //setInRealTimeUsers(Centerid);
    }

    // Function to check and request permission
    public void checkPermission(String permission, int requestCode) {

        // Checking if permission is not granted
        if (ContextCompat.checkSelfPermission(JoinRequests.this, permission)
                == PackageManager.PERMISSION_DENIED) {
            ActivityCompat
                    .requestPermissions(
                            JoinRequests.this,
                            new String[]{CALL_PHONE},
                            requestCode);
        } else {
            Toast
                    .makeText(JoinRequests.this,
                            "Permission already granted",
                            Toast.LENGTH_SHORT)
                    .show();
        }
    }

    /*  public void setInRealTimeUsers(String CenterId) {
          FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
          final DatabaseReference reference = rootNode.getReference("CenterUsers");
          final DatabaseReference reference2 = rootNode.getReference();
  //int id, String name, int age, String address, String email, String phone
          reference.child(CenterId);
          try {
              Thread.sleep(2000);
          } catch (InterruptedException e) {
              e.printStackTrace();
          }
      }//اضافة بيانات
  */
    public boolean getRequests(final String id_center) {

        FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
        final DatabaseReference reference = rootNode.getReference("CenterUsers").child(id_center)
                .child("Requests");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                requests.clear();

                for (DataSnapshot c : dataSnapshot.getChildren()) {
                    Student_Info cc = c.getValue(Student_Info.class);
                    requests.add(new Request(Centerid,"",R.drawable.mustafa, cc.getName(), cc.getId_Student(), cc.getBirth_date(), cc.getEmail(), cc.getPhoneNo(),
                            cc.getAcademic_level()));
//                    requests.add(new Request(R.drawable.mustafa, c.getValue(Request.class).getName(), c.getValue(Request.class).getId(), c.getValue(Request.class).getDate(),
//                            c.getValue(Request.class).getEmail(), c.getValue(Request.class).getGrade(), c.getValue(Request.class).getPhone()));


                }
                CustomRequests customRequests = new CustomRequests(requests, getBaseContext());
                customRequests.notifyDataSetChanged();
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getBaseContext());
                rv.setAdapter(customRequests);
                rv.setLayoutManager(layoutManager);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
//                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
        return true;
    }//جلب البيانات

    public void getInRealTimeUsers() {

        FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
        final DatabaseReference reference = rootNode.getReference("CenterUsers").child(Centerid).child("groups");


        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                for (DataSnapshot d : dataSnapshot.getChildren()){
                   DataSnapshot d2=d.child("group_info");
                  groupsID.add(d.getKey());
                    Toast.makeText(JoinRequests.this, d.getKey(), Toast.LENGTH_SHORT).show();
                    Toast.makeText(JoinRequests.this, d2.getValue(Group_Info.class).getPhone(), Toast.LENGTH_SHORT).show();
                  groupsName.add(d2.getValue(Group_Info.class).getPhone());
                }

                ArrayAdapter arrayAdapter=new ArrayAdapter(getBaseContext(),android.R.layout.simple_spinner_dropdown_item,groupsName);
                spinner.setAdapter(arrayAdapter);


            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });
    }//جلب البيانات

}