package com.example.quranprojectdemo.fireBase;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.quranprojectdemo.Activities.logIn.GuardianLogin;
import com.example.quranprojectdemo.models.students.Student_data;
import com.example.quranprojectdemo.models.students.Student_data_cash;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static com.example.quranprojectdemo.Activities.logIn.TeacherLogin.ID_LOGIN_TEACHER;
import static com.example.quranprojectdemo.Activities.logIn.TeacherLogin.ID_LOGIN_TEC_CENTER;
import static com.example.quranprojectdemo.Activities.logIn.TeacherLogin.INFO_TEACHER;

public class SetStudentData {
    private Context context;
     private static SetStudentData instance;
    private SharedPreferences sp;

    private SetStudentData(Context context) {
        this.context = context;

    }

    public static SetStudentData getinstance(Context context) {

        if (instance == null) {
            instance = new SetStudentData(context);
        }
        return instance;
    }


    public boolean uploadNewSave(List<Student_data_cash> data_cashes) {


        sp = context.getSharedPreferences(INFO_TEACHER, MODE_PRIVATE);
        String id_center = sp.getString(ID_LOGIN_TEC_CENTER, "-1");
        String id_groubsp = sp.getString(ID_LOGIN_TEACHER, "-1");

        if (data_cashes != null) {
            if (!data_cashes.isEmpty()) {
                FirebaseDatabase rootNode = FirebaseDatabase.getInstance();

                DatabaseReference reference = rootNode.getReference("CenterUsers")
                        .child(id_center).child("groups").child(id_groubsp);

                DatabaseReference my_student_group = reference.child("student_group");
                for (int i = 0; i < data_cashes.size(); i++) {
                    DatabaseReference student = my_student_group.child(data_cashes.get(i).getId_student());


                    DatabaseReference student_save = student.child("student_save").child(String.valueOf(data_cashes.get(i).getTime_save()));
                    Student_data s = new Student_data(data_cashes.get(i).getDate__student(), data_cashes.get(i).getDay_student()
                            , data_cashes.get(i).getSave_student(), data_cashes.get(i).getReview_student()
                            , data_cashes.get(i).getAttendess_student(), data_cashes.get(i).getCounnt_page_save(), data_cashes.get(i).getCounnt_page_review()
                            , String.valueOf(data_cashes.get(i).getMonth_save()), String.valueOf(data_cashes.get(i).getYear_save()), data_cashes.get(i).getTime_save(), data_cashes.get(i).getId_student(), data_cashes.get(i).getDate__student(), data_cashes.get(i).getId_group());

                    student_save.setValue(s);


                }
                return true;
            }
        }

        return false;
    }

    public boolean uploadOneNewSave(Student_data_cash data_cashe) {


        sp = context.getSharedPreferences(INFO_TEACHER, MODE_PRIVATE);
        String id_center = sp.getString(ID_LOGIN_TEC_CENTER, "-1");
        String id_groubsp = sp.getString(ID_LOGIN_TEACHER, "-1");

        if (data_cashe != null) {

            FirebaseDatabase rootNode = FirebaseDatabase.getInstance();

            DatabaseReference reference = rootNode.getReference("CenterUsers")
                    .child(id_center).child("groups").child(id_groubsp);

            DatabaseReference my_student_group = reference.child("student_group");

            DatabaseReference student = my_student_group.child(data_cashe.getId_student());


            DatabaseReference student_save = student.child("student_save").child(String.valueOf(data_cashe.getTime_save()));
            Student_data s = new Student_data(data_cashe.getDate__student(), data_cashe.getDay_student()
                    , data_cashe.getSave_student(), data_cashe.getReview_student()
                    , data_cashe.getAttendess_student(), data_cashe.getCounnt_page_save(), data_cashe.getCounnt_page_review()
                    , String.valueOf(data_cashe.getMonth_save()), String.valueOf(data_cashe.getYear_save()), data_cashe.getTime_save(), data_cashe.getId_student(), data_cashe.getDate__student(), data_cashe.getId_group());

            student_save.setValue(s);


            return true;

        }

        return false;
    }

}
