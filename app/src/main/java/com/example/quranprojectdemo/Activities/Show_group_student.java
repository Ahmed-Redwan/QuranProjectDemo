package com.example.quranprojectdemo.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quranprojectdemo.Other.Student_Info;
import com.example.quranprojectdemo.R;
import com.example.quranprojectdemo.Other.Recycler_show_group_student;
import com.example.quranprojectdemo.Other.Student_imageand_name;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Show_group_student extends AppCompatActivity {
    //show
    //show asd
    RecyclerView rv;
    TextView tv_show;
    Toolbar toolbar;
    private FirebaseAuth mAuth;
    ArrayList<Student_imageand_name> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_group_student);
        mAuth = FirebaseAuth.getInstance();

        toolbar = findViewById(R.id.StudentsList_ToolBar);
        rv = findViewById(R.id.recycler_show_group_student);
        tv_show = findViewById(R.id.ShowStudentsList_tv_show);
        tv_show.setTypeface(Typeface.createFromAsset(getAssets(), "Hacen_Tunisia_Bold.ttf"));

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.GroupListMenu_back) {
                    finish();
                    return true;
                }
                return false;
            }
        });
        arrayList = new ArrayList<>();

        get_student_group(mAuth.getCurrentUser().getUid(), mAuth.getCurrentUser().getDisplayName());

//        arrayList.add(new Student_imageand_name("مصطفى محمد الاسطل"));
//        arrayList.add(new Student_imageand_name("أحمد عبد الغفور"));
//        arrayList.add(new Student_imageand_name("محمد الاغا"));
//        arrayList.add(new Student_imageand_name("عبد الرحيم شراب"));
//        arrayList.add(new Student_imageand_name("أحمد اليعقوبي"));
//        arrayList.add(new Student_imageand_name("معتز ماضي"));



    }

    @Override
    protected void onStart() {
        super.onStart();

        Recycler_show_group_student recycler_show_group_student = new Recycler_show_group_student(arrayList);
        rv.setAdapter(recycler_show_group_student);
        RecyclerView.LayoutManager lm = new LinearLayoutManager(this);
        rv.setLayoutManager(lm);

    }

    public void get_student_group(String id_group, String id_center) {

        FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
        DatabaseReference reference = rootNode.getReference("CenterUsers").child(id_center)
                .child("groups").child(id_group).child("student_group");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot c : dataSnapshot.getChildren()) {
                    String id_student = c.getKey();
                    String name_student = c.getValue(Student_Info.class).getName();

                    arrayList.add(new Student_imageand_name(name_student));
//                    Toast.makeText(getBaseContext(), name_student, Toast.LENGTH_SHORT).show();

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

