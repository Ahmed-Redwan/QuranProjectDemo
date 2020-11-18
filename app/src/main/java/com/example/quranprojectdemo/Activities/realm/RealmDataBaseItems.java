package com.example.quranprojectdemo.Activities.realm;

import android.content.Context;
import android.util.Log;

import com.example.quranprojectdemo.Other.CenterUser;
import com.example.quranprojectdemo.Other.Group_Info;
import com.example.quranprojectdemo.Other.Student_Info;
import com.example.quranprojectdemo.Other.Student_data;
import com.example.quranprojectdemo.Other.Student_data_cash;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.internal.operators.observable.ObservableBlockingSubscribe;
import io.realm.Realm;
import io.realm.RealmModel;
import io.realm.RealmObject;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class RealmDataBaseItems {
    Context context;
    Realm realm;
    private static RealmDataBaseItems instance;

    private RealmDataBaseItems(Context context) {
        this.context = context;

    }

    public static RealmDataBaseItems getinstance(Context context) {

        if (instance == null) {
            instance = new RealmDataBaseItems(context);
        }
        return instance;
    }

    public void copyObjectToDataToRealm(Object object, Class classType) {
        Realm.init(this.context);
        realm = Realm.getDefaultInstance();
        if (!realm.isInTransaction())
            realm.beginTransaction();

        realm.copyToRealm((RealmModel) classType.cast(object));

        realm.commitTransaction();
        if (!realm.isClosed())
            realm.close();

    }

    public void insertObjectToDataToRealm(Object object, Class classType) {
        Realm.init(this.context);
        realm = Realm.getDefaultInstance();
        if (!realm.isInTransaction())
            realm.beginTransaction();

        realm.insertOrUpdate((RealmModel) classType.cast(object));

        realm.commitTransaction();
        if (!realm.isClosed())
            realm.close();

    }


    public List<Student_Info> getAllStudentInfo() {
        Realm.init(this.context);
        realm = Realm.getDefaultInstance();
        List<Student_Info> list;
        try {
            RealmResults realmResults = realm.where(Student_Info.class).findAll();/// this is
            list = realm.copyFromRealm(realmResults);
        } catch (Exception e) {

            list = null;
        }
        if (!realm.isClosed())
            realm.close();
        return list;
    }


    public List<Student_Info> getStudentInfo(String idGroup) {
        Realm.init(this.context);
        realm = Realm.getDefaultInstance();
        List<Student_Info> list = new ArrayList<>();
        try {
            RealmResults realmResults = realm.where(Student_Info.class)
                    .equalTo("id_group", idGroup)
                    .findAll();
            list = realm.copyFromRealm(realmResults);

        } catch (Exception e) {
            list = null;

        }

        if (!realm.isClosed())
            realm.close();
        return list;
    }

    public Student_Info getStudentInfo(String id_student, String id_group) {
        Realm.init(this.context);
        realm = Realm.getDefaultInstance();
        Student_Info student_info;
        try {
            student_info = realm.where(Student_Info.class)
                    .equalTo("id_Student", id_student)
                    .and().equalTo("id_group", id_group)
                    .findFirst();
            student_info = realm.copyFromRealm(student_info);

        } catch (Exception e) {
            student_info = null;

        }

        if (!realm.isClosed())
            realm.close();
        return student_info;
    }

    public List<Student_data> getStudentData(String idGroup, String idStudent) {
        Realm.init(this.context);
        realm = Realm.getDefaultInstance();
        List<Student_data> list = new ArrayList<>();
        try {
            RealmResults realmResults = realm.where(Student_data.class)
                    .equalTo("id_student", idStudent).and().equalTo
                            ("id_group", idGroup).findAll();/// this is
            list = realm.copyFromRealm(realmResults);

        } catch (Exception e) {
            list = null;

        }
        if (!realm.isClosed())
            realm.close();
        return list;
    }

    public List<CenterUser> getAllCenterUser() {
        Realm.init(this.context);
        realm = Realm.getDefaultInstance();
        List<CenterUser> list = new ArrayList<>();
        try {
            RealmResults realmResults = realm.where(CenterUser.class).findAll();/// this is
            list = realm.copyFromRealm(realmResults);
            Log.d("rrrr", "done");

        } catch (Exception e) {
            list = null;
            Log.d("rrrr", "Not done");

        }
        if (!realm.isClosed())
            realm.close();
        return list;
    }

    public List<Group_Info> getAllGroup_Info() {
        List<Group_Info> list = new ArrayList<>();

        Realm.init(this.context);
        realm = Realm.getDefaultInstance();

        try {
            RealmResults realmResults = realm.where(Group_Info.class).findAll();/// this is
            list = realm.copyFromRealm(realmResults);

        } catch (Exception e) {
            list = null;

        }
        if (!realm.isClosed())
            realm.close();
        return list;
    }

    public List<Student_data_cash> getAllStudent_data_cash() {

        Realm.init(this.context);
        realm = Realm.getDefaultInstance();
        List<Student_data_cash> list = new ArrayList<>();
        try {
            RealmResults realmResults = realm.where(Student_data_cash.class).findAll();/// this is
            list = realm.copyFromRealm(realmResults);

            realm = Realm.getDefaultInstance();
            if (!realm.isInTransaction())
                realm.beginTransaction();
            realm.delete(Student_data_cash.class);
            realm.commitTransaction();
            realm.close();
        } catch (Exception e) {
            list = null;

        }
        if (!realm.isClosed())
            realm.close();
        return list;
    }


    public List<Student_data> getStudentData(int date_year,
                                             int date_month, String id_student, String id_group) {
        Realm.init(this.context);
        realm = Realm.getDefaultInstance();
        List<Student_data> list = new ArrayList<>();
        try {
            RealmResults realmResults = realm.where(Student_data.class)
                    .equalTo("year_save", date_year).
                            and().equalTo("month_save", date_month).and().equalTo("id_student", id_student)
                    .and().equalTo("id_group", id_group).findAll();
            list = realm.copyFromRealm(realmResults);

        } catch (Exception e) {
            list = null;

        }
        if (!realm.isClosed())
            realm.close();

        return list;
    }


    public List<Student_data> getAllStudent_data(int date_year, int date_month) {
        Realm.init(this.context);
        realm = Realm.getDefaultInstance();
        List<Student_data> list = new ArrayList<>();
        try {
            RealmResults realmResults = realm.where(Student_data.class).equalTo("year_save", date_year).
                    and().equalTo("month_save", date_month).findAll();
            list = realm.copyFromRealm(realmResults);

        } catch (Exception e) {
            list = null;

        }
        if (!realm.isClosed())
            realm.close();
        return list;
    }

    public int[] getMaxMinStudentData(int year) {
        Realm.init(this.context);
        realm = Realm.getDefaultInstance();
        RealmQuery<Student_data> query = realm.where(Student_data.class);

        int[] arr = {0, 0, 0};
        try {
            String max = query.max("year_save").toString();

            String min = query.min("year_save").toString();
            Number x = realm.where(Student_data.class)
                    .equalTo("year_save", year).max("month_save");
            arr[1] = Integer.parseInt(max);
            arr[0] = Integer.parseInt(min);
            arr[2] = x.intValue();
        } catch (Exception e) {


        }
        if (!realm.isClosed())
            realm.close();
        return arr;
    }

    public void deleteAllData() {
        Realm.init(this.context);
        realm = Realm.getDefaultInstance();
        if (!realm.isInTransaction())
            realm.beginTransaction();
        realm.deleteAll();
        realm.commitTransaction();
        realm.close();
    }
}
