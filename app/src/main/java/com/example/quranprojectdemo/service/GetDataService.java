package com.example.quranprojectdemo.service;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;
import android.util.Log;

import com.example.quranprojectdemo.Activities.logIn.QuranCenter_Login;
import com.example.quranprojectdemo.Activities.registrar.QuranCenter_Reg;
import com.example.quranprojectdemo.fireBase.GetCenterData;
import com.example.quranprojectdemo.fireBase.GetGroupData;
import com.example.quranprojectdemo.fireBase.GetStudentData;
import com.example.quranprojectdemo.fireBase.SetStudentData;
import com.example.quranprojectdemo.models.centers.CenterUser;
import com.example.quranprojectdemo.models.groups.Group_Info;
import com.example.quranprojectdemo.models.students.Student_Info;
import com.example.quranprojectdemo.models.students.Student_data;
import com.example.quranprojectdemo.models.students.Student_data_cash;
import com.example.quranprojectdemo.realm.RealmDataBaseItems;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import static com.example.quranprojectdemo.Activities.logIn.QuranCenter_Login.INFO_CENTER_LOGIN;
import static com.example.quranprojectdemo.Activities.otherActivity.SplashScreen.CHEACKHOWISLOGGED;
import static com.example.quranprojectdemo.Activities.otherActivity.SplashScreen.HOWISLOGGED;

public class GetDataService extends Service {
    private SharedPreferences sp;
    private String centerId = null;

    public GetDataService() {


    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                sp = getSharedPreferences(CHEACKHOWISLOGGED, MODE_PRIVATE);
                int howIsLogged = sp.getInt(HOWISLOGGED, -1);

                if (haveANewtWork()) {

                    switch (howIsLogged) {
                        case 0:
                            centerId = getSharedPreferences(INFO_CENTER_LOGIN, MODE_PRIVATE).
                                    getString(QuranCenter_Login.ID_CENTER_LOGIN, "a");
                            if (centerId.equals("a")) {

                                centerId = getSharedPreferences(QuranCenter_Reg.INFO_CENTER_REG, MODE_PRIVATE).
                                        getString(QuranCenter_Reg.ID_CENTER_REG, "a");
                            }
                            try {
                                getDataCenter();

                            } catch (Exception e) {

                                try {
                                    getDataCenter();

                                } catch (Exception e1) {
                                }
                            }
                            break;
                        case 1:
                            try {
                                getDataGroup();

                            } catch (Exception e) {
                                try {
                                    getDataGroup();

                                } catch (Exception eq) {

                                }
                            }
                            try {

                            } catch (Exception e) {

                                try {
                                    uploadSaveData();


                                } catch (Exception e1) {
                                    uploadSaveData();
                                }
                            }
                            break;
                        case 2:
                            try {
                                getDataStudent();

                            } catch (Exception e) {
                                try {
                                    getDataStudent();

                                } catch (Exception e1) {
                                }


                            }
                            break;

                    }

                }
            }
        }).start();

        return START_NOT_STICKY;
    }

    private void getDataCenter() {
        RealmDataBaseItems dataBaseItems = RealmDataBaseItems.getInstance(getBaseContext());
        GetStudentData getStudentData = GetStudentData.getinstance(getBaseContext());
        GetCenterData getCenterData = GetCenterData.getinstance(getBaseContext());
        if (centerId != null) {
            CenterUser centerUser = getCenterData.getCenterInfo(centerId);
            if (centerUser != null)
                dataBaseItems.insertObjectToDataToRealm(centerUser, CenterUser.class);
        }
        List<Group_Info> group_infos = getStudentData.getAllGroupInfoToCenter();
        if (group_infos != null) {
            if (!group_infos.isEmpty())
                dataBaseItems.insertListDataToRealm(group_infos);
        }
        List<Student_Info> studentInfos = getStudentData.getNewStudentInfoToCenter();
        if (studentInfos != null && !studentInfos.isEmpty())
            dataBaseItems.insertListDataToRealm(studentInfos);

        List<Student_data> studentData = getStudentData.getNewStudentsSaveToCenter();
        if (studentData != null && !studentData.isEmpty())
            dataBaseItems.insertListDataToRealm(studentData);


    }

    private void getDataGroup() {

        RealmDataBaseItems dataBaseItems = RealmDataBaseItems.getInstance(getBaseContext());
        GetStudentData getStudentData = GetStudentData.getinstance(getBaseContext());
        GetGroupData getGroupData = GetGroupData.getinstance(getBaseContext());

        String[] typeName = {};
        String[] value = {};


        long[] maxMin = dataBaseItems.getMaxAndMinAndCountValue("id_Student", typeName, value, Student_Info.class);
        int max = (int) maxMin[1];
        int count = (int) maxMin[2];

        List<Student_Info> infoList = getStudentData.getNewStudentInfoToGroup(count, max);
        assert infoList != null;
        dataBaseItems.insertListDataToRealm(infoList);
        Group_Info groupInfo = getGroupData.getGroupInfo();
        if (groupInfo != null)
            dataBaseItems.insertObjectToDataToRealm(groupInfo, Group_Info.class);
        if (Integer.parseInt(groupInfo.getAuto_sutdent_id()) > 0) {
            boolean isEmpty = dataBaseItems.idEmpty(Student_data.class);
            if (isEmpty) {
                List<Student_data> studentDataList = getStudentData.getAllStudentSaveToGroup();
                if (studentDataList != null) {
                    if (!studentDataList.isEmpty()) {

                        dataBaseItems.insertListDataToRealm(studentDataList);
                    }
                }
            }
        }


    }

    private void getDataStudent() {


        RealmDataBaseItems dataBaseItems = RealmDataBaseItems.getInstance(getBaseContext());
        GetStudentData getStudentData = GetStudentData.getinstance(getBaseContext());
        String[] typeName = {};
        String[] value = {};
        long[] maxMinCount = dataBaseItems.getMaxAndMinAndCountValue("time_save", typeName, value, Student_data.class);
        int count = (int) maxMinCount[2];
        List<Student_data> studentDataList = getStudentData.getNewSaveToStudent(count);
        if (studentDataList != null) {
            if (!studentDataList.isEmpty())
                dataBaseItems.insertListDataToRealm(studentDataList);
        }


    }

    private void uploadSaveData() {

        RealmDataBaseItems dataBaseItems = RealmDataBaseItems.getInstance(getBaseContext());
        SetStudentData setStudentData = SetStudentData.getinstance(getBaseContext());
        List<Student_data_cash> student_data_cashes = dataBaseItems.getAllDataFromRealm(Student_data_cash.class);
        if (student_data_cashes != null) {
            if (!student_data_cashes.isEmpty()) {
                boolean uploaded = setStudentData.uploadNewSave(student_data_cashes);
                if (uploaded) {
                    dataBaseItems.deleteTable(Student_data_cash.class);

                }
            }
        }


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopSelf();
    }

    private boolean isOnline() {

        Runtime runtime = Runtime.getRuntime();
        try {

            Process ipProcess = null;
            try {
                ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            } catch (IOException e) {
                e.printStackTrace();
            }
            int exitValue = ipProcess.waitFor();
            return (exitValue == 0);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return false;
    }

    private boolean haveANewtWork() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        return connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED;


    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}