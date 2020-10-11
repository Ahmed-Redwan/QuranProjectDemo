package com.example.quranprojectdemo.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quranprojectdemo.Other.CustomStudentRecyclerView2;
import com.example.quranprojectdemo.Other.Group_Info;
import com.example.quranprojectdemo.Other.Student_Info;
import com.example.quranprojectdemo.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmQuery;

import static com.example.quranprojectdemo.Activities.TeacherLogin.INFO_TEACHER;

public class Main_teacher extends AppCompatActivity {

    ImageView image_backe_teacher, image_teacher;
    TextView tv_teacher_name, tv_teacher_name_ring, tv_teacher_phone, tv_teacher_count_student;
    FirebaseAuth mAuth;
    Toolbar toolbar_teacher;
    Realm realm;
    private SharedPreferences sp;
    SharedPreferences.Editor editor;
    ArrayList<Student_Info> student_infos;
    RecyclerView recyclerView;
    CustomStudentRecyclerView2 customStudentRecyclerView2;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_teacher);
        mAuth = FirebaseAuth.getInstance();
        Realm.init(getBaseContext());
        realm = Realm.getDefaultInstance();
        //     Caused by: java.lang.NullPointerException: Attempt to invoke virtual method
//     'java.lang.String com.google.firebase.auth.FirebaseUser.getUid()' on a null object reference
        //       getInfoTeacher();
        image_backe_teacher = findViewById(R.id.teacher_main_image_center);
        /*image_teacher = findViewById(R.id.teacher_main_image_teacher);*/
        tv_teacher_name = findViewById(R.id.teacher_main_tv_name_teacher);
        tv_teacher_name_ring = findViewById(R.id.teacher_main_tv_name_ring);
        tv_teacher_phone = findViewById(R.id.teacher_main_tv_phone);
        tv_teacher_count_student = findViewById(R.id.teacher_main_tv_count_student);

        toolbar_teacher = findViewById(R.id.teacher_main_tool);
        toolbar_teacher.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.MenuTeacherHomeAddNewSave:
                        startActivity(new Intent(getBaseContext(), Add_a_new_save.class));
                        return true;
                    case R.id.MenuTeacherHomeAddStudent:
                        startActivity(new Intent(getBaseContext(), AddNewStudent.class));
                        return true;
                    case R.id.MenuTeacherHomeShowInfo:
                        startActivity(new Intent(getBaseContext(), Show_group_student.class));
                        return true;
                    case R.id.MenuTeacherHomeSettings:
                        return true;
                    case R.id.MenuTeacherHomeAbout:
                        startActivity(new Intent(getBaseContext(), AboutApp.class));
                        return true;
                    case R.id.MenuTeacherHomeExit:
                        if (!realm.isInTransaction())
                            realm.beginTransaction();
                        realm.deleteAll();
                        realm.commitTransaction();
                        realm.close();
                        sp = getSharedPreferences(INFO_TEACHER, MODE_PRIVATE);
                        editor = sp.edit();
                        editor.clear();
                        editor.commit();
                        System.exit(0);
                        return true;
                }
                return false;
            }
        });


        TextView_EditFont(tv_teacher_count_student, "Hacen_Tunisia.ttf");
        TextView_EditFont(tv_teacher_name, "Hacen_Tunisia.ttf");
        TextView_EditFont(tv_teacher_name_ring, "Hacen_Tunisia.ttf");
        TextView_EditFont(tv_teacher_phone, "Hacen_Tunisia.ttf");

        recyclerView=findViewById(R.id.mainTeacher_rv);
        student_infos=new ArrayList<>();
        student_infos.add(new Student_Info("Ahmed Abdelghsssssssssafoor","0594114029"));
        student_infos.add(new Student_Info("Ahmed Abdelghsssafoosr","0594114029"));
        student_infos.add(new Student_Info("Ahmed Abdelghafoorss","0594114029"));
        student_infos.add(new Student_Info("Ahmed Abdelghafoossssr","0594119"));
        student_infos.add(new Student_Info("Ahmed Abdelghafosssor","0594sssssss114029"));
        student_infos.add(new Student_Info("Ahmed Abdelghafoor","0sss594114029"));
        student_infos.add(new Student_Info("Ahmed Abdelghafoorsssssssssss","0594114029"));
        customStudentRecyclerView2=new CustomStudentRecyclerView2(student_infos,getBaseContext());
        layoutManager=new LinearLayoutManager(getBaseContext(),RecyclerView.HORIZONTAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(customStudentRecyclerView2);
        customStudentRecyclerView2.notifyDataSetChanged();
        recyclerView.setHasFixedSize(true);
    }

    //change font type for textview.
    public void TextView_EditFont(TextView textView, String path) {
        textView.setTypeface(Typeface.createFromAsset(getAssets(), path));
    }

    @Override
    protected void onStart() {
        super.onStart();

        getInfoTeacher();

    }

    public void getInfoTeacher() {

        RealmQuery<Group_Info> query = realm.where(Group_Info.class);
        Group_Info val = query.findFirst();

        tv_teacher_name.setText("المحفظ " + val.getTeacher_name());
        tv_teacher_phone.setText("رقم الهاتف:" + val.getPhone());
        tv_teacher_name_ring.setText("حلقة " + val.getGroup_name());
        toolbar_teacher.setTitle("حلقة " + val.getGroup_name());
        tv_teacher_count_student.setText(val.getAuto_sutdent_id());
//        RealmQuery<Student_Info> query1 = realm.where(Student_Info.class);
//
//        tv_teacher_count_student.setText("عدد طلاب الحلقة:" + query1.count());

    }


}

