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

import com.example.quranprojectdemo.Activities.realm.RealmDataBaseItems;
import com.example.quranprojectdemo.Other.CustomStudentRecyclerView2;
import com.example.quranprojectdemo.Other.Group;
import com.example.quranprojectdemo.Other.Group_Info;
import com.example.quranprojectdemo.Other.Recycler_show_group_student;
import com.example.quranprojectdemo.Other.Student_Info;
import com.example.quranprojectdemo.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;


import static com.example.quranprojectdemo.Activities.TeacherLogin.INFO_TEACHER;

public class Main_teacher extends AppCompatActivity {


    public static final String CHECK_REG_TEACHER = "teacher_check";
    public static final String CHECK_REG_TEACHER_ID = "id_teacher_check";

    ImageView image_backe_teacher;
    TextView tv_teacher_name, tv_teacher_name_ring, tv_teacher_phone, tv_teacher_count_student;
    FirebaseAuth mAuth;
    Toolbar toolbar_teacher;
    private SharedPreferences sp;
    SharedPreferences.Editor editor;
    ArrayList<Student_Info> student_infos;
    RecyclerView recyclerView;
    CustomStudentRecyclerView2 customStudentRecyclerView2;
    RecyclerView.LayoutManager layoutManager;
    RealmDataBaseItems dataBaseItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_teacher);
        mAuth = FirebaseAuth.getInstance();
        dataBaseItems = RealmDataBaseItems.getinstance(getBaseContext());
        sp = getSharedPreferences(CHECK_REG_TEACHER, MODE_PRIVATE);
        editor = sp.edit();
        editor.putInt(CHECK_REG_TEACHER_ID, 1);
        editor.commit();


        image_backe_teacher = findViewById(R.id.teacher_main_image_center);
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

                        sp = getSharedPreferences(Main_teacher.CHECK_REG_TEACHER, MODE_PRIVATE);
                        editor = sp.edit();
                        editor.clear();


                        dataBaseItems.deleteAllData();
                        sp = getSharedPreferences(INFO_TEACHER, MODE_PRIVATE);
                        editor = sp.edit();
                        editor.clear();
                        editor.commit();
                        finish();
                        startActivity(new Intent(getBaseContext(), RegisterAs.class));

                        return true;
                }
                return false;
            }
        });


        TextView_EditFont(tv_teacher_count_student, "Hacen_Tunisia.ttf");
        TextView_EditFont(tv_teacher_name, "Hacen_Tunisia.ttf");
        TextView_EditFont(tv_teacher_name_ring, "Hacen_Tunisia.ttf");
        TextView_EditFont(tv_teacher_phone, "Hacen_Tunisia.ttf");

        recyclerView = findViewById(R.id.mainTeacher_rv);


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

        Group_Info val = (Group_Info) dataBaseItems.getAllDataFromRealm(Group_Info.class).get(0);
        if (val != null) {

            tv_teacher_name.setText("المحفظ " + val.getTeacher_name());
            tv_teacher_phone.setText("رقم الهاتف:" + val.getPhone());
            tv_teacher_name_ring.setText("حلقة " + val.getGroup_name());
            toolbar_teacher.setTitle("حلقة " + val.getGroup_name());
            tv_teacher_count_student.setText(val.getAuto_sutdent_id());
//        RealmQuery<Student_Info> query1 = realm.where(Student_Info.class);
//            finishAffinity();


        }
    }

    @Override
    public void onBackPressed() {
        finishAffinity();


    }

    public void get_student_group() {
        sp = getSharedPreferences(INFO_TEACHER, MODE_PRIVATE);

        student_infos = new ArrayList<>();

        //id_group
        String typeName[] = {"id_group"};
        String value[] = {sp.getString(TeacherLogin.ID_LOGIN_TEACHER, "1")};
        List<Student_Info> studentInfos = dataBaseItems.getDataWithAndStatement(typeName, value, Student_Info.class);
        if (studentInfos != null) {
            student_infos.clear();

            for (int i = 0; i < studentInfos.size(); i++) {

                String id_student = studentInfos.get(i).getId_Student();


                String name_student = studentInfos.get(i).getName();

                String id_center = studentInfos.get(i).getId_center();
                String id_group = studentInfos.get(i).getId_group();
                student_infos.add(new Student_Info(null, name_student, id_student, id_group, id_center));

            }

            customStudentRecyclerView2 = new CustomStudentRecyclerView2(student_infos, getBaseContext());
            layoutManager = new LinearLayoutManager(getBaseContext(), RecyclerView.HORIZONTAL, false);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(customStudentRecyclerView2);
            customStudentRecyclerView2.notifyDataSetChanged();
            recyclerView.setHasFixedSize(true);
        }
    }

}

