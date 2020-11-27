package com.example.quranprojectdemo.fireBase;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.quranprojectdemo.Activities.logIn.GuardianLogin;
import com.example.quranprojectdemo.Activities.logIn.TeacherLogin;
import com.example.quranprojectdemo.models.groups.Group_Info;
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

import java.util.concurrent.Semaphore;

import static android.content.Context.MODE_PRIVATE;
import static com.example.quranprojectdemo.Activities.logIn.TeacherLogin.ID_LOGIN_TEACHER;
import static com.example.quranprojectdemo.Activities.logIn.TeacherLogin.ID_LOGIN_TEC_CENTER;
import static com.example.quranprojectdemo.Activities.logIn.TeacherLogin.INFO_TEACHER;

public class GetGroupData {
    private FirebaseAuth mAuth;
    private Context context;
    private static GetGroupData instance;
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    TeacherLogin mLogTeacherActivity;

    private GetGroupData(Context context) {
        this.context = context;
        mAuth = FirebaseAuth.getInstance();
        if (context instanceof AppCompatActivity)
            mLogTeacherActivity = new TeacherLogin();

    }

    public static GetGroupData getinstance(Context context) {

        if (instance == null) {
            instance = new GetGroupData(context);
        }
        return instance;
    }

    public Group_Info getGroupInfo() {
        final Semaphore semaphore = new Semaphore(0);
        final Group_Info[] groupInfo = {new Group_Info()};
        sp = context.getSharedPreferences(INFO_TEACHER, MODE_PRIVATE);
        final String id_group = sp.getString(ID_LOGIN_TEACHER, "a");
        final String id_center = sp.getString(ID_LOGIN_TEC_CENTER, "a");
        FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
        final DatabaseReference reference = rootNode.getReference("CenterUsers").child(id_center)
                .child("groups").child(id_group);

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                groupInfo[0] = snapshot.child("group_info").getValue(Group_Info.class);
                semaphore.release();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return groupInfo[0];
    }

    public boolean log_in(String email, String password) {
        final Semaphore semaphore = new Semaphore(0);
        final boolean[] successLogIn = {false};

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(mLogTeacherActivity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            successLogIn[0] = true;
                            sp = context.getSharedPreferences(INFO_TEACHER, MODE_PRIVATE);
                            editor = sp.edit();
                            FirebaseUser user = mAuth.getCurrentUser();
                            editor.putString(ID_LOGIN_TEACHER, user.getPhotoUrl().toString());
                            editor.putString(ID_LOGIN_TEC_CENTER, user.getDisplayName());
                            editor.commit();

                            Toast.makeText(context, user.getEmail(), Toast.LENGTH_SHORT).show();
                            FancyToast.makeText(context, "تم تسجيل الدخول بنجاح.", FancyToast.LENGTH_LONG,
                                    FancyToast.SUCCESS, false).show();
                            semaphore.release();
                        }
                    }
                });
        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return successLogIn[0];
    }

}
