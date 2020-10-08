package com.example.quranprojectdemo.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quranprojectdemo.Other.CustomGroupRecyclerView;
import com.example.quranprojectdemo.Other.Group;
import com.example.quranprojectdemo.Other.Group_Info;
import com.example.quranprojectdemo.Other.Student_Info;
import com.example.quranprojectdemo.Other.Student_data;
import com.example.quranprojectdemo.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

import static com.example.quranprojectdemo.Activities.QuranCenter_Login.INFO_CENTER_LOGIN;

public class ShowmeMorizationLoops extends AppCompatActivity {
    TextView tv_ShowMemorizationLoops;
    RecyclerView rv_List;
    Toolbar toolbar;
    List<Group> data;
     SharedPreferences sp;
    private String id_center;
    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_memorization_loops);
        toolbar = findViewById(R.id.ShowMemorizationLoops_ToolBar);
        Realm.init(getBaseContext());
        realm = Realm.getDefaultInstance();
        sp = getSharedPreferences(INFO_CENTER_LOGIN, MODE_PRIVATE);

        if (sp.getString(QuranCenter_Login.ID_CENTER_LOGIN, "a").equals("a")) {
            sp = getSharedPreferences(QuranCenter_Reg.INFO_CENTER_REG, MODE_PRIVATE);
            id_center = sp.getString(QuranCenter_Reg.ID_CENTER_REG, "a");

        } else {
            id_center = sp.getString(QuranCenter_Login.ID_CENTER_LOGIN, "a");


        }
        data = new ArrayList<>();


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

        tv_ShowMemorizationLoops = findViewById(R.id.ShowMemorizationLoops_tv_ShowMemorizationLoops);
        rv_List = findViewById(R.id.ShowMemorizationLoops_Rv_List);

        tv_ShowMemorizationLoops.setTypeface(Typeface.createFromAsset(getAssets(), "Hacen_Tunisia_Bold.ttf"));
      //  int img, String groupName, String teacherName,String id_group,String id_center
         data.add(new Group(R.drawable.ahmed_abd,"ابو بكر الصديق","ahmed"));
         data.add(new Group(R.drawable.ahmed_ali,"ابو بكر الصديق","ahmed"));
         data.add(new Group(R.drawable.mustafa,"ابو بكر الصديق","mustafa"));



        LinearLayoutManager layoutManager1 =new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL
                ,false);
        rv_List.setLayoutManager(layoutManager1);
        rv_List.setItemAnimator(new DefaultItemAnimator());

        final CustomGroupRecyclerView customGroupRecyclerView = new CustomGroupRecyclerView(data, getBaseContext());
        rv_List.setAdapter(customGroupRecyclerView);


    }

    @Override
    protected void onStart() {
        super.onStart();
       // getGroups(id_center);


    }


    public void getGroups(final String id_center) {

        RealmQuery<Group_Info> query = realm.where(Group_Info.class);

        data.clear();
        RealmResults<Group_Info> realmResults = query.findAll();
        for (int i = 0; i < realmResults.size(); i++) {
            String id_group = realmResults.get(i).getGroup_id();
            Log.d("dre",id_group+" ! ");
            String name_group = realmResults.get(i).getGroup_name();
            String name_tech = realmResults.get(i).getTeacher_name();
            data.add(new Group(R.drawable.arabian, name_group, name_tech, id_group, id_center));
        }

        final CustomGroupRecyclerView customGroupRecyclerView = new CustomGroupRecyclerView(data, getBaseContext());
        rv_List.setHasFixedSize(true);
        rv_List.setAdapter(customGroupRecyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getBaseContext());
        LinearLayoutManager layoutManager1 =new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL
                ,false);
        rv_List.setLayoutManager(layoutManager1);
        rv_List.setItemAnimator(new DefaultItemAnimator());


    }


}
