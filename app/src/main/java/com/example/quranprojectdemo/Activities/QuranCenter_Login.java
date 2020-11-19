package com.example.quranprojectdemo.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quranprojectdemo.Activities.realm.RealmDataBaseItems;
import com.example.quranprojectdemo.Other.CenterUser;
import com.example.quranprojectdemo.Other.CheckInternet;
import com.example.quranprojectdemo.Other.Group_Info;
import com.example.quranprojectdemo.Other.Student_Info;
import com.example.quranprojectdemo.Other.Student_data;
import com.example.quranprojectdemo.Other.Student_data_cash;
import com.example.quranprojectdemo.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.List;


public class QuranCenter_Login extends AppCompatActivity {
    public static final String ID_CENTER_LOGIN = "id_center_log";
    public static final String INFO_CENTER_LOGIN = "Info_center";
    TextView tv_Login, tv_iDontHaveAnAccount, tv_NewAccount;
    EditText et_Email, et_password;
    Button btn_Login;
    public FirebaseAuth mAuth;
    SharedPreferences sp;
    SharedPreferences.Editor editor;

    private CheckBox saveLoginCheckBox;
    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;
    private Boolean saveLogin;
    private CenterUser user;
    RealmDataBaseItems dataBaseItems;

    private boolean checkInternet() {
        CheckInternet checkInternet;
        checkInternet = new CheckInternet();
        if (checkInternet.isConnected(this)) {
            return true;
        }
        return false;
    }

    private void getGroups_Student_Saves() {
        sp = getSharedPreferences(INFO_CENTER_LOGIN, MODE_PRIVATE);
        String id_center = sp.getString(ID_CENTER_LOGIN, "0");
        FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
        final DatabaseReference reference = rootNode.getReference("CenterUsers").child(id_center).child("groups");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    Group_Info g = dataSnapshot.child("group_info").getValue(Group_Info.class);
                    if (g != null) {

                        dataBaseItems.insertObjectToDataToRealm(g, Group_Info.class);

                    }

                    DataSnapshot snapshot_std = dataSnapshot.child("student_group");
//                    RealmResults<Student_Info> realmResults = realm.where(Student_Info.class).findAll();/// this is
//                    if (snapshot_std.getChildrenCount() > realmResults.size()) {
                    for (DataSnapshot snapshot1 : snapshot_std.getChildren()) {
                        Student_Info s = snapshot1.child("student_info").getValue(Student_Info.class);
                        if (s != null) {

                            dataBaseItems.insertObjectToDataToRealm(s, Student_Info.class);

                        }
                    }
//                    }
                    for (DataSnapshot snapshot2 : snapshot_std.getChildren()) {
                        DataSnapshot dataSnapshot1 = snapshot2.child("student_save");
//                        List<Student_data> studentDataList = dataBaseItems.getStudentData(dataSnapshot.getKey(), snapshot2.getKey());


//
//                        if (dataSnapshot1.getChildrenCount() > results.size()) {
                        int count = (int) dataSnapshot1.getChildrenCount();
                        if (count > 0) {
                            for (DataSnapshot snapshot1 : dataSnapshot1.getChildren()) {
                                Student_data data = snapshot1.getValue(Student_data.class);
                                dataBaseItems.insertObjectToDataToRealm(data, Student_data.class);

                            }


                        }
                    }
//                    }


                }

                startActivity(new Intent(getBaseContext(), Main_center.class));

                reference.removeEventListener(this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quran_center__login);
        mAuth = FirebaseAuth.getInstance();
        dataBaseItems = RealmDataBaseItems.getinstance(getBaseContext());
//        String id_center = sp.getString(QuranCenter_Login.ID_CENTER_LOGIN, "a");

        List<CenterUser> centerUserList = dataBaseItems.getAllDataFromRealm(CenterUser.class);
        if (centerUserList != null) {
            if (!centerUserList.isEmpty()) {
                Log.d("vvvv", centerUserList.size() + " 3033");
                sp = getSharedPreferences(INFO_CENTER_LOGIN, MODE_PRIVATE);


                if (checkInternet()) {

                    getGroups_Student_Saves();
                }
            }
        } else {

//            getInRealTimeUsers(id_center);
//            startActivity(new Intent(getBaseContext(), Main_center.class));
//            finish();
        }
        tv_Login = findViewById(R.id.QuranCenterLogin_tv_login);
        tv_NewAccount = findViewById(R.id.QuranCenterLogin_tv_NewAccount);
        tv_iDontHaveAnAccount = findViewById(R.id.QuranCenterLogin_tv_iDontHaveAnAccount);
        et_Email = findViewById(R.id.QuranCenterLogin_et_EmailOrphone);
        et_password = findViewById(R.id.QuranCenterLogin_et_Password);
        btn_Login = findViewById(R.id.QuranCenterLogin_btn_Login);
        saveLoginCheckBox = findViewById(R.id.QuranCenterLogin_Cb_remmemberme);
        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();

        saveLogin = loginPreferences.getBoolean("saveLogin", false);
        if (saveLogin == true) {
            et_Email.setText(loginPreferences.getString("username", ""));
            et_password.setText(loginPreferences.getString("password", ""));
            saveLoginCheckBox.setChecked(true);
        }


        TextView_EditFont(tv_Login, "Hacen_Tunisia_Bold.ttf");
        TextView_EditFont(tv_NewAccount, "Hacen_Tunisia.ttf");
        TextView_EditFont(tv_iDontHaveAnAccount, "Hacen_Tunisia.ttf");

        btn_Login.setTypeface(Typeface.createFromAsset(getAssets(), "Hacen_Tunisia.ttf"));

        EditText_EditFont(et_Email, "Hacen_Tunisia.ttf");
        EditText_EditFont(et_password, "Hacen_Tunisia.ttf");


        tv_NewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(), QuranCenter_Reg.class));
                finish();
            }
        });


    }

    //change font type for textview.
    public void TextView_EditFont(TextView textView, String path) {
        textView.setTypeface(Typeface.createFromAsset(getAssets(), path));
    }

    //change font type for edittext.
    public void EditText_EditFont(EditText editText, String path) {
        editText.setTypeface(Typeface.createFromAsset(getAssets(), path));
    }

    @Override
    protected void onStart() {
        super.onStart();

        btn_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (et_Email.getText().toString().isEmpty()) {
                    et_Email.setError("يجب ادخال الايميل أو رقم الهاتف.");
                    return;
                } else if (et_password.getText().toString().isEmpty()) {
                    et_password.setError("يجب ادخال كلمة المرور.");
                    return;
                } else if (et_password.getText().toString().length() < 5) {
                    et_password.setError("يجب أن تكون كلمة المرور أكثر من 7 حروف.");
                    return;
                }

                log_in();


                String username = et_Email.getText().toString();
                String password = et_password.getText().toString();

                if (saveLoginCheckBox.isChecked()) {
                    loginPrefsEditor.putBoolean("saveLogin", true);
                    loginPrefsEditor.putString("username", username);
                    loginPrefsEditor.putString("password", password);
                    loginPrefsEditor.commit();
                } else {
                    loginPrefsEditor.clear();
                    loginPrefsEditor.commit();
                }


            }
        });

    }

    private void log_in() {

        mAuth.signInWithEmailAndPassword(et_Email.getText().toString(), et_password.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            sp = getSharedPreferences(INFO_CENTER_LOGIN, MODE_PRIVATE);

                            editor = sp.edit();
                            editor.putString(ID_CENTER_LOGIN, user.getUid());
                            editor.commit();
                            sp = getSharedPreferences(QuranCenter_Reg.INFO_CENTER_REG, MODE_PRIVATE);
                            editor = sp.edit();
                            editor.clear();
                            editor.commit();

                            FancyToast.makeText(getBaseContext(), "تم تسجيل الدخول بنجاح.", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
                            getInRealTimeUsers(user.getUid());

                            //
//
                        } else {
                            et_Email.setError("تأكد من الإيميل و كلمة المرور.");
                            et_password.setError("تأكد من الإيميل و كلمة المرور.");
                            FancyToast.makeText(getBaseContext(), "فشل في تسجيل الدخول.", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();//
                        }
                    }
                });
    }//للدخول


    public void getInRealTimeUsers(String centerId) {
        FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
        final DatabaseReference reference = rootNode.getReference("CenterUsers").
                child(centerId).child("Center information");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                CenterUser value = snapshot.getValue(CenterUser.class);
                Log.w("TAG", value.getEmail() + "***");

                dataBaseItems.insertObjectToDataToRealm(value, CenterUser.class);


                Log.d("ffff", value.getAddress() + " rr ");
                getGroups_Student_Saves();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("TAG", "2112121212121212***");

            }
        });
    }//جلب البيانات
//
//    private void get_new_student() {
//        sp = getSharedPreferences(INFO_CENTER_LOGIN, MODE_PRIVATE);
//        String id_center = sp.getString(ID_CENTER_LOGIN, "-1");
//
//
//        FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
//
//        final DatabaseReference reference = rootNode.getReference("CenterUsers");//already found
//        DatabaseReference my_center = reference.child(id_center);//already found
//        DatabaseReference my_center_groups = my_center.child("groups");//already found or not
//        final ArrayList<Student_Info> arrayList = new ArrayList<>();
//        my_center_groups.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                    RealmQuery query = realm.where(Student_Info.class).equalTo("id_group", dataSnapshot.getKey());
////                    RealmResults<Student_Info> realmResults = realm.where(Student_Info.class).findAll().where()
////                            .equalTo("id_group", dataSnapshot.getKey()).findAll();/// this is
////                    query.equalTo("id_group", dataSnapshot.getKey());
//
//                    if (dataSnapshot.child("student_group").getChildrenCount() > query.findAll().size()) {
//                        DataSnapshot d = dataSnapshot.child("student_group");
//                        int i = query.findAll().max("id_number").intValue();
//                        for (DataSnapshot dataSnapshot1 : d.getChildren()) {
//                            if (Integer.parseInt(dataSnapshot1.getValue(Student_Info.class).getId_number()) > i) {
//                                arrayList.add(d.getValue(Student_Info.class));
//
//
//                            }
//
//                        }
//                    }
//
//                }
//                if (!arrayList.isEmpty()) {
//                    realm.insertOrUpdate(arrayList);
//
//                    realm.commitTransaction();
//                    realm.close();
//                }
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
//    private void get_student_save() {
//        sp = getSharedPreferences(INFO_CENTER_LOGIN, MODE_PRIVATE);
//
//        if (checkInternet()) {
//
//            FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
//            final DatabaseReference reference = rootNode.getReference("CenterUsers").child(sp.getString(ID_CENTER_LOGIN, "0"))
//                    .child("groups");
//            final ArrayList<Student_data> arrayList = new ArrayList<>();
//            RealmResults<Group_Info> group_infos = realm.where(Group_Info.class).findAll();/// this is
//            for (int i = 0; i < group_infos.size(); i++) {
//                final String idGroup = group_infos.get(i).getGroup_id();
//                if (idGroup != null && !idGroup.isEmpty())
//                    reference.child(idGroup).child("student_group").
//                            addValueEventListener(new ValueEventListener() {
//                                @Override
//                                public void onDataChange(DataSnapshot dataSnapshot) {
//                                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                                        int max = realm.where(Student_data.class).
//                                                equalTo("id_student", snapshot.getKey()).and().equalTo("id_group"
//                                                , idGroup).findAll().max("time_save").intValue();
//                                        for (DataSnapshot snapshot1 : snapshot.child("student_save").getChildren()) {
//                                            if (Integer.parseInt(snapshot1.getKey()) > max) {
//                                                arrayList.add(snapshot1.getValue(Student_data.class));
//
//                                            }
//
//                                        }
//
//
//                                    }
//                                    if (!realm.isInTransaction())
//                                        realm.beginTransaction();
//                                    realm.insertOrUpdate(arrayList);
//                                    realm.commitTransaction();
//                                    realm.close();
//                                    reference.removeEventListener(this);
//                                }
//
//                                @Override
//                                public void onCancelled(DatabaseError error) {
//                                }
//                            });
//            }
//
//
//        }
//
//
//    }
}
