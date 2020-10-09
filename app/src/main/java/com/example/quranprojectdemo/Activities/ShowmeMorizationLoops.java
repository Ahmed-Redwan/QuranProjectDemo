package com.example.quranprojectdemo.Activities;

import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quranprojectdemo.Other.CustomGroupRecyclerView;
import com.example.quranprojectdemo.Other.Group;
import com.example.quranprojectdemo.Other.Group_Info;
import com.example.quranprojectdemo.R;

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
//          data.add(new Group(R.drawable.arabian, "ابو بكر الصديق", "احمد عبد الغفور"));


    }

    @Override
    protected void onStart() {
        super.onStart();
        getGroups(id_center);


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
        rv_List.setLayoutManager(layoutManager);


    }


}
