package com.example.quranprojectdemo.fireBase;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.quranprojectdemo.Activities.logIn.TeacherLogin;
import com.example.quranprojectdemo.Activities.mainActivity.Main_teacher;
import com.example.quranprojectdemo.Activities.registrar.AddNewStudent;
import com.example.quranprojectdemo.models.groups.Group_Info;
import com.example.quranprojectdemo.models.students.Student_Info;
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

import static android.content.Context.MODE_PRIVATE;

public class SetNewStudent {
    private Context context;
    private static SetNewStudent instance;
    private String id_group;
    private String id_center;
    SharedPreferences sp;
    private FirebaseAuth mAuth;
    private String auto_student_id;
    RealmDataBaseItems dataBaseItems;

    private SetNewStudent(Context context) {
        this.context = context;
        this.dataBaseItems = RealmDataBaseItems.getinstance(context);
        this.mAuth = FirebaseAuth.getInstance();

    }

    public static SetNewStudent getInstance(Context context) {
        if (instance == null) {
            instance = new SetNewStudent(context);

        }
        return instance;
    }

    public void sign_up(final String email, final String phone, final Student_Info student_info) {
        AddNewStudent mActivity = new AddNewStudent();

        mAuth.createUserWithEmailAndPassword(email, phone)
                .addOnCompleteListener(mActivity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("qqqq",email+phone+" q q q ");
                        if (task.isSuccessful()) {
                            sp = context.getSharedPreferences(TeacherLogin.INFO_TEACHER, MODE_PRIVATE);
                            id_group = sp.getString(TeacherLogin.ID_LOGIN_TEACHER, "a");
                            id_center = sp.getString(TeacherLogin.ID_LOGIN_TEC_CENTER, "a");
                            FirebaseUser user = mAuth.getCurrentUser();
                            getAutoStudentID(id_group, id_center, user, student_info);

//                            FirebaseAuth.getInstance().signOut();
                            FancyToast.makeText(context, "تم إضافة طالب جديد.", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();


                        } else {
                            FancyToast.makeText(context, "فشل في إضافة الطالب.", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();

                        }

                    }
                });


    }


    private void updatename(FirebaseUser user, String id_center1, String id_group1, String auto_student_id1) {// space = 32
        UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder()
                .setDisplayName(id_center1).setPhotoUri(Uri.parse(id_group1 + " " + auto_student_id1))
                .build();
        user.updateProfile(profileUpdate)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                });

    }

    private void create_new_student(Student_Info info, String id_student, String id_groub1, String id_center) {
        FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
        DatabaseReference reference = rootNode.getReference("CenterUsers");//already found
        DatabaseReference center = reference.child(id_center);//already found
        DatabaseReference center_groups = center.child("groups");//already found or not
        DatabaseReference new_group = center_groups.child(id_groub1);// add new group

        DatabaseReference student_group = new_group.child("student_group");
        DatabaseReference new_student = student_group.child(id_student);

        DatabaseReference student_info = new_student.child("student_info");
        student_info.setValue(info);
        if (info != null) {
            dataBaseItems.copyObjectToDataToRealm(info, Student_Info.class);
        } else Toast.makeText(context, "لم تتم الاضافة بنجاح", Toast.LENGTH_SHORT).show();

    }


    private void getAutoStudentID(final String id_group, final String id_center, final FirebaseUser user, final Student_Info studentInfo) {
        FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
        final DatabaseReference reference = rootNode.getReference("CenterUsers").child(id_center)
                .child("groups").child(id_group).child("group_info");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Group_Info val = dataSnapshot.getValue(Group_Info.class);
                auto_student_id = val.getAuto_sutdent_id();

                int id_student = Integer.parseInt(auto_student_id) + 1;
                int my_id = id_student - 1;
                String new_id_student = "";
                if (id_student < 10) {
                    new_id_student = "0" + id_student;

                } else {

                    new_id_student = "" + id_student;

                }
                val.setAuto_sutdent_id(new_id_student);

                studentInfo.setId_group(id_group);
                studentInfo.setId_Student(auto_student_id);
                studentInfo.setId_center(id_center);
                create_new_student(studentInfo, auto_student_id, id_group, id_center);

                updatename(user, id_center, id_group, auto_student_id);
                save_new_id_group(val, id_center, id_group);
                reference.removeEventListener(this);
                 context.startActivity(new Intent(context, Main_teacher.class));

            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }


        });
    }

    private void save_new_id_group(Group_Info group_info, String center_id, String group_id) {
        FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
        final DatabaseReference reference = rootNode.getReference("CenterUsers").child(center_id).
                child("groups").child(group_id).child("group_info");
        reference.setValue(group_info);

    }


}
