package com.example.quranprojectdemo.Activities;

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

import com.example.quranprojectdemo.Other.Recycler_student;
import com.example.quranprojectdemo.Other.Student_Info;
import com.example.quranprojectdemo.Other.Student_data;
import com.example.quranprojectdemo.R;
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

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

import static com.example.quranprojectdemo.Activities.GuardianLogin.INFO_STUDENT_LOGIN;

public class Main_student extends AppCompatActivity {

    public static final String CHECK_REG_STUDENT = "student";
    public static final String CHECK_REG_STUDENT_ID = "id_student";
    Toolbar toolbar_student;
    Spinner spinner_year, spinner_month;

    Button btn_show_spinner;
    Button btn_disable_spinner;
    LinearLayout linearspinner;

    ImageView image_backe_student, image_student;
    TextView tv_student_name, tv_student_name_ring, tv_student_phone, tv_student_identity;
    private FirebaseAuth mAuth;
    String id_center, id_group, id_student;
    List<Student_data> student_data = new ArrayList<>();

    ArrayList<String> list_spinner_year = new ArrayList<>();
    ArrayList<String> list_spinner_month = new ArrayList<>();
    //    FirebaseAuth  ;
    TextView tv_date, tv_day, tv_attendess;
    RecyclerView rv;
    private Realm realm;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_student);
        mAuth = FirebaseAuth.getInstance();
        Realm.init(getBaseContext());
        realm = Realm.getDefaultInstance();
        image_backe_student = findViewById(R.id.student_main_image_center);
        image_student = findViewById(R.id.student_main_image_student);
        tv_student_name = findViewById(R.id.student_main_tv_name_student);
        tv_student_name_ring = findViewById(R.id.student_main_tv_name_ring);
        tv_student_phone = findViewById(R.id.student_main_tv_phone);
        tv_student_identity = findViewById(R.id.student_main_tv_identity);
//
        sp=getSharedPreferences(CHECK_REG_STUDENT,MODE_PRIVATE);
        editor=sp.edit();
        editor.putInt(CHECK_REG_STUDENT_ID,1);
        editor.commit();


        spinner_year = findViewById(R.id.spinner_choose_date_year);
        spinner_month = findViewById(R.id.spinner_choose_date_month);

        btn_show_spinner = findViewById(R.id.btn_choose_spinner);
        btn_disable_spinner = findViewById(R.id.btn_disable_spinner);
        linearspinner = findViewById(R.id.linear_spinner);

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


//                        sp=getSharedPreferences(Main_center.CHECK_REG_CENTER,MODE_PRIVATE);
//                        editor=sp.edit();
//                        editor.clear();
//                        sp=getSharedPreferences(Main_teacher.CHECK_REG_TEACHER,MODE_PRIVATE);
//                        editor=sp.edit();
//                        editor.clear();
                        sp=getSharedPreferences(Main_student.CHECK_REG_STUDENT,MODE_PRIVATE);
                        editor=sp.edit();
                        editor.clear();
                        editor.commit();


                        if (!realm.isInTransaction())
                            realm.beginTransaction();
                        realm.deleteAll();
                        realm.commitTransaction();
                        if (!realm.isClosed())
                            realm.close();
                        sp = getSharedPreferences(INFO_STUDENT_LOGIN, MODE_PRIVATE);
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


        rv = findViewById(R.id.student_main_recycler);





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
        id_group = mAuth.getCurrentUser().getPhotoUrl().toString();
        id_student = mAuth.getCurrentUser().getUid();

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
        String date_year = "Year : " + yearForamt.format(date);

        SimpleDateFormat monthForamt = new SimpleDateFormat("MM");
        String date_month = "Month : " + monthForamt.format(date);

        RealmQuery<Student_data> query = realm.where(Student_data.class);
        query.equalTo("year_save", date_year);
        query.and().equalTo("month_save", date_month);
        RealmResults<Student_data> realmResults = query.findAll();

        Recycler_student r_s = new Recycler_student(realmResults);
        rv.setAdapter(r_s);
        RecyclerView.LayoutManager lm = new LinearLayoutManager(getBaseContext());
        rv.setHasFixedSize(true);
        rv.setLayoutManager(lm);


    }


    public void getSavesStudent(String id_center, String id_group, String id_student) {
        FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
        final DatabaseReference reference = rootNode.getReference("CenterUsers").child(id_center)
                .child("groups").child(id_group).child("student_group").child(id_student).child("student_save");


        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
                list_spinner_year.add("اختر السنة");
                RealmQuery<Student_data> query = realm.where(Student_data.class);

                Number max = query.max("year_save");
                Number min = query.min("year_save");
                for (int i = min.intValue(); i <= max.intValue(); i++) {

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

                            final String year = parent.getItemAtPosition(position).toString();
                            Number x = realm.where(Student_data.class)
                                    .equalTo("year_save", "Year : " + year).max("month_save");
                            list_spinner_month.clear();
                            list_spinner_month.add("اختر الشهر");

                            for (int i = 1; i <= x.intValue(); i++) {
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

                                    String month = parent.getItemAtPosition(position).toString();
                                    RealmQuery<Student_data> query = realm.where(Student_data.class);
                                    query.equalTo("year_save", year);
                                    query.and().equalTo("month_save", month);
                                    RealmResults<Student_data> realmResults = query.findAll();


                                    Recycler_student r_s = new Recycler_student(realmResults);
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

            }
        });

    }


    public void getStudnetInfo() {
        RealmQuery<Student_Info> query = realm.where(Student_Info.class);
        Student_Info studentInfo = query.findFirst();


        tv_student_name.setText("الطالب " + studentInfo.getName());
        tv_student_name_ring.setText("الإيميل:" + studentInfo.getEmail());
        tv_student_phone.setText(studentInfo.getPhoneNo());
        tv_student_identity.setText("رقم الهوية:" + studentInfo.getId_number());
        toolbar_student.setTitle("الطالب " + studentInfo.getName());


    }

    @Override
    public void onBackPressed() {
        finishAffinity();


    }

}