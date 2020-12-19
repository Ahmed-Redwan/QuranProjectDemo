package com.example.quranprojectdemo.fireBase;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quranprojectdemo.Activities.logIn.GuardianLogin;
import com.example.quranprojectdemo.Activities.logIn.QuranCenter_Login;
import com.example.quranprojectdemo.Activities.mainActivity.Main_center;
import com.example.quranprojectdemo.Activities.registrar.QuranCenter_Reg;
import com.example.quranprojectdemo.models.centers.CenterUser;
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
import static com.example.quranprojectdemo.Activities.logIn.QuranCenter_Login.ID_CENTER_LOGIN;
import static com.example.quranprojectdemo.Activities.logIn.QuranCenter_Login.INFO_CENTER_LOGIN;
import static com.example.quranprojectdemo.Activities.registrar.QuranCenter_Reg.ID_CENTER_REG;
import static com.example.quranprojectdemo.Activities.registrar.QuranCenter_Reg.INFO_CENTER_REG;

public class GetCenterData {

    private final FirebaseAuth mAuth;
    private final Context context;
    @SuppressLint("StaticFieldLeak")
    private static GetCenterData instance;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    QuranCenter_Reg mAppContext_Reg;
    QuranCenter_Login mAppContext_login;

    private GetCenterData(Context context) {
        this.context = context;
        mAuth = FirebaseAuth.getInstance();
        if (context instanceof AppCompatActivity)
            mAppContext_Reg = new QuranCenter_Reg();
        if (context instanceof AppCompatActivity)
            mAppContext_login = new QuranCenter_Login();

    }

    public static GetCenterData getinstance(Context context) {

        if (instance == null) {
            instance = new GetCenterData(context);
        }
        return instance;
    }

    public CenterUser getCenterInfo(String centerId) {
        final CenterUser[] centerUser = {new CenterUser()};
        final Semaphore semaphore = new Semaphore(0);
        FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
        final DatabaseReference reference = rootNode.getReference("CenterUsers").
                child(centerId).child("Center information");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                centerUser[0] = snapshot.getValue(CenterUser.class);


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
        return centerUser[0];
    }//جلب البيانات

    public String logIn(String email, String password) {
        final String[] isLoggend = {null};
        final Semaphore semaphore = new Semaphore(0);


        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(mAppContext_login, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            isLoggend[0] = user.getUid();
                            sp = context.getSharedPreferences(INFO_CENTER_LOGIN, MODE_PRIVATE);
                            editor = sp.edit();
                            Log.d("aaaaa", "this is log in " + isLoggend[0]);
                            editor.putString(ID_CENTER_LOGIN, isLoggend[0]);


                            editor.apply();
                            sp = context.getSharedPreferences(INFO_CENTER_REG, MODE_PRIVATE);
                            editor = sp.edit();
                            editor.clear();
                            editor.commit();
                            FancyToast.makeText(context, "تم تسجيل الدخول بنجاح.", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
                            semaphore.release();
                        }
                    }
                });
        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return isLoggend[0];
    }

    public String signUp(String email, String password, final CenterUser centerUser) {
        final String[] isLoggend = {null};
        final Semaphore semaphore = new Semaphore(0);

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(mAppContext_Reg, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            isLoggend[0] = mAuth.getUid().toString();
                            centerUser.setId(isLoggend[0]);
                            centerUser.setAuto_id_group("00");

                            sp = context.getSharedPreferences(INFO_CENTER_REG, MODE_PRIVATE);
                            editor = sp.edit();
                            editor.putString(ID_CENTER_REG, user.getUid());
                            editor.apply();

                            FancyToast.makeText(context, "تم إنشاء الحساب بنجاح", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
                            semaphore.release();

                        }
                    }
                });
        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return isLoggend[0];
    }

    public void uploadCenterDataToFireBase(CenterUser centeruser, String centerId) {

        FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
        final DatabaseReference reference = rootNode.getReference("CenterUsers").child(centerId).child("Center information");
        DatabaseReference reference2 = rootNode.getReference("Countries").child(centeruser.getCountry())
                .child(centeruser.getCity()).child(mAuth.getUid());
        reference2.setValue(centeruser);
        reference.setValue(centeruser);


    }
}
