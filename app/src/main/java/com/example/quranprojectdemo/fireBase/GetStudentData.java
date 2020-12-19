package com.example.quranprojectdemo.fireBase;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.quranprojectdemo.Activities.logIn.GuardianLogin;
import com.example.quranprojectdemo.Activities.logIn.QuranCenter_Login;
import com.example.quranprojectdemo.Activities.logIn.TeacherLogin;
import com.example.quranprojectdemo.Activities.registrar.QuranCenter_Reg;
import com.example.quranprojectdemo.models.CheckInternet;
import com.example.quranprojectdemo.models.groups.Group_Info;
import com.example.quranprojectdemo.models.students.Student_Info;
import com.example.quranprojectdemo.models.students.Student_data;
import com.example.quranprojectdemo.realm.RealmDataBaseItems;
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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

import static android.content.Context.MODE_PRIVATE;


public class GetStudentData {
    private final FirebaseAuth mAuth;
    private final Context context;
    private static String centerId, studentId, groupId;
    @SuppressLint("StaticFieldLeak")
    private static GetStudentData instance;
    private boolean checkIsLogged = false;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private final RealmDataBaseItems dataBaseItems;
    @SuppressLint("StaticFieldLeak")
    GuardianLogin mAppContext;

    private GetStudentData(Context context) {
        this.context = context;
        mAuth = FirebaseAuth.getInstance();
        dataBaseItems = RealmDataBaseItems.getInstance(context);

//        centerId = sp.getString(GuardianLogin.STD_ID_CENTER, "0");

        if (context instanceof AppCompatActivity)
            mAppContext = new GuardianLogin();

    }

    public static GetStudentData getinstance(Context context) {

        if (instance == null) {
            instance = new GetStudentData(context);
        }


        return instance;
    }

    CheckInternet checkInternet;

    public List<Student_data> getStudentSave(final long maxValue, final long countData) {
        final List<Student_data> studentData = new ArrayList<>();
        if (checkInternet()) {
            if (!checkIsLogged) {
                sp = context.getSharedPreferences(GuardianLogin.INFO_STUDENT_LOGIN, MODE_PRIVATE);
                centerId = sp.getString(GuardianLogin.STD_ID_CENTER, "0");
                groupId = sp.getString(GuardianLogin.STD_ID_GROUP, "-1");
                studentId = sp.getString(GuardianLogin.STD_ID_STUDENT, "-1");

            }
            final Semaphore semaphore = new Semaphore(0);

            FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
            final DatabaseReference reference = rootNode.getReference("CenterUsers")
                    .child(centerId)
                    .child("groups").child(groupId).child("student_group").child(studentId).child("student_save");
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    int count = (int) dataSnapshot.getChildrenCount();

                    if (countData != count) {
                        if (count == 0) {
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                studentData.add(snapshot.getValue(Student_data.class));
                            }

                        } else {
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                if (!snapshot.getKey().equals("report"))
                                    if (maxValue < Long.parseLong(snapshot.getKey())) {
                                        studentData.add(snapshot.getValue(Student_data.class));
                                    }
                            }

                        }
                    }
                    semaphore.release();
//                    context.finish();
//                    context.startActivity(new Intent(context, Main_student.class));
                    reference.removeEventListener(this);
                }

                @Override
                public void onCancelled(DatabaseError error) {
                }
            });
            try {
                semaphore.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return studentData;


    }

    public List<Student_data> getAllStudentSave() {
        final List<Student_data> studentData = new ArrayList<>();
        if (checkInternet()) {

            final Semaphore semaphore = new Semaphore(0);

            FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
            final DatabaseReference reference = rootNode.getReference("CenterUsers")
                    .child(centerId)
                    .child("groups").child(groupId).child("student_group").child(studentId).child("student_save");
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        studentData.add(snapshot.getValue(Student_data.class));
                    }
                    semaphore.release();
                    reference.removeEventListener(this);
                }

                @Override
                public void onCancelled(DatabaseError error) {
                }
            });
            try {
                semaphore.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return studentData;


    }

    public List<Student_data> getNewSaveToStudent(final int count) {
        final List<Student_data> studentData = new ArrayList<>();
        if (checkInternet()) {
            sp = context.getSharedPreferences(GuardianLogin.INFO_STUDENT_LOGIN, MODE_PRIVATE);
            centerId = sp.getString(GuardianLogin.STD_ID_CENTER, "0");
            groupId = sp.getString(GuardianLogin.STD_ID_GROUP, "-1");
            studentId = sp.getString(GuardianLogin.STD_ID_STUDENT, "-1");
            final Semaphore semaphore = new Semaphore(0);
            Log.d("mmmmm", centerId + "g" + groupId + "s" + studentId);
            FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
            final DatabaseReference reference = rootNode.getReference("CenterUsers")
                    .child(centerId)
                    .child("groups").child(groupId).child("student_group").child(studentId).child("student_save");
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    int countSaveInFire = (int) (dataSnapshot.getChildrenCount() - 1);
                    Log.d("mmmmm", "count" + count + " fire count : " + countSaveInFire);
                    Log.d("mmmmm", reference.getKey());

                    if (countSaveInFire > count) {
                        Log.d("mmmmm", "count" + centerId + "g" + groupId + "s" + studentId);

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            studentData.add(snapshot.getValue(Student_data.class));
                            Log.d("mmmmm", "for" + centerId + "g" + groupId + "s" + studentId);

                        }
                    }
                    semaphore.release();
                    reference.removeEventListener(this);
                }

                @Override
                public void onCancelled(DatabaseError error) {
                }
            });
            try {
                semaphore.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return studentData;


    }


    public Student_Info getStudentInfo() {
        final Student_Info[] student_info = {new Student_Info()};
        if (checkInternet()) {

            if (!checkIsLogged) {
                sp = context.getSharedPreferences(GuardianLogin.INFO_STUDENT_LOGIN, MODE_PRIVATE);
                centerId = sp.getString(GuardianLogin.STD_ID_CENTER, "0");
                groupId = sp.getString(GuardianLogin.STD_ID_GROUP, "-1");
                studentId = sp.getString(GuardianLogin.STD_ID_STUDENT, "-1");

            }
            FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
            final Semaphore semaphore = new Semaphore(0);

            final DatabaseReference reference = rootNode.getReference("CenterUsers").child(centerId)
                    .child("groups").child(groupId).child("student_group").child(
                            studentId).child("student_info");

            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    student_info[0] = dataSnapshot.getValue(Student_Info.class);
                    semaphore.release();

                    reference.removeEventListener(this);

                }

                @Override
                public void onCancelled(DatabaseError error) {
                }
            });
            try {
                semaphore.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return student_info[0];
    }


    private boolean checkInternet() {
        checkInternet = new CheckInternet();
        if (checkInternet.isConnected(context)) {
            return true;
        }
        return false;
    }

    public boolean logInStudent(final String email, final String password) {
        final boolean[] islogIn = {false};
        final Semaphore semaphore = new Semaphore(0);
//        Log.d("kkkkk", mAppContext + "this s");
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(mAppContext, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            islogIn[0] = true;


                            final FirebaseUser user = mAuth.getCurrentUser();
                            String ids = user.getPhotoUrl().toString();
                            groupId = ids.substring(0, 2);
                            studentId = ids.substring(3, ids.length());
                            centerId = user.getDisplayName();
                            sp = context.getSharedPreferences(GuardianLogin.INFO_STUDENT_LOGIN, MODE_PRIVATE);
                            editor = sp.edit();

                            editor.putString(GuardianLogin.STD_ID_STUDENT, studentId);
                            editor.putString(GuardianLogin.STD_ID_GROUP, groupId);
                            editor.putString(GuardianLogin.STD_ID_CENTER, centerId);
                            editor.commit();
                            checkIsLogged = true;
                            semaphore.release();
                            FancyToast.makeText(context, "تم تسجيل الدخول بنجاح.", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();

//                            if (islogIn[0]) {
//                            Log.d("zzzz",id_group+","+id_sutdent+","+user.getDisplayName());
//                                getStudentInfo(user.getDisplayName(), id_group, id_sutdent);
//                            }


                        }
                    }
                });


        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();

        }

        return islogIn[0];
    }

    public List<Student_Info> getAllStudentInfoToGroup() {
        final Semaphore semaphore = new Semaphore(0);
        sp = context.getSharedPreferences(TeacherLogin.INFO_TEACHER, MODE_PRIVATE);
        final List<Student_Info> studentInfos = new ArrayList<>();
        final String id_group = sp.getString(TeacherLogin.ID_LOGIN_TEACHER, "a");
        final String id_center = sp.getString(TeacherLogin.ID_LOGIN_TEC_CENTER, "a");
        FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
        final DatabaseReference reference = rootNode.getReference("CenterUsers").child(id_center)
                .child("groups").child(id_group).child("student_group");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("rrrrr", snapshot.getKey());

                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    Log.d("rrrrr", snapshot1.getKey());

                    Student_Info s = snapshot1.child("student_info").getValue(Student_Info.class);

                    if (s != null) {
                        Log.d("rrrrr", s.getName() + " t : " + s.getTokenId());

                        studentInfos.add(s);
                    }
                }
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
        return studentInfos;
    }


    public List<Student_data> getAllStudentSaveToGroup() {
        final List<Student_data> studentData = new ArrayList<>();

        final Semaphore semaphore = new Semaphore(0);
        sp = context.getSharedPreferences(TeacherLogin.INFO_TEACHER, MODE_PRIVATE);
        final String id_group = sp.getString(TeacherLogin.ID_LOGIN_TEACHER, "a");
        final String id_center = sp.getString(TeacherLogin.ID_LOGIN_TEC_CENTER, "a");
        FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
        final DatabaseReference reference = rootNode.getReference("CenterUsers").child(id_center)
                .child("groups").child(id_group).child("student_group");

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    DataSnapshot dataSnapshot = snapshot1.child("student_save");
                    for (DataSnapshot snapshot2 : dataSnapshot.getChildren()) {

                        Student_data student_data = snapshot2.getValue(Student_data.class);
                        if (student_data != null) {
                            studentData.add(student_data);


                        }
                    }
                }
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
        return studentData;


    }

    public List<Student_Info> getNewStudentInfoToGroup(final int studentCount, final int maxValue) {
        final List<Student_Info> studentInfos = new ArrayList<>();
        if (checkInternet()) {
            final Semaphore semaphore = new Semaphore(0);
            sp = context.getSharedPreferences(TeacherLogin.INFO_TEACHER, MODE_PRIVATE);
            final String id_group = sp.getString(TeacherLogin.ID_LOGIN_TEACHER, "a");
            final String id_center = sp.getString(TeacherLogin.ID_LOGIN_TEC_CENTER, "a");
            FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
            final DatabaseReference reference = rootNode.getReference("CenterUsers").child(id_center)
                    .child("groups").child(id_group).child("student_group");

            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.getChildrenCount() > studentCount) {
                        for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                            if (Integer.parseInt(snapshot1.getKey()) > maxValue) {

                                Student_Info s = snapshot1.child("student_info").getValue(Student_Info.class);
                                if (s != null)
                                    studentInfos.add(s);

                            }
                        }
                    }

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
        }
        return studentInfos;
    }

    public List<Student_data> getNewStudentsSaveToGroup(final int maxValue, final int countSaves) {
        final List<Student_data> studentData = new ArrayList<>();
        if (checkInternet()) {
            final Semaphore semaphore = new Semaphore(0);
            sp = context.getSharedPreferences(TeacherLogin.INFO_TEACHER, MODE_PRIVATE);
            final String id_group = sp.getString(TeacherLogin.ID_LOGIN_TEACHER, "a");
            final String id_center = sp.getString(TeacherLogin.ID_LOGIN_TEC_CENTER, "a");
            FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
            final DatabaseReference reference = rootNode.getReference("CenterUsers").child(id_center)
                    .child("groups").child(id_group).child("student_group");

            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        DataSnapshot dataSnapshot = snapshot1.child("student_save");
                        if ((dataSnapshot.getChildrenCount() - 1) > countSaves || true) {
                            for (DataSnapshot snapshot2 : dataSnapshot.getChildren()) {

                                if (Integer.parseInt(snapshot2.getKey()) > maxValue || true) {
                                    Student_data student_data = snapshot2.getValue(Student_data.class);
                                    if (student_data != null) {
                                        studentData.add(student_data);

                                    }

                                }
                            }
                        }
                    }
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
        }
        return studentData;


    }


    public List<Student_Info> getAllStudentInfoToCenter() {
        final Semaphore semaphore = new Semaphore(0);
        sp = context.getSharedPreferences(QuranCenter_Login.INFO_CENTER_LOGIN, MODE_PRIVATE);
        final String id_center = sp.getString(QuranCenter_Login.ID_CENTER_LOGIN, "a");
        final List<Student_Info> studentInfos = new ArrayList<>();
        FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
        final DatabaseReference reference = rootNode.getReference("CenterUsers").child(id_center)
                .child("groups");

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    DataSnapshot dataSnapshot = snapshot1.child("student_group");
                    for (DataSnapshot snapshot2 : dataSnapshot.getChildren()) {
                        Student_Info student_info = snapshot2.child("student_info").getValue(Student_Info.class);
                        if (student_info != null)
                            studentInfos.add(student_info);
                    }

                }


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
        return studentInfos;
    }

    public List<Group_Info> getAllGroupInfoToCenter() {
        final Semaphore semaphore = new Semaphore(0);
        sp = context.getSharedPreferences(QuranCenter_Login.INFO_CENTER_LOGIN, MODE_PRIVATE);
        String id_center = sp.getString(QuranCenter_Login.ID_CENTER_LOGIN, "a");
        if (id_center.equals("a")) {
            id_center = context.getSharedPreferences(QuranCenter_Reg.INFO_CENTER_REG, MODE_PRIVATE).getString(QuranCenter_Reg.ID_CENTER_REG, "a");
        }

        final List<Group_Info> group_infos = new ArrayList<>();
        FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
        final DatabaseReference reference = rootNode.getReference("CenterUsers").child(id_center)
                .child("groups");

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    Group_Info groupInfo = snapshot1.child("group_info").getValue(Group_Info.class);
                    if (groupInfo != null) {
                        group_infos.add(groupInfo);

                    }

                }


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
        return group_infos;
    }

    public List<Student_data> getAllStudentSavesToCenter() {
        final List<Student_data> studentData = new ArrayList<>();

        final Semaphore semaphore = new Semaphore(0);
        sp = context.getSharedPreferences(QuranCenter_Login.INFO_CENTER_LOGIN, MODE_PRIVATE);
        final String id_center = sp.getString(QuranCenter_Login.ID_CENTER_LOGIN, "a");
        FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
        final DatabaseReference reference = rootNode.getReference("CenterUsers").child(id_center)
                .child("groups");

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    DataSnapshot dataSnapshot = snapshot1.child("student_group");
                    for (DataSnapshot snapshot2 : dataSnapshot.getChildren()) {
                        DataSnapshot dataSnapshot1 = snapshot2.child("student_save");
                        for (DataSnapshot snapshot3 : dataSnapshot1.getChildren()) {
                            if (!snapshot3.getKey().equals("report")) {
                                Student_data student_data = snapshot3.getValue(Student_data.class);
                                if (student_data != null) {
                                    studentData.add(student_data);

                                }

                            }

                        }
                    }

                }
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
        return studentData;


    }


    public List<Student_Info> getNewStudentInfoToCenter() {
        final List<Student_Info> studentInfos = new ArrayList<>();

        if (checkInternet()) {
            final Semaphore semaphore = new Semaphore(0);
            sp = context.getSharedPreferences(QuranCenter_Login.INFO_CENTER_LOGIN, MODE_PRIVATE);
            final String id_center = sp.getString(QuranCenter_Login.ID_CENTER_LOGIN, "a");

            FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
            final DatabaseReference reference = rootNode.getReference("CenterUsers").child(id_center)
                    .child("groups");

            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    for (DataSnapshot groupsSnapShot : snapshot.getChildren()) {

                        DataSnapshot dataSnapshotStudentGroup = groupsSnapShot.child("student_group");
                        String typeName[] = {"id_group"};
                        String value[] = {groupsSnapShot.getKey()};

                        long maxMin[] = dataBaseItems.getMaxAndMinAndCountValue("id_Student", typeName, value, Student_Info.class);
                        int maxValue = (int) maxMin[1];
                        int studentCount = (int) maxMin[2];

                        if (dataSnapshotStudentGroup.getChildrenCount() > studentCount) {
                            for (DataSnapshot snapshot1 : dataSnapshotStudentGroup.getChildren()) {
                                if (Integer.parseInt(snapshot1.getKey()) > maxValue) {

                                    Student_Info s = snapshot1.child("student_info").getValue(Student_Info.class);
                                    if (s != null)
                                        studentInfos.add(s);

                                }
                            }
                        }

                    }

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
        }
        return studentInfos;
    }

    public List<Student_data> getNewStudentsSaveToCenter() {
        final List<Student_data> studentData = new ArrayList<>();
        if (checkInternet()) {
            final Semaphore semaphore = new Semaphore(0);
            sp = context.getSharedPreferences(QuranCenter_Login.INFO_CENTER_LOGIN, MODE_PRIVATE);
            final String id_center = sp.getString(QuranCenter_Login.ID_CENTER_LOGIN, "a");
            Log.d("fffff", id_center + " oops!");
            FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
            final DatabaseReference reference = rootNode.getReference("CenterUsers").child(id_center)
                    .child("groups");

            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot groupsSnapShot : snapshot.getChildren()) {

                        DataSnapshot dataSnapshotStudentGroup = groupsSnapShot.child("student_group");
                        String[] typeName = {"id_group"};
                        String[] value = {groupsSnapShot.getKey()};

                        long[] maxMin = dataBaseItems.getMaxAndMinAndCountValue("time_save", typeName, value, Student_data.class);
                        int maxValue = (int) maxMin[1];
                        int countSaves = (int) maxMin[2];

                        for (DataSnapshot snapshot1 : dataSnapshotStudentGroup.getChildren()) {
                            DataSnapshot dataSnapshot = snapshot1.child("student_save");

                            if ((dataSnapshot.getChildrenCount() - 1) > countSaves) {
                                for (DataSnapshot snapshot2 : dataSnapshot.getChildren()) {
                                    if (!snapshot2.getKey().equals("report")) {
                                        if (Integer.parseInt(snapshot2.getKey()) > maxValue) {

                                            Student_data student_data = snapshot2.getValue(Student_data.class);

                                            if (student_data != null) {
                                                studentData.add(student_data);

                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
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

        }
        return studentData;


    }

}
