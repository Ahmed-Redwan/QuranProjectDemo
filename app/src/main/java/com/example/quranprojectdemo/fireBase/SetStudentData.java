package com.example.quranprojectdemo.fireBase;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.quranprojectdemo.Activities.logIn.GuardianLogin;
import com.example.quranprojectdemo.models.students.Student_data;
import com.example.quranprojectdemo.models.students.Student_data_cash;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONObject;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static com.example.quranprojectdemo.Activities.logIn.TeacherLogin.ID_LOGIN_TEACHER;
import static com.example.quranprojectdemo.Activities.logIn.TeacherLogin.ID_LOGIN_TEC_CENTER;
import static com.example.quranprojectdemo.Activities.logIn.TeacherLogin.INFO_TEACHER;

public class SetStudentData {
    private final Context context;
    @SuppressLint("StaticFieldLeak")
    private static SetStudentData instance;
    private SharedPreferences sp;
    private static final String SERVER_KEY = "AAAAkRwHS54:APA91bEn3p73H7TmuMHzCQwiqlBrtD99NnHYAytBSHL6iC0bjgTXIBosoES0Qg8u5p0SdSsW6ZKEbp611nSgH6iaqMQQ7Ih3HZQfRaCbu7XhaDy5S2Q9pRAncWut0J8qeiF9D9acgdwM";

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

    public boolean uploadOneNewSave(Student_data_cash data_cashe, String token) {
        Log.d("mmmm", token + " token ");

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
                    , String.valueOf(data_cashe.getMonth_save()), String.valueOf(data_cashe.getYear_save()), data_cashe.getTime_save(), data_cashe.getId_student(), data_cashe.getDate_id(), data_cashe.getId_group());

            student_save.setValue(s);
             String title = "تسميع جديد";
            String body = "حفظ جديد: " + s.getSave_student();
            String body2 = "مراجعة: " + s.getReview_student();
            sendFireBaseNotification(SERVER_KEY, token, title, body + "\n" + body2);
            return true;

        }

        return false;
    }

    private void sendFireBaseNotification(final String keyServer, final String token, final String title, final String body) {
        Log.d("mmmm", token + " token1  1 ");

        new Thread(new Runnable() {
            @Override
            public void run() {

                try {

                    URL url = new URL("https://fcm.googleapis.com/fcm/send");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                    conn.setUseCaches(false);
                    conn.setDoInput(true);
                    conn.setDoOutput(true);

                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Authorization", "key=" + keyServer);
                    conn.setRequestProperty("Content-Type", "application/json");

                    JSONObject json = new JSONObject();

                    json.put("to", token);

                    JSONObject info = new JSONObject();
                    info.put("title", title);   // Notification title
                    info.put("body", body); // Notification body

                    json.put("notification", info);

                    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                    wr.write(json.toString());
                    wr.flush();
                    conn.getInputStream();

                } catch (Exception e) {
                    Log.d("Error", "" + e);
                }


            }
        }).start();

    }

}
