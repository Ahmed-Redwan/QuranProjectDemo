package com.example.quranprojectdemo.Activities;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quranprojectdemo.Other.CustomRequests;
import com.example.quranprojectdemo.Other.Group_Info;
import com.example.quranprojectdemo.Other.OnClick;
import com.example.quranprojectdemo.Other.Student_Info;
import com.example.quranprojectdemo.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.ArrayList;

import static android.Manifest.permission.CALL_PHONE;

public class JoinRequests extends AppCompatActivity {
    RecyclerView rv;
    ArrayList<Student_Info> requests;
    String Centerid;
    Button btn_ok;
    SearchableSpinner spinner;
    ArrayList<String> groupsID;
    ArrayList<String> groupsName;
    int index;
    String id_group;
    private FirebaseAuth mAuth;
    boolean isSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_requests);
        mAuth = FirebaseAuth.getInstance();

        rv = findViewById(R.id.requests_rv);
        btn_ok = findViewById(R.id.requests_btn_ok);
        checkPermission("", 1);
        spinner = findViewById(R.id.requests_searchSpinner);

        groupsName = new ArrayList<>();
        groupsID = new ArrayList<>();


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
        final Intent getIntent = getIntent();
        Centerid = getIntent.getStringExtra("CenterId");

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                requests.get(i).setGroupid(groupsID.get(i));
//                id_group = groupsID.get(i);
//                requests.get(i).setGroupid(groupsID.get(i));
                id_group = groupsID.get(i);
                isSelected = true;

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                isSelected = false;
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
                    Student_Info re = c.getValue(Student_Info.class);
                    //        Request re =c.getValue(Request.class);

       /* this.day=day;
        this.month=month;
        this.year=year;*/
//                    this.name = name;
//                    this.id_number = id_number;
//                    this.phoneNo = phoneNo;
//                    this.email = email;
//                    this.academic_level = academic_level;
//                    this.birth_date = birth_date;
//                    this.img_student = img_student;
//                    this.id_center = id_center;
//                    this.id_group = id_group;

//                    requests.add(new Student_Info(re.getName(), re.getId_number(), re.getPhoneNo(), re.getEmail(),
//                            re.getAcademic_level(), re.getBirth_date(), null, Centerid, id_group
//
//                    ));

//                    requests.add(new Request(R.drawable.mustafa, c.getValue(Request.class).getName(), c.getValue(Request.class).getId(), c.getValue(Request.class).getDate(),
//                            c.getValue(Request.class).getEmail(), c.getValue(Request.class).getGrade(), c.getValue(Request.class).getPhone()));


                }
                CustomRequests customRequests = new CustomRequests(requests, getBaseContext(), new OnClick() {
                    @Override
                    public void OnCLick(Student_Info request, int i) {

                        if (i == 1) {
                            if (isSelected){
                                sign_up(request);
                                FancyToast.makeText(getBaseContext(),"لقد تم إضافة الطالب بنجاح.",FancyToast.LENGTH_LONG,FancyToast.SUCCESS,false).show();

                            }else{
                                FancyToast.makeText(getBaseContext(),"يرجى اختيار حلقة لاضافة الطالب فيها.",FancyToast.LENGTH_LONG,FancyToast.ERROR,false).show();
                            }



                        } else {
                            Toast.makeText(JoinRequests.this, "ahmed", Toast.LENGTH_SHORT).show();
                            Toast.makeText(JoinRequests.this, request.getId_number(), Toast.LENGTH_SHORT).show();
                            FirebaseDatabase rootNode1 = FirebaseDatabase.getInstance();
                            final DatabaseReference reference1 = rootNode1.getReference("CenterUsers");

                            reference1.child(id_center).child("Requests").child(request.getId_number()).setValue(null);

                        }

                    }
                });
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


                groupsID.clear();
                groupsName.clear();
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    DataSnapshot d2 = d.child("group_info");
                    groupsID.add(d.getKey());
//                    Toast.makeText(JoinRequests.this, d.getKey(), Toast.LENGTH_SHORT).show();
//                    Toast.makeText(JoinRequests.this, d2.getValue(Group_Info.class).getPhone(), Toast.LENGTH_SHORT).show();
//                    if (d2.getValue(Group_Info.class) != null)

                    groupsName.add(d2.getValue(Group_Info.class).getGroup_name());
//                    groupsID.add()
                }

                ArrayAdapter arrayAdapter = new ArrayAdapter(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, groupsName);
                spinner.setAdapter(arrayAdapter);


            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });
    }//جلب البيانات

    private void sign_up(final Student_Info request) {
//        Toast.makeText(getBaseContext(), request.getEmail(), Toast.LENGTH_LONG).show();

        mAuth.createUserWithEmailAndPassword(request.getEmail(), request.getPhoneNo() + "123123")
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            create_new_student(user.getUid(), id_group, Centerid, request);
                            updatename(user, request);
//                            Toast.makeText(getBaseContext(), user.(), Toast.LENGTH_SHORT).show();
//                            FirebaseAuth.getInstance().signOut();

                        } else {
                            Log.w("TAG", "createUserWithEmail:failure", task.getException());

                        }

                    }
                });


    }//للتسجيل


    void updatename(FirebaseUser user, Student_Info request) {
        UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder()
                .setDisplayName(Centerid).setPhotoUri(Uri.parse(id_group))
                .build();
        user.updateProfile(profileUpdate)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                });

    }

    public void create_new_student(String name_student, String id_groub, String id_center, Student_Info request) {
        //String birth_day = request.getDay()+ "/" + request.getMonth() + "/" + request.getYear();
        FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
        DatabaseReference reference = rootNode.getReference("CenterUsers");//already found
        DatabaseReference center = reference.child(id_center);//already found
        DatabaseReference center_groups = center.child("groups");//already found or not
        DatabaseReference new_group = center_groups.child(id_groub);// add new group

        DatabaseReference student_group = new_group.child("student_group");
        DatabaseReference new_student = student_group.child(name_student);

        DatabaseReference student_info = new_student.child("student_info");
//        student_info.setValue(new Student_Info(request.getName(),
//                1 + "",
//                request.getPhoneNo(),
//                request.getEmail(), request.getAcademic_level(), request.getBirth_date()));

        FirebaseDatabase rootNode1 = FirebaseDatabase.getInstance();
        final DatabaseReference reference1 = rootNode1.getReference("CenterUsers");
        Toast.makeText(this, " 1: " + request.getId_number(), Toast.LENGTH_SHORT).show();
//int id, String name, int age, String address, String email, String phone
        reference1.child(id_center).child("Requests").child(request.getId_number()).setValue(null);


    }


}