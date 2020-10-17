package com.example.quranprojectdemo.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quranprojectdemo.Other.CenterUser;
import com.example.quranprojectdemo.Other.CustomGroupRecyclerView;
import com.example.quranprojectdemo.Other.CustomGroupRecyclerView2;
import com.example.quranprojectdemo.Other.Group;
import com.example.quranprojectdemo.Other.Group_Info;
import com.example.quranprojectdemo.Other.Student_Info;
import com.example.quranprojectdemo.R;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

import static com.example.quranprojectdemo.Activities.QuranCenter_Login.INFO_CENTER_LOGIN;

public class Main_center extends AppCompatActivity {

    public static final String CHECK_REG_CENTER = "center_check";
    public static final String CHECK_REG_CENTER_ID = "id_center_check";
    Toolbar toolbar_center;
    ImageView image_center;
    TextView tv_center_name, tv_center_name_maneger, tv_center_phone, tv_center_count_ring, tv_center_count_student;
    SharedPreferences sp;
    private String centerId;
    Realm realm;
    private SharedPreferences.Editor editor;
    RecyclerView.LayoutManager layoutManager;
    CustomGroupRecyclerView2 customGroupRecyclerView2;
    RecyclerView recyclerView;

    RecyclerView rv_List;

    ArrayList<Group> data;


    @Override
    protected void onStart() {
        super.onStart();

        getInRealTimeUsers();
        getGroups(centerId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_center);
        Realm.init(getBaseContext());
        realm = Realm.getDefaultInstance();

        tv_center_name = findViewById(R.id.center_main_tv_name_center);
        tv_center_name_maneger = findViewById(R.id.center_main_tv_name_maneger);
        tv_center_phone = findViewById(R.id.center_main_tv_phone);
        tv_center_count_ring = findViewById(R.id.center_main_tv_count_ring);
        tv_center_count_student = findViewById(R.id.center_main_tv_count_student);


        recyclerView = findViewById(R.id.mainCenter_rv);
        //        groups.add(new Group(R.drawable.arabian, "", ""));
//        groups.add(new Group(R.drawable.ahmed_abd, "Ahmed Redwan Abdelghafoor", "0594114029"));
//        groups.add(new Group(R.drawable.ahmed_ali, "Ahmed Ali Alyaqubi", "0594111238"));
//        groups.add(new Group(R.drawable.mustafa, "mustafa muhammed alastal", "0594115468"));
//        groups.add(new Group(R.drawable.arabian, "", ""));
//        groups.add(new Group(R.drawable.ahmed_abd, "Ahmed Redwan Abdelghafoor", "0594114029"));
//        groups.add(new Group(R.drawable.ahmed_ali, "Ahmed Ali Alyaqubi", "0594111238"));
//        groups.add(new Group(R.drawable.mustafa, "mustafa muhammed alastal", "0594115468"));
//        groups.add(new Group(R.drawable.arabian, "", ""));
//        groups.add(new Group(R.drawable.ahmed_abd, "Ahmed Redwan Abdelghafoor", "0594114029"));
//        groups.add(new Group(R.drawable.ahmed_ali, "Ahmed Ali Alyaqubi", "0594111238"));
//        groups.add(new Group(R.drawable.mustafa, "mustafa muhammed alastal", "0594115468"));
//        groups.add(new Group(R.drawable.arabian, "", ""));
//        groups.add(new Group(R.drawable.ahmed_abd, "Ahmed Redwan Abdelghafoor", "0594114029"));
//        groups.add(new Group(R.drawable.ahmed_ali, "Ahmed Ali Alyaqubi", "0594111238"));
//        groups.add(new Group(R.drawable.mustafa, "mustafa muhammed alastal", "0594115468"));






        sp = getSharedPreferences(INFO_CENTER_LOGIN, MODE_PRIVATE);
        if (sp.getString(QuranCenter_Login.ID_CENTER_LOGIN, "a").equals("a")) {
            sp = getSharedPreferences(QuranCenter_Reg.INFO_CENTER_REG, MODE_PRIVATE);
            centerId = sp.getString(QuranCenter_Reg.ID_CENTER_REG, "a");

        } else {
            centerId = sp.getString(QuranCenter_Login.ID_CENTER_LOGIN, "a");
        }


        sp=getSharedPreferences(CHECK_REG_CENTER,MODE_PRIVATE);
        editor=sp.edit();
        editor.putInt(CHECK_REG_CENTER_ID,1);
        editor.commit();


        toolbar_center = findViewById(R.id.center_main_tool);
        toolbar_center.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.MenuCentreHomeAddGroub:
                        startActivity(new Intent(getBaseContext(), AddNewGroup.class));

                        return true;
//                    case R.id.MenuCentreHomeAddStudent:
//                        startActivity(new Intent(getBaseContext(), AddNewStudent.class));
//                        return true;
                    case R.id.MenuCentreHomeShowInfo:
                        startActivity(new Intent(getBaseContext(), ShowmeMorizationLoops.class));
                        return true;
                    case R.id.MenuCentreHomeRequestsList:
                        startActivity(new Intent(getBaseContext(), JoinRequests.class).putExtra("CenterId", centerId));
                        return true;
                    case R.id.MenuCenterHomeExit:
                        sp = getSharedPreferences(INFO_CENTER_LOGIN, MODE_PRIVATE);
                        editor = sp.edit();
                        editor.clear();
                        editor.commit();
                        sp = getSharedPreferences(QuranCenter_Reg.INFO_CENTER_REG, MODE_PRIVATE);
                        editor = sp.edit();
                        editor.clear();
                        editor.commit();


                        sp=getSharedPreferences(Main_center.CHECK_REG_CENTER,MODE_PRIVATE);
                        editor=sp.edit();
                        editor.clear();
                        editor.commit();
//                        sp=getSharedPreferences(Main_teacher.CHECK_REG_TEACHER,MODE_PRIVATE);
//                        editor=sp.edit();
//                        editor.clear();
//                        sp=getSharedPreferences(Main_student.CHECK_REG_STUDENT,MODE_PRIVATE);
//                        editor=sp.edit();
//                        editor.clear();




                        if (!realm.isInTransaction())
                            realm.beginTransaction();
                        realm.deleteAll();
                        realm.commitTransaction();
                        realm.close();
                        FancyToast.makeText(getBaseContext(), "تم تسجيل الخروج.", FancyToast.LENGTH_LONG, FancyToast.DEFAULT, false).show();
                        finish();
                        startActivity(new Intent(getBaseContext(), RegisterAs.class));

                        return true;
                    case R.id.MenuCenterHomeSettings:
                        return true;
                    case R.id.MenuCenterHomeAbout:
                        startActivity(new Intent(getBaseContext(), AboutApp.class));
                        return true;
                }
                return false;
            }
        });

        TextView_EditFont(tv_center_count_ring, "Hacen_Tunisia.ttf");
        TextView_EditFont(tv_center_count_student, "Hacen_Tunisia.ttf");
        TextView_EditFont(tv_center_name, "Hacen_Tunisia.ttf");
        TextView_EditFont(tv_center_name_maneger, "Hacen_Tunisia.ttf");
        TextView_EditFont(tv_center_phone, "Hacen_Tunisia.ttf");


    }

    //change font type for textview.
    public void TextView_EditFont(TextView textView, String path) {
        textView.setTypeface(Typeface.createFromAsset(getAssets(), path));
    }


    public void getInRealTimeUsers() {


        RealmQuery<CenterUser> query = realm.where(CenterUser.class);
        CenterUser value = query.findFirst();

        RealmQuery<Student_Info> queryStudent = realm.where(Student_Info.class);


        tv_center_count_ring.setText("عدد الحلقات : " + value.getAuto_id_group());


        if (queryStudent.findAll().isEmpty()) {
            tv_center_count_student.setText("عدد طلاب المركز:" + "0");
        } else {
            tv_center_count_student.setText("عدد طلاب المركز:" + queryStudent.count());


        }


        tv_center_name.setText("مركز:" + value.getCenterName());
        tv_center_name_maneger.setText("مدير المركز:" + value.getManagerName());
        tv_center_phone.setText("هاتف:" + value.getPhone());
        toolbar_center.setTitle("مركز:" + value.getCenterName());


    }

    @Override
    public void onBackPressed() {
        finishAffinity();


    }

    public void getGroups(final String id_center) {

        RealmQuery<Group_Info> query = realm.where(Group_Info.class);

        data.clear();
        RealmResults<Group_Info> realmResults = query.findAll();
        for (int i = 0; i < realmResults.size(); i++) {
            String id_group = realmResults.get(i).getGroup_id();
            Log.d("dre", id_group + " ! ");
            String name_group = realmResults.get(i).getGroup_name();
            String name_tech = realmResults.get(i).getTeacher_name();
            data.add(new Group(R.drawable.arabian, name_group, name_tech, id_group, id_center));
        }

        layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        customGroupRecyclerView2 = new CustomGroupRecyclerView2(data, getBaseContext());
        recyclerView.setAdapter(customGroupRecyclerView2);
        recyclerView.setLayoutManager(layoutManager);
        customGroupRecyclerView2.notifyDataSetChanged();
        recyclerView.setHasFixedSize(true);


    }

}

