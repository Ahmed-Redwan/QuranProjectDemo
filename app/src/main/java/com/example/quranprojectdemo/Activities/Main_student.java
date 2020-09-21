package com.example.quranprojectdemo.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quranprojectdemo.Other.Student_Info;
import com.example.quranprojectdemo.R;
import com.example.quranprojectdemo.Other.Student_data;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Main_student extends AppCompatActivity {

    Toolbar toolbar_student;

    ImageView image_backe_student, image_student;
    TextView tv_student_name, tv_student_name_ring, tv_student_phone, tv_student_identity;
    private FirebaseAuth mAuth;
    String id_center, id_group, id_student;
    final ArrayList<Student_data> student_data = new ArrayList<>();
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


        toolbar_student = findViewById(R.id.student_main_tool);
//        setSupportActionBar(toolbar_student);
        toolbar_student.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.MenuStudentHomeSettings:

                        return true;
                    case R.id.MenuStudentHomeAbout:

                        return true;
                    case R.id.MenuStudentHomeExit:
                        finish();
                        return true;
                }
                return false;
            }
        });


        rv = findViewById(R.id.student_main_recycler);


        ArrayList<Student_data> datass = new ArrayList<>();

        datass.add(new Student_data("10/9/2020", "الخميس", "حاضر", "ال عمران 5:150", "ال عمران 55:120"));
        datass.add(new Student_data("10/9/2020", "الخميس", "حاضر", "الانسان", "المدثر"));
        datass.add(new Student_data("10/9/2020", "الخميس", "حاضر", "الانسان5:8", "المدثر1:23"));
        datass.add(new Student_data("10/9/2020", "الخميس", "حاضر", "الانسان", "المدثر"));
        datass.add(new Student_data("10/9/2020", "الخميس", "حاضر", "الانسان", "المدثر"));
        datass.add(new Student_data("10/9/2020", "الخميس", "حاضر", "الانسان", "المدثر"));
        datass.add(new Student_data("10/9/2020", "الخميس", "حاضر", "الانسان", "المدثر"));
        datass.add(new Student_data("10/9/2020", "الخميس", "حاضر", "الانسان", "المدثر"));
//
//        for (Student_data c : datass) {
// Log.d(c.getDate__student(),c.getDay_student()+c.getAttendess_student()+c.getReview_student()+c.getSave_student());
//        }
//
//        Recycler_student r_s=new Recycler_student(datass);
//        rv.setAdapter(r_s);
//        RecyclerView.LayoutManager lm =new LinearLayoutManager(this);
//        rv.setHasFixedSize(true);
//        rv.setLayoutManager(lm);

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
        id_group =
                mAuth.getCurrentUser().getPhotoUrl().toString();
        id_student = mAuth.getCurrentUser().getUid();
        getSavesStudent(id_center, id_group, id_student);
        getStudnetInfo(id_center, id_group, id_student);
    }

    public void getSavesStudent(String id_center, String id_group, String id_student) {

        FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
        DatabaseReference reference = rootNode.getReference("CenterUsers").child(id_center)
                .child("groups").child(id_group).child("student_group").child(id_student).child("student_save");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot c : dataSnapshot.getChildren()) {
                    if (!c.getKey().equals("student_info")) {
                        Student_data d = c.getValue(Student_data.class);
                        Toast.makeText(getBaseContext(), d.getDate__student(), Toast.LENGTH_SHORT).show();
                        student_data.add(d);
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
                tv_student_identity.setText(studentInfo.getId_number()+"");


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