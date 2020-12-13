package com.example.quranprojectdemo.Activities.mainActivity;

import android.content.Intent;
import android.content.SharedPreferences;
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

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quranprojectdemo.Activities.logIn.GuardianLogin;
import com.example.quranprojectdemo.Activities.otherActivity.AboutApp;
import com.example.quranprojectdemo.Activities.otherActivity.SplashScreen;
import com.example.quranprojectdemo.chat.MassegeActivity;
import com.example.quranprojectdemo.realm.RealmDataBaseItems;
import com.example.quranprojectdemo.recyclerView.student.Recycler_student;
import com.example.quranprojectdemo.models.students.Student_Info;
import com.example.quranprojectdemo.models.students.Student_data;
import com.example.quranprojectdemo.R;
import com.example.quranprojectdemo.Activities.registrar.RegisterAs;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import static com.example.quranprojectdemo.Activities.logIn.GuardianLogin.INFO_STUDENT_LOGIN;
import static com.example.quranprojectdemo.Activities.otherActivity.SplashScreen.CHEACKHOWISLOGGED;

public class Main_student extends AppCompatActivity {

    Toolbar toolbar_student;
    Spinner spinner_year, spinner_month;

    Button btn_show_spinner;
    Button btn_disable_spinner;
    LinearLayout linearspinner;

    ImageView image_backe_student, image_student;
    TextView tv_student_name, tv_student_name_ring, tv_student_phone, tv_student_identity;
    String id_center, id_group, id_student;
    List<Student_data> student_data = new ArrayList<>();

    ArrayList<String> list_spinner_year = new ArrayList<>();
    ArrayList<String> list_spinner_month = new ArrayList<>();

    RealmDataBaseItems dataBaseItems;
    RecyclerView rv;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_student);
        if (getSharedPreferences(CHEACKHOWISLOGGED, MODE_PRIVATE).getInt(SplashScreen.HOWISLOGGED, -1) == -1)
            getSharedPreferences(CHEACKHOWISLOGGED, MODE_PRIVATE).edit().putInt(SplashScreen.HOWISLOGGED, 2).commit();

        sp = getSharedPreferences(GuardianLogin.INFO_STUDENT_LOGIN, MODE_PRIVATE);
        id_center = sp.getString(GuardianLogin.STD_ID_CENTER, "0");
        id_group = sp.getString(GuardianLogin.STD_ID_GROUP, "-1");
        id_student = sp.getString(GuardianLogin.STD_ID_STUDENT, "-1");
        dataBaseItems = RealmDataBaseItems.getInstance(getBaseContext());
        def();
        viewFont();
        toolbar_student.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.MenuStudentChat: {

                        Intent i = new Intent(Main_student.this, MassegeActivity.class);
                        i.putExtra("key", "student");
                        i.putExtra("id_center", id_center);
                        i.putExtra("id_group", id_group);
                        i.putExtra("id_student", id_student);
                        startActivity(i);
                    }
                    return true;
                    case R.id.MenuStudentHomeSettings:
                        return true;
                    case R.id.MenuStudentHomeAbout:
                        startActivity(new Intent(getBaseContext(), AboutApp.class));
                        return true;
                    case R.id.MenuStudentHomeExit:
                        getSharedPreferences(CHEACKHOWISLOGGED, MODE_PRIVATE).edit().clear().commit();
                        getSharedPreferences(INFO_STUDENT_LOGIN, MODE_PRIVATE).edit().clear().commit();
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
        rv = findViewById(R.id.student_main_recycler);

        image_backe_student = findViewById(R.id.student_main_image_center);
        image_student = findViewById(R.id.student_main_image_student);
        tv_student_name = findViewById(R.id.student_main_tv_name_student);
        tv_student_name_ring = findViewById(R.id.student_main_tv_name_ring);
        tv_student_phone = findViewById(R.id.student_main_tv_phone);
        tv_student_identity = findViewById(R.id.student_main_tv_identity);

        spinner_year = findViewById(R.id.spinner_choose_date_year);
        spinner_month = findViewById(R.id.spinner_choose_date_month);

        btn_show_spinner = findViewById(R.id.btn_choose_spinner);
        btn_disable_spinner = findViewById(R.id.btn_disable_spinner);
        linearspinner = findViewById(R.id.linear_spinner);

        toolbar_student = findViewById(R.id.student_main_tool);
    }

    public void viewFont() {

        tv_student_identity.setTypeface(Typeface.createFromAsset(getAssets(), "Hacen_Tunisia.ttf"));
        tv_student_name.setTypeface(Typeface.createFromAsset(getAssets(), "Hacen_Tunisia.ttf"));
        tv_student_name_ring.setTypeface(Typeface.createFromAsset(getAssets(), "Hacen_Tunisia.ttf"));
        tv_student_phone.setTypeface(Typeface.createFromAsset(getAssets(), "Hacen_Tunisia.ttf"));
    }

    @Override
    protected void onStart() {

        super.onStart();


        getsave_showStudent();

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
                getsave_showStudent();
            }
        });
        getStudnetInfo();


    }

    public void getsave_showStudent() {


        Date date = new Date();

        SimpleDateFormat yearForamt = new SimpleDateFormat("yyyy");
        int date_year = Integer.parseInt(yearForamt.format(date));

        SimpleDateFormat monthForamt = new SimpleDateFormat("MM");
        int date_month = Integer.parseInt(monthForamt.format(date));
        String typeName[] = {"year_save", "month_save"};
        String value[] = {String.valueOf(date_year), String.valueOf(date_month)};
        List<Student_data> student_dataList = dataBaseItems.getDataWithAndStatement(typeName, value, Student_data.class);

        if (student_dataList != null) {
            Recycler_student r_s = new Recycler_student(student_dataList);
            rv.setAdapter(r_s);
            RecyclerView.LayoutManager lm = new LinearLayoutManager(getBaseContext());
            rv.setHasFixedSize(true);
            rv.setLayoutManager(lm);
        }

    }


    public void getSavesStudent(String id_center, String id_group, String id_student) {
        FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
        final DatabaseReference reference = rootNode.getReference("CenterUsers").child(id_center)
                .child("groups").child(id_group).child("student_group").child(id_student).child("student_save");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
                list_spinner_year.add("اختر السنة");
                String typeName[] = {};
                String value[] = {};
                long[] maxMin = dataBaseItems.getMaxAndMinAndCountValue("year_save", typeName, value, Student_data.class);
                for (int i = (int) maxMin[0]; i <= maxMin[1]; i++) {

                    list_spinner_year.add(i + "");

                }


                ArrayAdapter adapter_spinner_year = new ArrayAdapter<>(getBaseContext(), android.R.layout.simple_list_item_1, list_spinner_year);
                spinner_year.setAdapter(adapter_spinner_year);


                spinner_year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                        if (position == 0) {

                            Toast.makeText(Main_student.this, "من فضلك اختر السنة", Toast.LENGTH_SHORT).show();
                        } else {
                            final String year = (String) parent.getItemAtPosition(position);

                            String typeName[] = {"year_save"};
                            String value[] = {year};
                            long[] maxMin = dataBaseItems.getMaxAndMinAndCountValue("month_save", typeName, value, Student_data.class);
                            int max = (int) maxMin[1];
                            int min = (int) maxMin[0];

//                            int x = dataBaseItems.getMaxMinStudentData(year)[2];
                            list_spinner_month.clear();
                            list_spinner_month.add("اختر الشهر");

                            for (int i = 1; i <= max; i++) {
                                list_spinner_month.add("" + i);


                            }


                            if (list_spinner_month.isEmpty()) {
                                Toast.makeText(Main_student.this, "لايوجد لك اشهر في هذه السنة" + year, Toast.LENGTH_SHORT).show();
                            } else
                                Toast.makeText(Main_student.this, "من فضلك اختر الشهر" + year, Toast.LENGTH_SHORT).show();

                            ArrayAdapter adapter_spinner_month = new ArrayAdapter<>(getBaseContext(), android.R.layout.simple_list_item_1, list_spinner_month);
                            spinner_month.setAdapter(adapter_spinner_month);

                            spinner_month.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    int month = 0;
                                    if (parent.getItemAtPosition(position).toString().equals("اختر الشهر")) {

                                        month = 0;
                                    } else {
                                        month = Integer.parseInt(parent.getItemAtPosition(position).toString());
                                    }

                                    String typeName[] = {"year_save", "month_save"};
                                    String value[] = {String.valueOf(year), String.valueOf(month)};
                                    List<Student_data> student_dataList = dataBaseItems.getDataWithAndStatement(typeName, value, Student_data.class);

//                                    List<Student_data> student_dataList = dataBaseItems.getAllStudent_data(year, month);
                                    if (student_dataList != null) {

                                        Recycler_student r_s = new Recycler_student(student_dataList);
                                        rv.setAdapter(r_s);
                                        RecyclerView.LayoutManager lm = new LinearLayoutManager(getBaseContext());
                                        rv.setHasFixedSize(true);
                                        rv.setLayoutManager(lm);
                                    }
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

            }
        });

    }


    public void getStudnetInfo() {
        Student_Info studentInfo = (Student_Info) dataBaseItems.getAllDataFromRealm(Student_Info.class).get(0);
        if (studentInfo != null) {
            tv_student_name.setText("الطالب " + studentInfo.getName());
            tv_student_name_ring.setText("الإيميل:" + studentInfo.getEmail());
            tv_student_phone.setText(studentInfo.getPhoneNo());
            tv_student_identity.setText("رقم الهوية:" + studentInfo.getId_number());
            toolbar_student.setTitle("الطالب " + studentInfo.getName());
        }

    }

    @Override
    public void onBackPressed() {
        finishAffinity();


    }

}