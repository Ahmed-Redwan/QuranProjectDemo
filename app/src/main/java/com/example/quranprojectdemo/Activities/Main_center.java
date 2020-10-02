package com.example.quranprojectdemo.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quranprojectdemo.Other.Center;
import com.example.quranprojectdemo.Other.CenterUser;
import com.example.quranprojectdemo.Other.Group;
import com.example.quranprojectdemo.Other.Group_Info;
import com.example.quranprojectdemo.Other.Student_Info;
import com.example.quranprojectdemo.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shashank.sony.fancytoastlib.FancyToast;

import io.realm.Realm;
import io.realm.RealmQuery;

import static com.example.quranprojectdemo.Activities.QuranCenter_Login.INFO_CENTER_LOGIN;

public class Main_center extends AppCompatActivity {

    Toolbar toolbar_center;
    ImageView image_center;
    TextView tv_center_name, tv_center_name_maneger, tv_center_phone, tv_center_count_ring, tv_center_count_student;
    SharedPreferences sp;
    private String centerId;

    @Override
    protected void onStart() {
        super.onStart();
        getInRealTimeUsers();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_center);

        //image_center = findViewById(R.id.center_main_image);
        tv_center_name = findViewById(R.id.center_main_tv_name_center);
        tv_center_name_maneger = findViewById(R.id.center_main_tv_name_maneger);
        tv_center_phone = findViewById(R.id.center_main_tv_phone);
        tv_center_count_ring = findViewById(R.id.center_main_tv_count_ring);
        tv_center_count_student = findViewById(R.id.center_main_tv_count_student);
        sp = getSharedPreferences(INFO_CENTER_LOGIN, MODE_PRIVATE);

        if (sp.getString(QuranCenter_Login.ID_CENTER_LOGIN, "a").equals("a")) {
            sp = getSharedPreferences(QuranCenter_Reg.INFO_CENTER_REG, MODE_PRIVATE);
            centerId = sp.getString(QuranCenter_Reg.ID_CENTER_REG, "a");

        } else {
            centerId = sp.getString(QuranCenter_Login.ID_CENTER_LOGIN, "a");
        }

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
                        FancyToast.makeText(getBaseContext(), "تم تسجيل الخروج.", FancyToast.LENGTH_LONG, FancyToast.DEFAULT, false).show();
                        finish();
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


        Realm.init(getBaseContext());
        Realm realm = Realm.getDefaultInstance();
        RealmQuery<CenterUser> query = realm.where(CenterUser.class);
        CenterUser value = query.findFirst();

//        RealmQuery<Group_Info> queryGroup = realm.where(Group_Info.class);
        RealmQuery<Student_Info> queryStudent = realm.where(Student_Info.class);
//

        tv_center_count_ring.setText("عدد الحلقات : " + value.getAuto_id_group());


        tv_center_count_student.setText("عدد طلاب المركز:" + queryStudent.count());


        tv_center_name.setText("مركز:" + value.getcenterName());
        tv_center_name_maneger.setText("مدير المركز:" + value.getmanagerName());
        tv_center_phone.setText("هاتف:" + value.getPhone());
        toolbar_center.setTitle("مركز:" + value.getcenterName());


    }

}

