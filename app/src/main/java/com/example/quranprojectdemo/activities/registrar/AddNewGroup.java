package com.example.quranprojectdemo.activities.registrar;

import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quranprojectdemo.activities.logIn.QuranCenter_Login;
import com.example.quranprojectdemo.realm.RealmDataBaseItems;
import com.example.quranprojectdemo.models.centers.CenterUser;
import com.example.quranprojectdemo.models.groups.Group;
import com.example.quranprojectdemo.models.groups.Group_Info;
import com.example.quranprojectdemo.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shashank.sony.fancytoastlib.FancyToast;


import static com.example.quranprojectdemo.activities.logIn.QuranCenter_Login.INFO_CENTER_LOGIN;

public class AddNewGroup extends AppCompatActivity {
    TextView tv_AddNewGroup;
    EditText et_GroupName, et_TeacherName, et_TeacherEmail, et_TeacherPassword, et_TeacherPhone;
    Button btn_add, btn_Cancel;
    FirebaseAuth mAuth;
    private String id_center;
    SharedPreferences sp;
    private String auto_id_group = null;
    private Group_Info group_info;
    private CenterUser value;
    RealmDataBaseItems dataBaseItems;

    // 249 - 259 ,,, 52- 64
    private void addNewGroupDataBase(Group_Info group_info) {
        try {
            if (group_info != null) {
                dataBaseItems.copyObjectToDataToRealm(group_info, Group.class);
            } else
                Toast.makeText(getBaseContext(), "لم تنجح الاضافة", Toast.LENGTH_LONG).show();
        } catch (Exception e) {


        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_group);
        dataBaseItems = RealmDataBaseItems.getinstance(getBaseContext());

        mAuth = FirebaseAuth.getInstance();
        sp = getSharedPreferences(INFO_CENTER_LOGIN, MODE_PRIVATE);

        if (sp.getString(QuranCenter_Login.ID_CENTER_LOGIN, "a").equals("a")) {
            sp = getSharedPreferences(QuranCenter_Reg.INFO_CENTER_REG, MODE_PRIVATE);
            id_center = sp.getString(QuranCenter_Reg.ID_CENTER_REG, "a");

        } else {
            id_center = sp.getString(QuranCenter_Login.ID_CENTER_LOGIN, "a");
        }

        tv_AddNewGroup = findViewById(R.id.AddNewGroup_tv_Add);
        et_GroupName = findViewById(R.id.AddNewGroup_et_GroupName);
        et_TeacherName = findViewById(R.id.AddNewGroup_et_TeacherName);
        btn_add = findViewById(R.id.AddNewGroup_btn_Add);
        btn_Cancel = findViewById(R.id.AddNewGroup_btn_cancel);
        et_TeacherPassword = findViewById(R.id.AddNewGroup_et_TeacherPassword);
        et_TeacherPhone = findViewById(R.id.AddNewGroup_et_TeacherNumberPhone);
        et_TeacherEmail = findViewById(R.id.AddNewGroup_et_Teacher_Email);
        TextView_EditFont(tv_AddNewGroup, "Hacen_Tunisia_Bold.ttf");

        EditText_EditFont(et_GroupName, "Hacen_Tunisia.ttf");
        EditText_EditFont(et_TeacherName, "Hacen_Tunisia.ttf");

        Button_EditFont(btn_add, "Hacen_Tunisia.ttf");
        Button_EditFont(btn_Cancel, "Hacen_Tunisia.ttf");


        btn_Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (et_GroupName.getText().toString().isEmpty() || et_TeacherName.getText().toString().isEmpty() || et_TeacherEmail.getText().toString().isEmpty()
                        || et_TeacherPassword.getText().toString().isEmpty() || et_TeacherPhone.getText().toString().isEmpty()) {
                    et_TeacherPhone.setError("يجب تعبئة جميع الحقول.");
                    et_TeacherPassword.setError("يجب تعبئة جميع الحقول.");
                    et_TeacherEmail.setError("يجب تعبئة جميع الحقول.");
                    et_TeacherName.setError("يجب تعبئة جميع الحقول.");
                    et_GroupName.setError("يجب تعبئة جميع الحقول.");
                    return;
                }
                sign_up(et_TeacherEmail.getText().toString(), et_TeacherPassword.getText().toString());


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

    //change font type for button.
    public void Button_EditFont(Button button, String path) {
        button.setTypeface(Typeface.createFromAsset(getAssets(), path));
    }

    private void sign_up(String email, String password) {


        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            final FirebaseUser user = mAuth.getCurrentUser();


                            FancyToast.makeText(getBaseContext(), "تم إضافة حلقة جديدة.",
                                    FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    getAutoIdGroup(id_center, user);


                                }
                            }).start();


                        } else {
                            Log.w("TAG", "createUserWithEmail:failure", task.getException());
                            FancyToast.makeText(getBaseContext(), "فشل في إضافة الحلقة.", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();

                        }


                    }
                });


    }

    private void updatename(FirebaseUser user, String id_center, String _id_group) {
        UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder()
                .setDisplayName(id_center).
                        setPhotoUri(Uri.parse(_id_group))
                .build();

        user.updateProfile(profileUpdate).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

            }
        });

    }

    public void create_new_group(Group_Info group_info, String id_center) {
        FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
        DatabaseReference reference = rootNode.getReference("CenterUsers").
                child(id_center).child("groups").child(auto_id_group).child("group_info");//already found

        reference.setValue(group_info);//add info As value


    }

    public void getAutoIdGroup(String centerId, final FirebaseUser user) {
        FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
        final DatabaseReference reference = rootNode.getReference("CenterUsers").child(centerId)
                .child("Center information");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                value = snapshot.getValue(CenterUser.class);

                auto_id_group = value.getAuto_id_group();
                int id_group = Integer.parseInt(auto_id_group) + 1;
                String new_id_group = "";
                if (id_group < 10) {
                    new_id_group = "0" + id_group;

                } else {

                    new_id_group = "" + id_group;

                }
                auto_id_group = new_id_group;

                group_info = new Group_Info(et_TeacherEmail.getText().toString(),
                        et_GroupName.getText().toString(), et_TeacherPassword.getText().toString()
                        , et_TeacherPhone.getText().toString(), et_TeacherName.getText().toString()
                        , auto_id_group, id_center, "00");
                updatename(user, id_center, auto_id_group);

                addNewGroupDataBase(group_info);
                create_new_group(group_info, id_center);
                value.setAuto_id_group(auto_id_group);
                save_new_id_group(value);

                if (value != null) {
                         dataBaseItems.insertObjectToDataToRealm(value,CenterUser.class);

                } else
                    Toast.makeText(getBaseContext(), "لم تنجح الاضافة", Toast.LENGTH_LONG).show();
                reference.removeEventListener(this);


                finish();

                finish();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }//جلب البيانات

    private void save_new_id_group(CenterUser centerUser) {
        FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
        final DatabaseReference reference = rootNode.getReference("CenterUsers").child(id_center).
                child("Center information");
        reference.setValue(centerUser);

    }


}

