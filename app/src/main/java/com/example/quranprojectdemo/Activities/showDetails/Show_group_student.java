package com.example.quranprojectdemo.Activities.showDetails;

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
import androidx.recyclerview.widget.RecyclerView;

import com.example.quranprojectdemo.Activities.logIn.TeacherLogin;
import com.example.quranprojectdemo.realm.RealmDataBaseItems;
import com.example.quranprojectdemo.recyclerView.student.Recycler_show_group_student;
import com.example.quranprojectdemo.models.students.Student_Info;
import com.example.quranprojectdemo.R;

import java.util.ArrayList;
import java.util.List;


public class Show_group_student extends AppCompatActivity {

    RecyclerView rv;
    TextView tv_show;
    Toolbar toolbar;
    ArrayList<Student_Info> arrayList;
    private SharedPreferences sp;
    String id_group;
    String id_center;
    String id_group_c;
    String id_center_c;
    RealmDataBaseItems dataBaseItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_group_student);
        sp = getSharedPreferences(TeacherLogin.INFO_TEACHER, MODE_PRIVATE);
        dataBaseItems = RealmDataBaseItems.getInstance(getBaseContext());
        Intent i = getIntent();
        id_center_c = i.getStringExtra("id_center");
        id_group_c = i.getStringExtra("id_group");
        if (id_center_c == null) {
            id_group = sp.getString(TeacherLogin.ID_LOGIN_TEACHER, "a");
            id_center = sp.getString(TeacherLogin.ID_LOGIN_TEC_CENTER, "a");

        } else {
            id_center = id_center_c;
            id_group = id_group_c;
        }

        def();

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

    private void def() {

        toolbar = findViewById(R.id.StudentsList_ToolBar);
        rv = findViewById(R.id.recycler_show_group_student);
        tv_show = findViewById(R.id.ShowStudentsList_tv_show);
        tv_show.setTypeface(Typeface.createFromAsset(getAssets(), "Hacen_Tunisia_Bold.ttf"));
    }

    @Override
    protected void onStart() {
        super.onStart();
        get_student_group();


    }

    public void get_student_group() {
        String typeName[] = {"id_group"};
        String value[] = {id_group};
        List<Student_Info> studentInfoList = dataBaseItems.getDataWithAndStatement(typeName, value, Student_Info.class);
        Log.d("sssss", studentInfoList.size() + "size " + id_group);
        if (studentInfoList != null) {
            arrayList.clear();

            for (int i = 0; i < studentInfoList.size(); i++) {

                String id_student = studentInfoList.get(i).getId_Student();


                String name_student = studentInfoList.get(i).getName();

                String id_center = studentInfoList.get(i).getId_center();
                String id_group = studentInfoList.get(i).getId_group();
                String tokenId = studentInfoList.get(i).getTokenId();
                arrayList.add(new Student_Info(null, name_student, id_student, id_group, id_center, tokenId));

            }

            Recycler_show_group_student recycler_show_group_student = new Recycler_show_group_student(arrayList);
            rv.setAdapter(recycler_show_group_student);
            RecyclerView.LayoutManager lm = new GridLayoutManager(getBaseContext(), 2);
            rv.setLayoutManager(lm);
        }
    }


}

