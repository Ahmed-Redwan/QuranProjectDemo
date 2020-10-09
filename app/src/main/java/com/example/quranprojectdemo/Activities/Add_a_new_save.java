package com.example.quranprojectdemo.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.quranprojectdemo.Other.Adabter_student_image_and_name;
import com.example.quranprojectdemo.Other.CheckInternet;
import com.example.quranprojectdemo.Other.Recycler_show_group_student;
import com.example.quranprojectdemo.Other.Sora;
import com.example.quranprojectdemo.Other.Student_Info;
import com.example.quranprojectdemo.Other.Student_data;
import com.example.quranprojectdemo.Other.Student_data_cash;
import com.example.quranprojectdemo.R;
import com.example.quranprojectdemo.Other.Student_imageand_name;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

//asd
public class Add_a_new_save extends AppCompatActivity {
    //maa
    SearchableSpinner spinner_saves, spinner_save_from, spinner_save_too;
    SearchableSpinner spinner_reviews, spinner_reviews_from, spinner_reviews_too;
    Spinner spinner_select_student;
    Button btn_addSave;
    private EditText et_numOfSavePages, et_numOfRevPages;
    private String id_center;
    private String id_student;
    private String id_group;
    SharedPreferences sp;
    ArrayList<Student_Info> infoArrayList;
    ArrayList<Sora> soras;
    ArrayList<String> sorasName;
    ArrayList<String> save = new ArrayList<>();
    ArrayAdapter<String> adapter_save_from;

    ArrayAdapter<String> adapter_save_to;

    String text_save;
    String text_save_from;
    String text_save_to;
    String save_all;
    Realm realm;
    String text_review;
    String text_review_from;
    String text_review_to;
    String review_all;
    CheckInternet checkInternet;
    boolean check_show_spinner;

    private void addSaveToDataBase(String date__student, String day_student,
                                   String save_student, String review_student,
                                   String attendess_student, double counnt_page_save,
                                   double counnt_page_review, String month_save,
                                   String year_save, long time_save, String id_student, String date_save, String id_group) {

        Student_data student_data = new Student_data(date__student, day_student, save_student, review_student
                , attendess_student, counnt_page_save, counnt_page_review, month_save, year_save, time_save, id_student, date_save, id_group);
        realm = Realm.getDefaultInstance();
        if (!realm.isInTransaction())
            realm.beginTransaction();
        try {
            realm.insertOrUpdate(student_data);

            realm.commitTransaction();
            realm.close();
        } catch (Exception e) {


        }
    }


    private void addSaveToCashDataBase(String date__student, String day_student,
                                       String save_student, String review_student,
                                       String attendess_student, double counnt_page_save,
                                       double counnt_page_review, String month_save,
                                       String year_save, long time_save, String id_student, String date_save) {

        Student_data_cash student_data = new Student_data_cash(date__student, day_student, save_student, review_student
                , attendess_student, counnt_page_save, counnt_page_review, month_save, year_save, time_save, id_student, date_save, id_group);
        realm = Realm.getDefaultInstance();
        if (!realm.isInTransaction())
            realm.beginTransaction();
        try {
            realm.insertOrUpdate(student_data);

            realm.commitTransaction();
            realm.close();
        } catch (Exception e) {


        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_add_a_new_save);
        Realm.init(getBaseContext());


        et_numOfSavePages = findViewById(R.id.student_add_new_save_et_numOfSavePages);
        et_numOfRevPages = findViewById(R.id.student_add_new_save_et_numOfRevPages);
        sp = getSharedPreferences(TeacherLogin.INFO_TEACHER, MODE_PRIVATE);

        id_group = sp.getString(TeacherLogin.ID_LOGIN_TEACHER, "a");
        id_center = sp.getString(TeacherLogin.ID_LOGIN_TEC_CENTER, "a");

        soras();
        soraname();


        infoArrayList = new ArrayList<>();


        spinner_select_student = findViewById(R.id.spinner_selection_student);
        btn_addSave = findViewById(R.id.student_add_new_save_btn_addSave);

        spinner_saves = findViewById(R.id.spinner_save);
        spinner_save_from = findViewById(R.id.spinner_save_from);
        spinner_save_too = findViewById(R.id.spinner_save_to);

        spinner_reviews = findViewById(R.id.spinner_review);
        spinner_reviews_from = findViewById(R.id.spinner_review_from);
        spinner_reviews_too = findViewById(R.id.spinner_review_to);


        ArrayAdapter<String> adapter_save = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, sorasName);
        spinner_saves.setAdapter(adapter_save);


        spinner_saves.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                final ArrayList<String> save_from = new ArrayList<>();
                final ArrayList<String> save_to = new ArrayList<>();
                text_save = soras.get(i).getName_sora();

                for (int j = 1; j <= soras.get(i).getNumber_auah(); j++) {
                    save_from.add(j + "");
                    save_to.add(j + "");
                }
                adapter_save_from = new ArrayAdapter<>(getBaseContext(), android.R.layout.simple_list_item_1, save_from);
                spinner_save_from.setAdapter(adapter_save_from);

                spinner_save_from.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        text_save_from = String.valueOf(save_from.get(position));
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
                adapter_save_to = new ArrayAdapter<>(getBaseContext(), android.R.layout.simple_list_item_1, save_to);
                spinner_save_too.setAdapter(adapter_save_to);
                spinner_save_too.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        text_save_to = String.valueOf(save_to.get(position));
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        final ArrayAdapter<String> adapteReview = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, sorasName);
        spinner_reviews.setAdapter(adapter_save);
        spinner_reviews.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                final ArrayList<String> review_from = new ArrayList<>();
                final ArrayList<String> review_to = new ArrayList<>();
                text_review = soras.get(i).getName_sora();
                Toast.makeText(Add_a_new_save.this, soras.get(i).getName_sora(), Toast.LENGTH_SHORT).show();

                for (int j = 1; j <= soras.get(i).getNumber_auah(); j++) {
                    review_from.add(j + "");
                    review_to.add(j + "");
                }
                ArrayAdapter adapteReviewfrom = new ArrayAdapter<>(getBaseContext(), android.R.layout.simple_list_item_1, review_from);
                spinner_reviews_from.setAdapter(adapteReviewfrom);

                spinner_reviews_from.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        text_review_from = String.valueOf(review_from.get(position));
                        Toast.makeText(Add_a_new_save.this, String.valueOf(review_from.get(position)), Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                ArrayAdapter adapter_reviews_to = new ArrayAdapter<>(getBaseContext(), android.R.layout.simple_list_item_1, review_to);
                spinner_reviews_too.setAdapter(adapter_reviews_to);

                spinner_reviews_too.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        text_review_to = String.valueOf(review_to.get(position));

                        Toast.makeText(Add_a_new_save.this, String.valueOf(review_to.get(position)), Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    public ArrayList<Student_Info> get_student_group() {
        ArrayList<Student_Info> arrayList = new ArrayList<>();
        realm = Realm.getDefaultInstance();
        if (!realm.isInTransaction())
            realm.beginTransaction();
        RealmResults<Student_Info> realmResults = realm.where(Student_Info.class).equalTo("id_group", id_group).findAll();
        for (int i = 0; i < realmResults.size(); i++) {

            String id_student = realmResults.get(i).getId_Student();


            String name_student = realmResults.get(i).getName();

            String id_center = realmResults.get(i).getId_center();
            String id_group = realmResults.get(i).getId_group();
            arrayList.add(new Student_Info(null, name_student, id_student, id_group, id_center));

        }
        realm.close();
        return arrayList;

    }


    @Override
    protected void onStart() {
        super.onStart();
        btn_addSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_numOfRevPages.getText().toString().isEmpty() || et_numOfSavePages.getText().toString().isEmpty()) {
                    et_numOfSavePages.setError("يجب إدخال عدد صفحات الحفظ.");
                    et_numOfRevPages.setError("يجب إدخال عدد صفحات المراجعة.");
                    return;
                }
                if (check_show_spinner == false) {
                    Toast.makeText(Add_a_new_save.this, "لا يمكنك اضافة حفظ وانتا لم تختر اي طالب", Toast.LENGTH_SHORT).show();
                } else {
                    insert_new_save(id_student, id_group, id_center);
//                    if (checkInternet()) {
//                        insert_new_save_fireBase(id_student, id_group, id_center);
//
//                    }


                }

            }
        });

        show_spinner();
        spinner_select_student.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(Add_a_new_save.this, infoArrayList.get(position).getName(), Toast.LENGTH_SHORT).show();
                id_student = infoArrayList.get(position).getId_Student();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    public void insert_new_save(final String id_student, String id_groub, String id_center) {

        Date date = new Date();
        SimpleDateFormat Foramt_date = new SimpleDateFormat("dd-MM-yyyy");
        String date_now = Foramt_date.format(date);

        SimpleDateFormat yearForamt = new SimpleDateFormat("yyyy");
        String date_year = "Year : " + yearForamt.format(date);

        SimpleDateFormat monthForamt = new SimpleDateFormat("MM");
        String date_month = "Month : " + monthForamt.format(date);


        save_all = "السورة  " + text_save + " من  " + text_save_from + " الى    " + text_save_to;
        review_all = "السورة  " + text_review + " من  " + text_review_from + " الى    " + text_review_to;


        addSaveToDataBase(date_now, getDay(), save_all, review_all,
                "attendess_student", Double.parseDouble(et_numOfSavePages.getText().toString()),
                Double.parseDouble(et_numOfRevPages.getText().toString()), date_month, date_year,
                date.getTime(), id_student, date_now + id_student, id_groub);
//        if (!checkInternet()) {
        addSaveToCashDataBase(date_now, getDay(), save_all, review_all,
                "attendess_student", Double.parseDouble(et_numOfSavePages.getText().toString()),
                Double.parseDouble(et_numOfRevPages.getText().toString()), date_month, date_year,
                date.getTime(), id_student, date_now + id_student);

//        }

    }

    public void insert_new_save_fireBase(final String id_student, String id_groub, String id_center) {
        FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
        DatabaseReference reference = rootNode.getReference("CenterUsers");//already found
        DatabaseReference my_center = reference.child(id_center);//already found
        DatabaseReference my_center_groups = my_center.child("groups");//already found or not
        DatabaseReference my_group = my_center_groups.child(id_groub);// add new group

        DatabaseReference my_student_group = my_group.child("student_group");
        Date date = new Date();

        long time = date.getTime();
        SimpleDateFormat Foramt_date = new SimpleDateFormat("dd-MM-yyyy");
        String date_now = Foramt_date.format(date);

        SimpleDateFormat yearForamt = new SimpleDateFormat("yyyy");
        String date_year = "Year : " + yearForamt.format(date);

        SimpleDateFormat monthForamt = new SimpleDateFormat("MM");
        String date_month = "Month : " + monthForamt.format(date);


        save_all = "السورة  " + text_save + " من  " + text_save_from + " الى      " + text_save_to;
        review_all = "السورة  " + text_review + " من  " + text_review_from + " الى    " + text_review_to;

        SimpleDateFormat dayForamt = new SimpleDateFormat("dd");
        String date_day = "Day : " + dayForamt.format(date);
        Student_data student_data = new Student_data(date_now, getDay(), save_all, review_all,
                "attendess_student", Double.parseDouble(et_numOfSavePages.getText().toString()),
                Double.parseDouble(et_numOfRevPages.getText().toString()), date_month, date_year,
                time, id_student, date_now + id_student, id_group);
        DatabaseReference student = my_student_group.child(id_student);


        DatabaseReference student_save = student.child("student_save").child(time + "");

        student_save.setValue(student_data);

//        addSaveToDataBase();

//        my_student_group.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for (DataSnapshot d : snapshot.getChildren()) {
//                    String student_id = d.getKey();
//                    String student_name = d.child("student_group").getValue(Student_Info.class).getName();
//                    infoArrayList.add(new Student_Info(student_name, student_id, null));
//
//                }
//                Adabter_student_image_and_name adabter = new Adabter_student_image_and_name(getApplicationContext(),
//                        R.layout.student_recycler_image_and_name, infoArrayList);
//
//                spinner_select_student.setAdapter(adabter);
////                spinner_select_student.setOnItemClickListener(new AdapterView.OnItemClickListener() {
////                    @Override
////                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
////
////
////                        infoArrayList.get(position);
////                    }
////                });
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

    }

    public void show_spinner() {
//

        infoArrayList.clear();

         ArrayList<Student_Info> arrayList = get_student_group();
        for (int i = 0; i < arrayList.size(); i++) {

            String student_id = arrayList.get(i).getId_Student();
            String student_name = arrayList.get(i).getName();
            infoArrayList.add(new Student_Info(student_name, student_id, null));

        }

        if (infoArrayList.isEmpty()) {
            check_show_spinner = false;
        } else {
            check_show_spinner = true;
        }

        Adabter_student_image_and_name adabter = new Adabter_student_image_and_name(getApplicationContext(),
                R.layout.student_recycler_image_and_name, infoArrayList);

        spinner_select_student.setAdapter(adabter);

    }


    private String getDay() {
        Date date = new Date();
        date.getDay();
        String day = "";
        System.out.println(date.getDay());
        switch (date.getDay()) {
            case 0:
                day = "الاحد";
                break;
            case 1:
                day = "الاثنين";
                break;
            case 2:
                day = "الثلاثاء";
                break;
            case 3:
                day = "الاربعاء";
                break;
            case 4:
                day = "الخميس";
                break;
            case 5:
                day = "الجمعة";
                break;
            case 6:
                day = "السبت";
                break;
        }
        return day;
    }

    void soras() {
        soras = new ArrayList<>();
        soras.add(new Sora("الفاتحة", 7));
        soras.add(new Sora("البقرة", 286));
        soras.add(new Sora("آل عمران", 200));
        soras.add(new Sora("النساء", 176));
        soras.add(new Sora("المائدة", 120));
        soras.add(new Sora("الأنعام", 165));
        soras.add(new Sora("الأعراف", 206));
        soras.add(new Sora("الأنفال", 75));
        soras.add(new Sora("التوبة", 129));
        soras.add(new Sora("يونس", 109));
        soras.add(new Sora("هود", 123));
        soras.add(new Sora("يوسف", 111));
        soras.add(new Sora("الرعد", 43));
        soras.add(new Sora("إبراهيم", 52));
        soras.add(new Sora("الحجر", 99));
        soras.add(new Sora("النحل", 128));
        soras.add(new Sora("الإسراء", 111));
        soras.add(new Sora("الكهف", 110));
        soras.add(new Sora("مريم", 98));
        soras.add(new Sora("طه", 135));
        soras.add(new Sora("الأنبياء", 112));
        soras.add(new Sora("الحج", 78));
        soras.add(new Sora("المؤمنون", 118));
        soras.add(new Sora("النور", 64));
        soras.add(new Sora("الفرقان", 77));
        soras.add(new Sora("الشعراء", 227));
        soras.add(new Sora("النمل", 93));
        soras.add(new Sora("القصص", 88));
        soras.add(new Sora("العنكبوت", 69));
        soras.add(new Sora("الروم", 60));
        soras.add(new Sora("لقمان", 34));
        soras.add(new Sora("السجدة", 30));
        soras.add(new Sora("الأحزاب", 73));
        soras.add(new Sora("سبأ", 54));
        soras.add(new Sora("فاطر", 45));
        soras.add(new Sora("يس", 83));
        soras.add(new Sora("الصافات", 182));
        soras.add(new Sora("ص", 88));
        soras.add(new Sora("الزمر", 75));
        soras.add(new Sora("غافر", 85));
        soras.add(new Sora("فصلت", 54));
        soras.add(new Sora("الشورى", 53));
        soras.add(new Sora("الزخرف", 89));
        soras.add(new Sora("الدخان", 59));
        soras.add(new Sora("الجاثية", 37));
        soras.add(new Sora("الأحقاف", 35));
        soras.add(new Sora("محمد", 38));
        soras.add(new Sora("الفتح", 29));
        soras.add(new Sora("الحجرات", 18));
        soras.add(new Sora("ق", 45));
        soras.add(new Sora("الذاريات", 60));
        soras.add(new Sora("الطور", 49));
        soras.add(new Sora("النحم", 62));
        soras.add(new Sora("القمر", 55));
        soras.add(new Sora("الرحمن", 78));
        soras.add(new Sora("الواقعة", 96));
        soras.add(new Sora("الحديد", 29));
        soras.add(new Sora("المجادلة", 22));
        soras.add(new Sora("الحشر", 24));
        soras.add(new Sora("الممتحنة", 13));
        soras.add(new Sora("الصف", 14));
        soras.add(new Sora("الجمعة", 11));
        soras.add(new Sora("المنافقون", 11));
        soras.add(new Sora("التغابن", 18));
        soras.add(new Sora("الطلاق", 12));
        soras.add(new Sora("التحريم", 12));
        soras.add(new Sora("الملك", 30));
        soras.add(new Sora("القلم", 52));
        soras.add(new Sora("الحاقة", 52));
        soras.add(new Sora("المعارج", 44));
        soras.add(new Sora("نوح", 28));
        soras.add(new Sora("الجن", 28));
        soras.add(new Sora("المزمل", 20));
        soras.add(new Sora("المدثر", 56));
        soras.add(new Sora("القيامة", 40));
        soras.add(new Sora("الإنسان", 31));
        soras.add(new Sora("المرسلات", 50));
        soras.add(new Sora("النبأ", 40));
        soras.add(new Sora("النازعات", 46));
        soras.add(new Sora("عبس", 42));
        soras.add(new Sora("التكوير", 29));
        soras.add(new Sora("الانفطار", 19));
        soras.add(new Sora("المطففين", 36));
        soras.add(new Sora("الانشقاق", 25));
        soras.add(new Sora("البروج", 22));
        soras.add(new Sora("الطارق", 17));
        soras.add(new Sora("الأعلى", 19));
        soras.add(new Sora("الغاشية", 26));
        soras.add(new Sora("الفجر", 30));
        soras.add(new Sora("البلد", 20));
        soras.add(new Sora("الشمس", 15));
        soras.add(new Sora("الليل", 21));
        soras.add(new Sora("الضحى", 11));
        soras.add(new Sora("الشرح", 8));
        soras.add(new Sora("التين", 8));
        soras.add(new Sora("العلق", 19));
        soras.add(new Sora("القدر", 5));
        soras.add(new Sora("البينة", 8));
        soras.add(new Sora("الزلزلة", 8));
        soras.add(new Sora("العاديات", 11));
        soras.add(new Sora("القارعة", 11));
        soras.add(new Sora("التكاثر", 8));
        soras.add(new Sora("العصر", 3));
        soras.add(new Sora("الهمزة", 9));
        soras.add(new Sora("الفيل", 5));
        soras.add(new Sora("قريش", 4));
        soras.add(new Sora("الماعون", 7));
        soras.add(new Sora("الكوثر", 3));
        soras.add(new Sora("الكافرون", 6));
        soras.add(new Sora("النصر", 3));
        soras.add(new Sora("المسد", 5));
        soras.add(new Sora("الإخلاص", 4));
        soras.add(new Sora("الفلق", 5));
        soras.add(new Sora("الناس", 6));


    }

    private void soraname() {
        sorasName = new ArrayList<>();
        sorasName.add("الفاتحة");
        sorasName.add("البقرة");
        sorasName.add("آل عمران");
        sorasName.add("النساء");
        sorasName.add("المائدة");
        sorasName.add("الأنعام");
        sorasName.add("الأعراف");
        sorasName.add("الأنفال");
        sorasName.add("التوبة");
        sorasName.add("يونس");
        sorasName.add("هود");
        sorasName.add("يوسف");
        sorasName.add("الرعد");
        sorasName.add("إبراهيم");
        sorasName.add("الحجر");
        sorasName.add("النحل");
        sorasName.add("الإسراء");
        sorasName.add("الكهف");
        sorasName.add("مريم");
        sorasName.add("طه");
        sorasName.add("الأنبياء");
        sorasName.add("الحج");
        sorasName.add("المؤمنون");
        sorasName.add("النور");
        sorasName.add("الفرقان");
        sorasName.add("الشعراء");
        sorasName.add("النمل");
        sorasName.add("القصص");
        sorasName.add("العنكبوت");
        sorasName.add("الروم");
        sorasName.add("لقمان");
        sorasName.add("السجدة");
        sorasName.add("الأحزاب");
        sorasName.add("سبأ");
        sorasName.add("فاطر");
        sorasName.add("يس");
        sorasName.add("الصافات");
        sorasName.add("ص");
        sorasName.add("الزمر");
        sorasName.add("غافر");
        sorasName.add("فصلت");
        sorasName.add("الشورى");
        sorasName.add("الزخرف");
        sorasName.add("الدخان");
        sorasName.add("الجاثية");
        sorasName.add("الأحقاف");
        sorasName.add("محمد");
        sorasName.add("الفتح");
        sorasName.add("الحجرات");
        sorasName.add("ق");
        sorasName.add("الذاريات");
        sorasName.add("الطور");
        sorasName.add("النحم");
        sorasName.add("القمر");
        sorasName.add("الرحمن");
        sorasName.add("الواقعة");
        sorasName.add("الحديد");
        sorasName.add("المجادلة");
        sorasName.add("الحشر");
        sorasName.add("الممتحنة");
        sorasName.add("الصف");
        sorasName.add("الجمعة");
        sorasName.add("المنافقون");
        sorasName.add("التغابن");
        sorasName.add("الطلاق");
        sorasName.add("التحريم");
        sorasName.add("الملك");
        sorasName.add("القلم");
        sorasName.add("الحاقة");
        sorasName.add("المعارج");
        sorasName.add("نوح");
        sorasName.add("الجن");
        sorasName.add("المزمل");
        sorasName.add("المدثر");
        sorasName.add("القيامة");
        sorasName.add("الإنسان");
        sorasName.add("المرسلات");
        sorasName.add("النبأ");
        sorasName.add("النازعات");
        sorasName.add("عبس");
        sorasName.add("التكوير");
        sorasName.add("الانفطار");
        sorasName.add("المطففين");
        sorasName.add("الانشقاق");
        sorasName.add("البروج");
        sorasName.add("الطارق");
        sorasName.add("الأعلى");
        sorasName.add("الغاشية");
        sorasName.add("الفجر");
        sorasName.add("البلد");
        sorasName.add("الشمس");
        sorasName.add("الليل");
        sorasName.add("الضحى");
        sorasName.add("الشرح");
        sorasName.add("التين");
        sorasName.add("العلق");
        sorasName.add("القدر");
        sorasName.add("البينة");
        sorasName.add("الزلزلة");
        sorasName.add("العاديات");
        sorasName.add("القارعة");
        sorasName.add("التكاثر");
        sorasName.add("العصر");
        sorasName.add("الهمزة");
        sorasName.add("الفيل");
        sorasName.add("قريش");
        sorasName.add("الماعون");
        sorasName.add("الكوثر");
        sorasName.add("الكافرون");
        sorasName.add("النصر");
        sorasName.add("المسد");
        sorasName.add("الإخلاص");
        sorasName.add("الفلق");
        sorasName.add("الناس");

    }

    private boolean checkInternet() {
        checkInternet = new CheckInternet();
        if (checkInternet.isConnected(this)) {
            return true;
        }
        return false;
    }
}