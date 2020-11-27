package com.example.quranprojectdemo.Activities.mainActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quranprojectdemo.Activities.otherActivity.AboutApp;
import com.example.quranprojectdemo.Activities.joinRequsers.JoinRequests;
import com.example.quranprojectdemo.Activities.otherActivity.SplashScreen;
import com.example.quranprojectdemo.Activities.showDetails.ShowmeMorizationLoops;
import com.example.quranprojectdemo.Activities.registrar.QuranCenter_Reg;
import com.example.quranprojectdemo.Activities.logIn.QuranCenter_Login;
import com.example.quranprojectdemo.realm.RealmDataBaseItems;
import com.example.quranprojectdemo.models.centers.CenterUser;
import com.example.quranprojectdemo.recyclerView.group.CustomGroupRecyclerView2;
import com.example.quranprojectdemo.models.groups.Group;
import com.example.quranprojectdemo.models.groups.Group_Info;
import com.example.quranprojectdemo.models.students.Student_Info;
import com.example.quranprojectdemo.R;
import com.example.quranprojectdemo.Activities.registrar.AddNewGroup;
import com.example.quranprojectdemo.Activities.registrar.RegisterAs;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.protobuf.Api;
import com.shashank.sony.fancytoastlib.FancyToast;

import org.json.JSONObject;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


import static com.example.quranprojectdemo.Activities.logIn.QuranCenter_Login.INFO_CENTER_LOGIN;
import static com.example.quranprojectdemo.Activities.otherActivity.SplashScreen.CHEACKHOWISLOGGED;

public class Main_center extends AppCompatActivity {

    public static final String CHECK_REG_CENTER = "check_center";
    public static final String CHECK_REG_CENTER_ID = "check_center_id";
    //    private static final String SERVER_KEY = "AAAAkRwHS54:APA91bHo7rCE2XO3EDN-MZqYfN9wdn1R3kDYamiIwH-ZxQT_uQ2gJYBu_TyMS-HzkeeSW26324LnpET2VeLC4QG__f7R3sCHD_oRD5t8uOD7NNWv2c1DrthXIKLbHMYlvdSMYwpJdNng";
    public Toolbar toolbar_center;
    TextView tv_center_name, tv_center_name_maneger, tv_center_phone, tv_center_count_ring, tv_center_count_student;
    SharedPreferences sp;
    private String centerId;
    private SharedPreferences.Editor editor;
    RecyclerView.LayoutManager layoutManager;
    CustomGroupRecyclerView2 customGroupRecyclerView2;
    RecyclerView recyclerView;
    ArrayList<Group> data;
    RealmDataBaseItems dataBaseItems;

    String tkn;

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
        if (getSharedPreferences(CHEACKHOWISLOGGED, MODE_PRIVATE).getInt(SplashScreen.HOWISLOGGED, -1) == -1)
            getSharedPreferences(CHEACKHOWISLOGGED, MODE_PRIVATE).edit().putInt(SplashScreen.HOWISLOGGED, 0).commit();
        tkn = FirebaseInstanceId.getInstance().getToken();

        data = new ArrayList<>();
        def();
        sp = getSharedPreferences(INFO_CENTER_LOGIN, MODE_PRIVATE);
        dataBaseItems = RealmDataBaseItems.getInstance(getBaseContext());
        viewFont();
        if (sp.getString(QuranCenter_Login.ID_CENTER_LOGIN, "a").equals("a")) {
            sp = getSharedPreferences(QuranCenter_Reg.INFO_CENTER_REG, MODE_PRIVATE);
            centerId = sp.getString(QuranCenter_Reg.ID_CENTER_REG, "a");

        } else {
            centerId = sp.getString(QuranCenter_Login.ID_CENTER_LOGIN, "a");
        }

        sp = getSharedPreferences(CHECK_REG_CENTER, MODE_PRIVATE);
        editor = sp.edit();
        editor.putInt(CHECK_REG_CENTER_ID, 1);
        editor.commit();


        toolbar_center = findViewById(R.id.center_main_tool);
        toolbar_center.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.MenuCentreHomeAddGroub:
                        startActivity(new Intent(getBaseContext(), AddNewGroup.class));
//                        new Notify().execute();
//                        sendPostToFCM("hhelllo");
//                        sendFireBaseNotification(SERVER_KEY, tkn, "hello how are you", "welcome to our app");

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
                        getSharedPreferences(INFO_CENTER_LOGIN, MODE_PRIVATE).edit().clear().clear();
                        getSharedPreferences(QuranCenter_Reg.INFO_CENTER_REG, MODE_PRIVATE).edit().clear().commit();
                        getSharedPreferences(CHEACKHOWISLOGGED, MODE_PRIVATE).edit().clear().commit();
                        getSharedPreferences(Main_center.CHECK_REG_CENTER, MODE_PRIVATE).edit().clear().commit();
                        dataBaseItems.deleteAllData();
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


    }

    private void def() {
        tv_center_name = findViewById(R.id.center_main_tv_name_center);
        tv_center_name_maneger = findViewById(R.id.center_main_tv_name_maneger);
        tv_center_phone = findViewById(R.id.center_main_tv_phone);
        tv_center_count_ring = findViewById(R.id.center_main_tv_count_ring);
        tv_center_count_student = findViewById(R.id.center_main_tv_count_student);
        recyclerView = findViewById(R.id.mainCenter_rv);

    }

    public void viewFont() {

        tv_center_count_ring.setTypeface(Typeface.createFromAsset(getAssets(), "Hacen_Tunisia.ttf"));
        tv_center_count_student.setTypeface(Typeface.createFromAsset(getAssets(), "Hacen_Tunisia.ttf"));
        tv_center_name.setTypeface(Typeface.createFromAsset(getAssets(), "Hacen_Tunisia.ttf"));
        tv_center_name_maneger.setTypeface(Typeface.createFromAsset(getAssets(), "Hacen_Tunisia.ttf"));
        tv_center_phone.setTypeface(Typeface.createFromAsset(getAssets(), "Hacen_Tunisia.ttf"));
    }


    public void getInRealTimeUsers() {


        List<CenterUser> centerUserList = dataBaseItems.getAllDataFromRealm(CenterUser.class);
        if (centerUserList != null) {
            CenterUser value = centerUserList.get(0);

            List<Student_Info> studentInfos = dataBaseItems.getAllDataFromRealm(Student_Info.class);
            if (studentInfos != null) {
                tv_center_count_ring.setText("عدد الحلقات : " + value.getAuto_id_group());


                if (studentInfos.isEmpty()) {
                    tv_center_count_student.setText("عدد طلاب المركز:" + "0");
                } else {
                    tv_center_count_student.setText("عدد طلاب المركز:" + studentInfos.size());


                }


                tv_center_name.setText("مركز:" + value.getCenterName());
                tv_center_name_maneger.setText("مدير المركز:" + value.getManagerName());
                tv_center_phone.setText("هاتف:" + value.getPhone());
                toolbar_center.setTitle("مركز:" + value.getCenterName());

            }
        }
    }

    @Override
    public void onBackPressed() {
        finishAffinity();


    }

    public void getGroups(final String id_center) {

        List<Group_Info> group_infos = dataBaseItems.getAllDataFromRealm(Group_Info.class);
        data.clear();
        if (group_infos != null) {
            for (int i = 0; i < group_infos.size(); i++) {
                String id_group = group_infos.get(i).getGroup_id();
                Log.d("dre", id_group + " ! ");
                String name_group = group_infos.get(i).getGroup_name();
                String name_tech = group_infos.get(i).getTeacher_name();
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

    private void sendFireBaseNotification(final String keyServer, final String token, final String title, final String body) {
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {

                    URL url = new URL("https://fcm.googleapis.com/fcm/send");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                    conn.setUseCaches(false);
                    conn.setDoInput(true);
                    conn.setDoOutput(true);

                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Authorization", "key=" + keyServer);
                    conn.setRequestProperty("Content-Type", "application/json");

                    JSONObject json = new JSONObject();

                    json.put("to", token);

                    JSONObject info = new JSONObject();
                    info.put("title", title);   // Notification title
                    info.put("body", body); // Notification body

                    json.put("notification", info);

                    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                    wr.write(json.toString());
                    wr.flush();
                    conn.getInputStream();

                } catch (Exception e) {
                    Log.d("Error", "" + e);
                }


            }
        }).start();

    }



}
