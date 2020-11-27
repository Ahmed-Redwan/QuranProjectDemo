package com.example.quranprojectdemo.fireBase;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quranprojectdemo.Activities.mainActivity.Main_center;
import com.example.quranprojectdemo.Activities.registrar.AddNewGroup;
import com.example.quranprojectdemo.models.centers.CenterUser;
import com.example.quranprojectdemo.models.groups.Group;
import com.example.quranprojectdemo.models.groups.Group_Info;
import com.example.quranprojectdemo.realm.RealmDataBaseItems;
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

public class SetGroupData {
    FirebaseAuth mAuth;
    private String auto_id_group = null;
    private Group_Info group_info;
    private CenterUser value;
    private Context context;
    private static SetGroupData instance;
    AddNewGroup mAppContext;

    private RealmDataBaseItems dataBaseItems;

    private SetGroupData(Context context) {
        this.context = context;
        mAuth = FirebaseAuth.getInstance();
        dataBaseItems = RealmDataBaseItems.getInstance(context);
        if (context instanceof AppCompatActivity)
            mAppContext= new AddNewGroup();
    }

    public static SetGroupData getinstance(Context context) {

        if (instance == null) {
            instance = new SetGroupData(context);
        }
        return instance;
    }

    public void sign_up(final String email, final String password, final String groupName, final String teacherName, final String phone, final String centerID) {


        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(mAppContext, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            final FirebaseUser user = mAuth.getCurrentUser();


                            FancyToast.makeText(context, "تم إضافة حلقة جديدة.",
                                    FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    getAutoIdGroup(email, password, groupName, teacherName, phone, centerID, user);


                                }
                            }).start();


                        } else {
                            Log.w("TAG", "createUserWithEmail:failure", task.getException());
                            FancyToast.makeText(context, "فشل في إضافة الحلقة.", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();

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

        reference.setValue(group_info);


    }

    public Group_Info getAutoIdGroup(final String email, final String password, final String groupName, final String teacherName, final String phone, final String centerID, final FirebaseUser user) {
        FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
        Group_Info groupInfo = new Group_Info();
        final DatabaseReference reference = rootNode.getReference("CenterUsers").child(centerID)
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


                group_info = new Group_Info(email,
                        groupName, password
                        , phone, teacherName
                        , auto_id_group, centerID, "00");
                updatename(user, centerID, auto_id_group);

                addNewGroupDataBase(group_info);
                create_new_group(group_info, centerID);
                value.setAuto_id_group(auto_id_group);
                save_new_id_group(value, centerID);

                if (value != null) {
                    dataBaseItems.insertObjectToDataToRealm(value, CenterUser.class);

                } else
                    Toast.makeText(context, "لم تنجح الاضافة", Toast.LENGTH_LONG).show();

                reference.removeEventListener(this);
                context.startActivity(new Intent(context, Main_center.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return groupInfo;
    }//جلب البيانات

    private void save_new_id_group(CenterUser centerUser, String centerID) {
        FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
        final DatabaseReference reference = rootNode.getReference("CenterUsers").child(centerID).
                child("Center information");
        reference.setValue(centerUser);

    }

    private void addNewGroupDataBase(Group_Info group_info) {
        try {
            if (group_info != null) {
                dataBaseItems.copyObjectToDataToRealm(group_info, Group.class);
            } else
                Toast.makeText(context, "لم تنجح الاضافة", Toast.LENGTH_LONG).show();
        } catch (Exception e) {


        }


    }

}
