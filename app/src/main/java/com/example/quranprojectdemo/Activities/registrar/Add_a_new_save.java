package com.example.quranprojectdemo.Activities.registrar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quranprojectdemo.Activities.logIn.TeacherLogin;
import com.example.quranprojectdemo.Activities.mainActivity.Main_teacher;
import com.example.quranprojectdemo.fireBase.SetStudentData;
import com.example.quranprojectdemo.realm.RealmDataBaseItems;
import com.example.quranprojectdemo.recyclerView.student.Adabter_student_image_and_name;
import com.example.quranprojectdemo.models.CheckInternet;
import com.example.quranprojectdemo.models.otherModels.Report;
import com.example.quranprojectdemo.models.otherModels.Sora;
import com.example.quranprojectdemo.models.students.Student_Info;
import com.example.quranprojectdemo.models.students.Student_data;
import com.example.quranprojectdemo.models.students.Student_data_cash;
import com.example.quranprojectdemo.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;


public class Add_a_new_save extends AppCompatActivity {
    private SearchableSpinner spinner_saves, spinner_save_from, spinner_save_too, spinner_reviews, spinner_reviews_from, spinner_reviews_too;
    private Spinner spinner_select_student;
    private Button btn_addSave, btn_addAbcens;
    private EditText et_numOfSavePages, et_numOfRevPages;
    private String id_center, text_save, review_all, text_review_to, text_review_from, text_review, save_all, text_save_to, text_save_from;
    private String id_student;
    private int numSavePages = 0, numRevPages = 0;
    private String id_group;
    private SetStudentData setStudentData;
    private RealmDataBaseItems dataBaseItems;
    private SharedPreferences sp;
    private ArrayList<Student_Info> infoArrayList = new ArrayList<>();
    private ArrayList<Sora> soras;
    private ArrayList<String> sorasName;
    private ArrayAdapter<String> adapter_save_from, adapter_save_to;
    private boolean check_show_spinner;
    private CheckInternet checkInternet;
    private boolean isAbcens;
    private String token;
    private String isAttetud;

    private void uploadAndSave(String id_groub) {

        Date date = new Date();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat Foramt_date = new SimpleDateFormat("dd-MM-yyyy");
        String date_now = Foramt_date.format(date);

        @SuppressLint("SimpleDateFormat") SimpleDateFormat Foramt_date_time = new SimpleDateFormat("ddMMyyyy");
        String date_now_t = Foramt_date_time.format(date);
        int tt = Integer.parseInt(date_now_t);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat yearForamt = new SimpleDateFormat("yyyy");
        int date_year = Integer.parseInt(yearForamt.format(date));

        @SuppressLint("SimpleDateFormat") SimpleDateFormat monthForamt = new SimpleDateFormat("MM");
        int date_month = Integer.parseInt(monthForamt.format(date));


        String uIdDate = id_student + "." + date_now;
        Log.d("sssss", uIdDate);
        Student_data student_data = new Student_data(date_now, getDay(), save_all, review_all,
                isAttetud, numSavePages,
                numRevPages, String.valueOf(date_month), String.valueOf(date_year),
                tt, id_student, (uIdDate), id_groub);

        Student_data_cash student_data_cash = new Student_data_cash(date_now, getDay(), save_all, review_all,
                isAttetud, numSavePages,
                numRevPages, String.valueOf(date_month), String.valueOf(date_year),
                tt, id_student, (uIdDate), id_group);

        dataBaseItems.insertObjectToDataToRealm(student_data, Student_data.class);
        if (checkInternet()) {
            setStudentData.uploadOneNewSave(student_data_cash, token);


        } else {
            dataBaseItems.insertObjectToDataToRealm(student_data_cash, Student_data_cash.class);

        }


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_add_a_new_save);
        dataBaseItems = RealmDataBaseItems.getInstance(getBaseContext());
        setStudentData = SetStudentData.getinstance(this);
        sp = getSharedPreferences(TeacherLogin.INFO_TEACHER, MODE_PRIVATE);
        id_group = sp.getString(TeacherLogin.ID_LOGIN_TEACHER, "a");
        id_center = sp.getString(TeacherLogin.ID_LOGIN_TEC_CENTER, "a");
        def();
        soras();
        soraname();

        setSaveAndrev();
        btn_addAbcens.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!check_show_spinner) {
                    Toast.makeText(Add_a_new_save.this, "لا يمكنك اضافة غياب وانتا لم تختر اي طالب", Toast.LENGTH_SHORT).show();
                } else {
                    save_all = "غائب";
                    review_all = "غائب";
                    isAttetud = "غائب";
                    numRevPages = 0;
                    numSavePages = 0;

                    uploadAndSave(id_group);
                    Toast.makeText(Add_a_new_save.this, "تم تسجيل غياب بنجاح", Toast.LENGTH_SHORT).show();
                    finish();
                    startActivity(new Intent(getBaseContext(), Main_teacher.class));

                }

            }
        });
        btn_addSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_numOfRevPages.getText().toString().isEmpty() || et_numOfSavePages.getText().toString().isEmpty()) {
                    et_numOfSavePages.setError("يجب إدخال عدد صفحات الحفظ.");
                    et_numOfRevPages.setError("يجب إدخال عدد صفحات المراجعة.");
                    return;
                }
                if (!check_show_spinner) {
                    Toast.makeText(Add_a_new_save.this, "لا يمكنك اضافة حفظ وانتا لم تختر اي طالب", Toast.LENGTH_SHORT).show();
                } else {

                    save_all = " سورة: " + text_save + " من " + text_save_from + " إلى " + text_save_to;
                    review_all = " سورة: " + text_review + " من " + text_review_from + " إلى " + text_review_to;
                    numSavePages = Integer.parseInt(et_numOfSavePages.getText().toString());
                    numRevPages = Integer.parseInt(et_numOfRevPages.getText().toString());
                    isAttetud = "حاضر";
                    uploadAndSave(id_group);
                    Toast.makeText(Add_a_new_save.this, "تم اضافة الحفظ بنجاح", Toast.LENGTH_SHORT).show();
                    finish();
                    startActivity(new Intent(getBaseContext(), Main_teacher.class));

                }

            }
        });


        show_spinner();
        spinner_select_student.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(Add_a_new_save.this, infoArrayList.get(position).getName(), Toast.LENGTH_SHORT).show();
                id_student = infoArrayList.get(position).getId_Student();
                token = infoArrayList.get(position).getTokenId();
                Log.d("ffffff", token + " tt " + id_student);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }


    public ArrayList<Student_Info> get_student_group() {
        ArrayList<Student_Info> arrayList = new ArrayList<>();

        String typeName[] = {"id_group"};
        String value[] = {id_group};
        List<Student_Info> infoList = dataBaseItems.getDataWithAndStatement(typeName, value, Student_Info.class);


        for (int i = 0; i < infoList.size(); i++) {

            String id_student = infoList.get(i).getId_Student();


            String name_student = infoList.get(i).getName();

            String id_center = infoList.get(i).getId_center();
            String id_group = infoList.get(i).getId_group();
            String tokenId = infoList.get(i).getTokenId();
            arrayList.add(new Student_Info(null, name_student, id_student, id_group, id_center, tokenId));

        }
        return arrayList;

    }


    private void def() {


        et_numOfSavePages = findViewById(R.id.student_add_new_save_et_numOfSavePages);
        et_numOfRevPages = findViewById(R.id.student_add_new_save_et_numOfRevPages);
        spinner_select_student = findViewById(R.id.spinner_selection_student);
        btn_addSave = findViewById(R.id.student_add_new_save_btn_addSave);
        btn_addAbcens = findViewById(R.id.student_add_new_save_btn_addAbsence);

        spinner_saves = findViewById(R.id.spinner_save);
        spinner_save_from = findViewById(R.id.spinner_save_from);
        spinner_save_too = findViewById(R.id.spinner_save_to);

        spinner_reviews = findViewById(R.id.spinner_review);
        spinner_reviews_from = findViewById(R.id.spinner_review_from);
        spinner_reviews_too = findViewById(R.id.spinner_review_to);

    }

    private void show_spinner() {


        infoArrayList.clear();

        ArrayList<Student_Info> arrayList = get_student_group();
        for (int i = 0; i < arrayList.size(); i++) {

            String student_id = arrayList.get(i).getId_Student();
            String student_name = arrayList.get(i).getName();
            Log.d("fffff", arrayList.get(i).getTokenId() + " t t");
            infoArrayList.add(new Student_Info(student_name, student_id, null, arrayList.get(i).getTokenId()));

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

    private void setSaveAndrev() {
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

    private void soras() {
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


//    private void getReport(final DatabaseReference student, final int date_year, final int date_month) {
//        final DatabaseReference reports = student.child("student_save").child("report").
//                child(date_month + "/" + date_year);
//        reports.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if (snapshot != null) {
//                    report1 = snapshot.getValue(Report.class);
//                    Report report2 = null;
//                    if (isAbcens) {
//                        if (report2 != null) {
//                            report2 = new Report(0 +
//                                    report1.getNumOfAttendanceDays(),
//                                    1 + report1.getNumOfNonAttendanceDays(),
//                                    0 + report1.getNumOfSavePages(),
//                                    0 + report1.getNumOfReviewPages());
//                        } else {
//                            report2 = new Report(0
//                                    ,
//                                    1,
//                                    0,
//                                    0);
//                        }
//                    } else {
//                        if (report2 != null) {
//                            report2 = new Report(1
//                                    + report1.getNumOfAttendanceDays()
//                                    , 0 + report1.getNumOfNonAttendanceDays()
//                                    , Integer.parseInt(et_numOfSavePages.getText().toString())
//                                    + report1.getNumOfSavePages(),
//                                    Integer.parseInt(et_numOfRevPages.getText().toString())
//                                            + report1.getNumOfReviewPages());
//                        } else {
//                            report2 = new Report(1
//                                    , 0
//                                    , Integer.parseInt(et_numOfSavePages.getText().toString())
//                                    ,
//                                    Integer.parseInt(et_numOfRevPages.getText().toString())
//                            );
//
//                        }
//                    }
//                    DatabaseReference reports = student.child("student_save").child("report").child(date_month + "/" + date_year);
//                    reports.setValue(report2);
//
//                }
//                reports.removeEventListener(this);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//
//
//    }
//

}