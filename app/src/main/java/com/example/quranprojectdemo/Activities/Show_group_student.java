package com.example.quranprojectdemo.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quranprojectdemo.Other.Recycler_show_group_student;
import com.example.quranprojectdemo.Other.Student_Info;
import com.example.quranprojectdemo.R;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

public class Show_group_student extends AppCompatActivity {
    //show
    //show asd
    RecyclerView rv;
    TextView tv_show;
    Toolbar toolbar;
    ArrayList<Student_Info> arrayList;
    private SharedPreferences sp;
    String id_group;
    String id_center;
    String id_group_c;
    String id_center_c;
    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_group_student);
        sp = getSharedPreferences(TeacherLogin.INFO_TEACHER, MODE_PRIVATE);
        Realm.init(getBaseContext());
        realm = Realm.getDefaultInstance();
//        i.putExtra("id_group", arrayList.get(position).getId_group());
//        i.putExtra("id_center", arrayList.get(position).getId_center());
        Intent i = getIntent();
        id_center_c = i.getStringExtra("id_center");
        id_group_c = i.getStringExtra("id_group");
        Log.d("re", id_center_c + " ! ");
        Log.d("re", id_group_c + " ! ");
        if (id_center_c == null) {

            id_group = sp.getString(TeacherLogin.ID_LOGIN_TEACHER, "a");
            id_center = sp.getString(TeacherLogin.ID_LOGIN_TEC_CENTER, "a");
            Log.d("rere ", id_group + " !");
            Log.d("rere ", id_center + " !");
        } else {
            id_center = id_center_c;
            id_group = id_group_c;
            Log.d("re", id_group_c + " !!! ");


        }

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


    }

    @Override
    protected void onStart() {
        super.onStart();
        get_student_group();


    }

    public void get_student_group() {


        RealmResults<Student_Info> realmResults = realm.where(Student_Info.class)
                .equalTo("id_group", id_group)
                .findAll();
        arrayList.clear();

        for (int i = 0; i < realmResults.size(); i++) {

            String id_student = realmResults.get(i).getId_Student();


            String name_student = realmResults.get(i).getName();

            String id_center = realmResults.get(i).getId_center();
            String id_group = realmResults.get(i).getId_group();
            arrayList.add(new Student_Info(null, name_student, id_student, id_group, id_center));

        }

        Recycler_show_group_student recycler_show_group_student = new Recycler_show_group_student(arrayList);
        rv.setAdapter(recycler_show_group_student);
        RecyclerView.LayoutManager lm = new GridLayoutManager(getBaseContext(),2);
        rv.setLayoutManager(lm);

    }


}//جلب البيانات
//    public void get_student_group(final String id_group, final String id_center) {
//
//        FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
//        DatabaseReference reference = rootNode.getReference("CenterUsers").child(id_center)
//                .child("groups").child(id_group).child("student_group");
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                arrayList.clear();
//
//                for (DataSnapshot c : dataSnapshot.getChildren()) {
//                    String id_student = c.getKey();
//                    if (!id_student.equals("student_save")) {
//                        Student_Info studentInfo = c.child("student_info").getValue(Student_Info.class);
//
//                        String name_student = studentInfo.getName();
//
//                        try {
//                            Thread.sleep(1000);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
////                        String img_student,String name, String id_Student,String id_group,String id_cente
////                    arrayList
//                        arrayList.add(new Student_Info(null, name_student, id_student, id_group, id_center));
//                    }
//                }
//                Recycler_show_group_student recycler_show_group_student = new Recycler_show_group_student(arrayList);
//                rv.setAdapter(recycler_show_group_student);
//                RecyclerView.LayoutManager lm = new LinearLayoutManager(getBaseContext());
//                rv.setLayoutManager(lm);
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



