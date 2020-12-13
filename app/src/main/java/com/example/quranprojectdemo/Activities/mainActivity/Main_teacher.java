package com.example.quranprojectdemo.Activities.mainActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quranprojectdemo.Activities.otherActivity.AboutApp;
import com.example.quranprojectdemo.Activities.otherActivity.Reprts;
import com.example.quranprojectdemo.Activities.otherActivity.SplashScreen;
import com.example.quranprojectdemo.Activities.showDetails.Show_group_student;
import com.example.quranprojectdemo.Activities.logIn.TeacherLogin;
import com.example.quranprojectdemo.Activities.registrar.AddNewStudent;
import com.example.quranprojectdemo.Activities.registrar.Add_a_new_save;
import com.example.quranprojectdemo.chat.Show_student;
import com.example.quranprojectdemo.realm.RealmDataBaseItems;
import com.example.quranprojectdemo.recyclerView.student.CustomStudentRecyclerView2;
import com.example.quranprojectdemo.models.groups.Group_Info;
import com.example.quranprojectdemo.models.students.Student_Info;
import com.example.quranprojectdemo.R;
import com.example.quranprojectdemo.Activities.registrar.RegisterAs;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import static com.example.quranprojectdemo.Activities.otherActivity.SplashScreen.CHEACKHOWISLOGGED;

public class Main_teacher extends AppCompatActivity {


    ImageView image_backe_teacher;
    TextView tv_teacher_name, tv_teacher_name_ring, tv_teacher_phone, tv_teacher_count_student;
    FirebaseAuth mAuth;
    Toolbar toolbar_teacher;
    ArrayList<Student_Info> student_infos;
    RecyclerView recyclerView;
    CustomStudentRecyclerView2 customStudentRecyclerView2;
    RecyclerView.LayoutManager layoutManager;
    RealmDataBaseItems dataBaseItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_teacher);
        if (getSharedPreferences(CHEACKHOWISLOGGED, MODE_PRIVATE).getInt(SplashScreen.HOWISLOGGED, -1) == -1)
            getSharedPreferences(CHEACKHOWISLOGGED, MODE_PRIVATE).edit().putInt(SplashScreen.HOWISLOGGED, 1).commit();
        mAuth = FirebaseAuth.getInstance();
        dataBaseItems = RealmDataBaseItems.getInstance(getBaseContext());


        def();
        viewFont();

        toolbar_teacher.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.MenuTeacherChat:
                        startActivity(new Intent(getBaseContext(), Show_student.class));
                        return true;
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
                        startActivity(new Intent(getBaseContext(), Reprts.class));
                        return true;
                    case R.id.MenuTeacherHomeExit:

                        getSharedPreferences(CHEACKHOWISLOGGED, MODE_PRIVATE).edit().clear().commit();

                        getSharedPreferences(TeacherLogin.INFO_TEACHER, MODE_PRIVATE).edit().clear().commit();
                        dataBaseItems.deleteAllData();

                        finish();
                        startActivity(new Intent(getBaseContext(), RegisterAs.class));

                        return true;
                }
                return false;
            }
        });


    }

    private void def() {


        image_backe_teacher = findViewById(R.id.teacher_main_image_center);
        tv_teacher_name = findViewById(R.id.teacher_main_tv_name_teacher);
        tv_teacher_name_ring = findViewById(R.id.teacher_main_tv_name_ring);
        tv_teacher_phone = findViewById(R.id.teacher_main_tv_phone);
        tv_teacher_count_student = findViewById(R.id.teacher_main_tv_count_student);
        recyclerView = findViewById(R.id.mainTeacher_rv);

        toolbar_teacher = findViewById(R.id.teacher_main_tool);
    }

    public void viewFont() {

        tv_teacher_count_student.setTypeface(Typeface.createFromAsset(getAssets(), "Hacen_Tunisia.ttf"));
        tv_teacher_name.setTypeface(Typeface.createFromAsset(getAssets(), "Hacen_Tunisia.ttf"));
        tv_teacher_name_ring.setTypeface(Typeface.createFromAsset(getAssets(), "Hacen_Tunisia.ttf"));
        tv_teacher_phone.setTypeface(Typeface.createFromAsset(getAssets(), "Hacen_Tunisia.ttf"));
    }

    @Override
    protected void onStart() {
        super.onStart();

        getInfoTeacher();

    }

    public void getInfoTeacher() {

        if (!dataBaseItems.getAllDataFromRealm(Group_Info.class).isEmpty()) {
            Group_Info val = (Group_Info) dataBaseItems.getAllDataFromRealm(Group_Info.class).get(0);
            if (val != null) {

                tv_teacher_name.setText("المحفظ " + val.getTeacher_name());
                tv_teacher_phone.setText("رقم الهاتف:" + val.getPhone());
                tv_teacher_name_ring.setText("حلقة " + val.getGroup_name());
                toolbar_teacher.setTitle("حلقة " + val.getGroup_name());
                tv_teacher_count_student.setText(val.getAuto_sutdent_id());

            }
        }
    }

    @Override
    public void onBackPressed() {
        finishAffinity();


    }

    public void get_student_group() {

        student_infos = new ArrayList<>();

        //id_group
        String typeName[] = {"id_group"};
        String value[] = {getSharedPreferences(TeacherLogin.INFO_TEACHER, MODE_PRIVATE).getString(TeacherLogin.ID_LOGIN_TEACHER, "1")};
        List<Student_Info> studentInfos = dataBaseItems.getDataWithAndStatement(typeName, value, Student_Info.class);
        if (studentInfos != null) {
            student_infos.clear();

            for (int i = 0; i < studentInfos.size(); i++) {

                String id_student = studentInfos.get(i).getId_Student();


                String name_student = studentInfos.get(i).getName();

                String id_center = studentInfos.get(i).getId_center();
                String id_group = studentInfos.get(i).getId_group();
                String tokenId=studentInfos.get(i).getTokenId();
                student_infos.add(new Student_Info(null, name_student, id_student, id_group, id_center,tokenId));

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

