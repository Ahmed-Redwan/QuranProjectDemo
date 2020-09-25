package com.example.quranprojectdemo.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quranprojectdemo.Other.Recycler_student;
import com.example.quranprojectdemo.Other.Student_Info;
import com.example.quranprojectdemo.R;
import com.example.quranprojectdemo.Other.Student_data;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Main_student extends AppCompatActivity {

    Toolbar toolbar_student;
    Spinner spinner_year,spinner_month  ;

    Button btn_show_spinner;
    Button btn_disable_spinner;
    LinearLayout linearspinner;

    ImageView image_backe_student, image_student;
    TextView tv_student_name, tv_student_name_ring, tv_student_phone, tv_student_identity;
    private FirebaseAuth mAuth;
    String id_center, id_group, id_student;
    final ArrayList<Student_data> student_data = new ArrayList<>();

    ArrayList<String> list_spinner_year =new ArrayList<>();
    ArrayList<String> list_spinner_month =new ArrayList<>();
    //    FirebaseAuth  ;
    TextView tv_date, tv_day, tv_attendess;
    RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_student);
        mAuth = FirebaseAuth.getInstance();

        image_backe_student = findViewById(R.id.student_main_image_center);
        image_student = findViewById(R.id.student_main_image_student);
        tv_student_name = findViewById(R.id.student_main_tv_name_student);
        tv_student_name_ring = findViewById(R.id.student_main_tv_name_ring);
        tv_student_phone = findViewById(R.id.student_main_tv_phone);
        tv_student_identity = findViewById(R.id.student_main_tv_identity);
//


        spinner_year=findViewById(R.id.spinner_choose_date_year);
        spinner_month=findViewById(R.id.spinner_choose_date_month);

        btn_show_spinner=findViewById(R.id.btn_choose_spinner);
        btn_disable_spinner=findViewById(R.id.btn_disable_spinner);
        linearspinner=findViewById(R.id.linear_spinner);

        toolbar_student = findViewById(R.id.student_main_tool);
//        setSupportActionBar(toolbar_student);
        toolbar_student.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.MenuStudentHomeSettings:

                        return true;
                    case R.id.MenuStudentHomeAbout:
                        startActivity(new Intent(getBaseContext(), AboutApp.class));
                        return true;
                    case R.id.MenuStudentHomeExit:
                        finish();
                        return true;
                }
                return false;
            }
        });


        rv = findViewById(R.id.student_main_recycler);


      //  ArrayList<Student_data> datass = new ArrayList<>();
//
//        datass.add(new Student_data("10/9/2020", "الخميس", "حاضر", "ال عمران 5:150", "ال عمران 55:120"));
//        datass.add(new Student_data("10/9/2020", "الخميس", "حاضر", "الانسان", "المدثر"));
//        datass.add(new Student_data("10/9/2020", "الخميس", "حاضر", "الانسان5:8", "المدثر1:23"));
//        datass.add(new Student_data("10/9/2020", "الخميس", "حاضر", "الانسان", "المدثر"));
//        datass.add(new Student_data("10/9/2020", "الخميس", "حاضر", "الانسان", "المدثر"));
//        datass.add(new Student_data("10/9/2020", "الخميس", "حاضر", "الانسان", "المدثر"));
//        datass.add(new Student_data("10/9/2020", "الخميس", "حاضر", "الانسان", "المدثر"));
//        datass.add(new Student_data("10/9/2020", "الخميس", "حاضر", "الانسان", "المدثر"));


//


      /*  TextView_EditFont(tv_attendess,"Hacen_Tunisia.ttf");
        TextView_EditFont(tv_date,"Hacen_Tunisia.ttf");
        TextView_EditFont(tv_day,"Hacen_Tunisia.ttf");*/
        TextView_EditFont(tv_student_identity, "Hacen_Tunisia.ttf");
        TextView_EditFont(tv_student_name, "Hacen_Tunisia.ttf");
        TextView_EditFont(tv_student_name_ring, "Hacen_Tunisia.ttf");
        TextView_EditFont(tv_student_phone, "Hacen_Tunisia.ttf");
    }

    //change font type for textview.
    public void TextView_EditFont(TextView textView, String path) {
        textView.setTypeface(Typeface.createFromAsset(getAssets(), path));
    }

    @Override
    protected void onStart() {

        super.onStart();
        id_center = mAuth.getCurrentUser().getDisplayName();
        id_group =mAuth.getCurrentUser().getPhotoUrl().toString();
        id_student = mAuth.getCurrentUser().getUid();

        getsave_showStudent(id_center, id_group, id_student);

        btn_show_spinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                student_data.clear();
                linearspinner.setVisibility(View.VISIBLE);
                getSavesStudent(id_center, id_group, id_student);
            }
        });

        btn_disable_spinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearspinner.setVisibility(View.GONE);
                getsave_showStudent(id_center, id_group, id_student);
            }
        });
        getStudnetInfo(id_center, id_group, id_student);


    }

    public void getsave_showStudent(String id_center, String id_group, String id_student){

        FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
        final  DatabaseReference reference = rootNode.getReference("CenterUsers").child(id_center)
                .child("groups").child(id_group).child("student_group").child(id_student).child("student_save")
              ;

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Date date = new Date();
                SimpleDateFormat Foramt_date = new SimpleDateFormat("dd-MM-yyyy");
                String date_now = Foramt_date.format(date);

                SimpleDateFormat yearForamt = new SimpleDateFormat("yyyy");
                 String date_year = "Year : " + yearForamt.format(date);
                Toast.makeText(Main_student.this, date_year, Toast.LENGTH_SHORT).show();

                SimpleDateFormat monthForamt = new SimpleDateFormat("MM");
                String date_month = "Month : " + monthForamt.format(date);
                Toast.makeText(Main_student.this, date_month, Toast.LENGTH_SHORT).show();

                SimpleDateFormat dayForamt = new SimpleDateFormat("dd");
                String date_day = "Day : " + dayForamt.format(date);



                student_data.clear();
                for (DataSnapshot snapshot1 : snapshot.child(date_year).child(date_month).getChildren() ){
                    Student_data d = snapshot1.getValue(Student_data.class);
                    Toast.makeText(Main_student.this,  snapshot1.getKey(), Toast.LENGTH_SHORT).show();

                    Toast.makeText(Main_student.this, d.getDate__student(), Toast.LENGTH_SHORT).show();

                    student_data.add(d);
                }

                Recycler_student r_s = new Recycler_student(student_data);
                rv.setAdapter(r_s);
                RecyclerView.LayoutManager lm = new LinearLayoutManager(getBaseContext());
                rv.setHasFixedSize(true);
                rv.setLayoutManager(lm);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void getSavesStudent(String id_center, String id_group, String id_student) {
        FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
        final     DatabaseReference reference = rootNode.getReference("CenterUsers").child(id_center)
                .child("groups").child(id_group).child("student_group").child(id_student).child("student_save");


                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(final DataSnapshot dataSnapshot) {

//                for (DataSnapshot c : dataSnapshot.getChildren()) {
//                    for (DataSnapshot c1 : c.getChildren()) {
//                        for (DataSnapshot c3 : c1.getChildren()) {
//                            Student_data d = c3.getValue(Student_data.class);
//                            student_data.add(d);
//
//                        }
//                    }
//                }

                        list_spinner_year.clear();
                        list_spinner_year.add("اختر السنة");
                        for (DataSnapshot c : dataSnapshot.getChildren()) {
                            list_spinner_year.add(c.getKey());
//                    Toast.makeText(Main_student.this, c.getKey(), Toast.LENGTH_SHORT).show();
                        }

                        ArrayAdapter adapter_spinner_year = new ArrayAdapter<>(getBaseContext(), android.R.layout.simple_list_item_1, list_spinner_year);
                        spinner_year.setAdapter(adapter_spinner_year);


                        spinner_year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                if (position==0){

                                    Toast.makeText(Main_student.this, "من فضلك اختر السنة", Toast.LENGTH_SHORT).show();
                                }
                                else {

                                    final     String year =parent.getItemAtPosition(position).toString();
                                    Toast.makeText(Main_student.this,"السنة" +year, Toast.LENGTH_SHORT).show();

                                    list_spinner_month.clear();
                                    list_spinner_month.add("اختر الشهر");

                                    for (DataSnapshot dataSnapshot1 :dataSnapshot.child(year).getChildren()){
                                        list_spinner_month.add(dataSnapshot1.getKey());
                                        Toast.makeText(Main_student.this,dataSnapshot1.getKey(), Toast.LENGTH_SHORT).show();
                                    }

                                    if (list_spinner_month.isEmpty()){
                                        Toast.makeText(Main_student.this,"لايوجد لك اشهر في هذه السنة" +year, Toast.LENGTH_SHORT).show();
                                    }else
                                        Toast.makeText(Main_student.this,"من فضلك اختر الشهر" +year, Toast.LENGTH_SHORT).show();

                                    ArrayAdapter adapter_spinner_month = new ArrayAdapter<>(getBaseContext(), android.R.layout.simple_list_item_1, list_spinner_month);
                                    spinner_month.setAdapter(adapter_spinner_month);

                                    spinner_month.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                            String month=parent.getItemAtPosition(position).toString();
                                            Toast.makeText(Main_student.this, month, Toast.LENGTH_SHORT).show();

                                            student_data.clear();
                                            for (DataSnapshot snapshot: dataSnapshot.child(year).child(month).getChildren()){

                                                Student_data d = snapshot.getValue(Student_data.class);
                                                student_data.add(d);
                                            }

                                            Recycler_student r_s = new Recycler_student(student_data);
                                            rv.setAdapter(r_s);
                                            RecyclerView.LayoutManager lm = new LinearLayoutManager(getBaseContext());
                                            rv.setHasFixedSize(true);
                                            rv.setLayoutManager(lm);
                                        }

                                        @Override
                                        public void onNothingSelected(AdapterView<?> parent) {

                                        }
                                    });
                                }
                            }
                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {
                            }
                        });



                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
//                Log.w(TAG, "Failed to read value.", error.toException());
                    }
                });

        }


    //جلب البيانات


//    public void getSavesStudent(String id_center, String id_group, String id_student) {
//
//        FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
//        DatabaseReference reference = rootNode.getReference("CenterUsers").child(id_center)
//                .child("groups").child(id_group).child("student_group").child(id_student).child("student_save");
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                student_data.clear();
//
//                for (DataSnapshot c : dataSnapshot.getChildren()) {
//                    if (!c.getKey().equals("student_info")) {
//                        Student_data d = c.getValue(Student_data.class);
//                        Toast.makeText(getBaseContext(), d.getDate__student(), Toast.LENGTH_SHORT).show();
//                        student_data.add(d);
//                    }
//                }
//                Recycler_student r_s=new Recycler_student(student_data);
//                rv.setAdapter(r_s);
//                RecyclerView.LayoutManager lm =new LinearLayoutManager(getBaseContext());
//                rv.setHasFixedSize(true);
//                rv.setLayoutManager(lm);
//
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError error) {
//                // Failed to read value
////                Log.w(TAG, "Failed to read value.", error.toException());
//            }
//        });
//
//    }//جلب البيانات

    public void getStudnetInfo(String id_center, String id_group, String id_student) {

        FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
        DatabaseReference reference = rootNode.getReference("CenterUsers").child(id_center)
                .child("groups").child(id_group).child("student_group").child(
                        id_student).child("student_info");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Student_Info studentInfo = dataSnapshot.getValue(Student_Info.class);
                tv_student_name.setText(studentInfo.getName());
                tv_student_name_ring.setText(studentInfo.getEmail());
                tv_student_phone.setText(studentInfo.getPhoneNo());
                tv_student_identity.setText(studentInfo.getId_number() + "");
                toolbar_student.setTitle(studentInfo.getName());


            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });

    }//جلب البيانات

}


//<?xml version="1.0" encoding="utf-8"?>
//<LinearLayout
//    xmlns:android="http://schemas.android.com/apk/res/android"
//    android:layout_width="match_parent"
//    android:layout_height="match_parent"
//    xmlns:app="http://schemas.android.com/apk/res-auto"
//    android:orientation="vertical"
//    android:gravity="center">
//
//    <androidx.cardview.widget.CardView
//        android:layout_width="match_parent"
//        android:layout_height="@dimen/_50sdp"
//        app:cardElevation="@dimen/_7sdp"
//        app:cardCornerRadius="@dimen/_7sdp"
//        android:layout_marginTop="@dimen/_3sdp"
//        android:layout_marginBottom="@dimen/_3sdp"
//        app:cardBackgroundColor="#D1D4D6"
//
//        >
//        <LinearLayout
//            android:layout_width="match_parent"
//            android:layout_height="match_parent"
//            android:orientation="horizontal"
//            android:layout_gravity="center"
//            android:layout_marginEnd="@dimen/_10sdp"
//            android:layout_marginStart="@dimen/_10sdp"
//             >
//            <androidx.cardview.widget.CardView
//                android:layout_width="match_parent"
//                android:layout_height="match_parent"
//                android:layout_weight="1"
//                app:cardBackgroundColor="#D1D4D6"
//                app:cardCornerRadius="@dimen/_6sdp"
//                app:cardElevation="@dimen/_6sdp"
//
//                >
//
//                <TextView
//                    android:layout_width="match_parent"
//                    android:layout_height="match_parent"
//                    android:text="التاريخ"
//                    android:gravity="center"
//                    android:textColor="@color/colorPrimary"
//                    android:textSize="@dimen/_13ssp"
//                    android:textStyle="bold"
//
//                    android:layout_marginStart="@dimen/_3sdp"
//                    android:layout_marginEnd="@dimen/_3sdp"
//
//                    android:id="@+id/student_recycler_date"
//                    />
//
//            </androidx.cardview.widget.CardView>
//
//            <androidx.cardview.widget.CardView
//                android:layout_width="match_parent"
//                android:layout_height="match_parent"
//                android:layout_weight="1"
//                 app:cardCornerRadius="@dimen/_6sdp"
//                app:cardElevation="@dimen/_6sdp"
//                app:cardBackgroundColor="#D1D4D6"
//
//                >
//
//
//                    <TextView
//                        android:layout_width="match_parent"
//                        android:layout_height="match_parent"
//                        android:text="اليوم"
//                        android:textStyle="bold"
//                        android:gravity="center"
//                        android:textColor="@color/colorPrimary"
//                        android:textSize="@dimen/_13ssp"
//
//                        android:layout_marginStart="@dimen/_3sdp"
//                        android:layout_marginEnd="@dimen/_3sdp"
//
//                        android:id="@+id/student_recycler_day"
//                        />
//
//
//
//            </androidx.cardview.widget.CardView>
//
//            <androidx.cardview.widget.CardView
//                android:layout_width="match_parent"
//                android:layout_height="match_parent"
//                android:layout_weight="1"
//                 app:cardCornerRadius="@dimen/_6sdp"
//                app:cardElevation="@dimen/_6sdp"
//                app:cardBackgroundColor="#D1D4D6"
//
//                >
//
//                <TextView
//                    android:layout_width="match_parent"
//                    android:layout_height="match_parent"
//                    android:text="الحضور"
//                    android:gravity="center"
//                    android:textColor="@color/colorPrimary"
//                    android:textSize="@dimen/_13ssp"
//                    android:textStyle="bold"
//
//                    android:layout_marginStart="@dimen/_3sdp"
//                    android:layout_marginEnd="@dimen/_3sdp"
//
//                    android:id="@+id/student_recycler_attendess"
//                    />
//
//            </androidx.cardview.widget.CardView>
//
//            <androidx.cardview.widget.CardView
//                android:layout_width="match_parent"
//                android:layout_height="match_parent"
//                android:layout_weight="1"
//                 app:cardCornerRadius="@dimen/_6sdp"
//                app:cardElevation="@dimen/_6sdp"
//                app:cardBackgroundColor="#D1D4D6"
//
//                >
//
//                <TextView
//                    android:layout_width="match_parent"
//                    android:layout_height="match_parent"
//                    android:text="المراجعه"
//                    android:gravity="center"
//                    android:textColor="@color/colorPrimary"
//                    android:textSize="@dimen/_13ssp"
//                    android:textStyle="bold"
//                    android:layout_marginStart="@dimen/_3sdp"
//                    android:layout_marginEnd="@dimen/_3sdp"
//
//                    android:id="@+id/student_recycler_review"
//                    />
//
//            </androidx.cardview.widget.CardView>
//
//            <androidx.cardview.widget.CardView
//                android:layout_width="match_parent"
//                android:layout_height="match_parent"
//                android:layout_weight="1"
//                 app:cardCornerRadius="@dimen/_6sdp"
//                app:cardElevation="@dimen/_6sdp"
//                app:cardBackgroundColor="#D1D4D6"
//
//
//                >
//                <TextView
//                    android:layout_width="match_parent"
//                    android:layout_height="match_parent"
//                    android:text="الحفظ"
//                    android:gravity="center"
//                    android:layout_marginStart="@dimen/_3sdp"
//                    android:layout_marginEnd="@dimen/_3sdp"
//
//                    android:textColor="@color/colorPrimary"
//                    android:textSize="@dimen/_13ssp"
//                    android:textStyle="bold"
//
//
//                    android:id="@+id/student_recycler_save"
//                    />
//
//
//            </androidx.cardview.widget.CardView>
//
//
//
//
//        </LinearLayout>
//    </androidx.cardview.widget.CardView>
//
//
//
//</LinearLayout>