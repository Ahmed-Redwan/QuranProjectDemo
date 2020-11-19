package com.example.quranprojectdemo.Activities.realm;

import android.content.Context;
import android.util.Log;

import com.example.quranprojectdemo.Other.CenterUser;
import com.example.quranprojectdemo.Other.Group_Info;
import com.example.quranprojectdemo.Other.Student_Info;
import com.example.quranprojectdemo.Other.Student_data;
import com.example.quranprojectdemo.Other.Student_data_cash;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmModel;
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


    public <T> List<T> getAllDataFromRealm(Class classType) {
        List<T> list;

        Realm.init(this.context);
        realm = Realm.getDefaultInstance();

        try {
            RealmResults realmResults = realm.where(classType).findAll();/// this is
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

    public <T> List<T> getDataWithAndStatement(String[] nameType, String[] value, Class classType) {
        int length = nameType.length;
        Realm.init(this.context);
        realm = Realm.getDefaultInstance();
        List<T> list;
        RealmResults realmResults;
        try {

            switch (length) {
                case 1:
                    realmResults = realm.where(classType).equalTo(nameType[0], value[0]).findAll();
                    list = realm.copyFromRealm(realmResults);


                    break;
                case 2:
                    realmResults = realm.where(classType).equalTo(nameType[0], value[0]).
                            and().equalTo(nameType[1], value[1])
                            .findAll();
                    list = realm.copyFromRealm(realmResults);

                    break;
                case 3:
                    realmResults = realm.where(classType).equalTo(nameType[0], value[0]).
                            and().equalTo(nameType[1], value[1])
                            .and().equalTo(nameType[2], value[2])
                            .findAll();
                    list = realm.copyFromRealm(realmResults);
                    break;
                case 4:
                    realmResults = realm.where(classType).equalTo(nameType[0], value[0]).
                            and().equalTo(nameType[1], value[1])
                            .and().equalTo(nameType[2], value[2])
                            .and().equalTo(nameType[3], value[3])
                            .findAll();
                    list = realm.copyFromRealm(realmResults);

                    break;
                case 5:
                    realmResults = realm.where(classType).equalTo(nameType[0], value[0]).
                            and().equalTo(nameType[1], value[1])
                            .and().equalTo(nameType[2], value[2])
                            .and().equalTo(nameType[3], value[3])
                            .and().equalTo(nameType[4], value[4])
                            .findAll();
                    list = realm.copyFromRealm(realmResults);
                    break;
                case 6:

                    realmResults = realm.where(classType).equalTo(nameType[0], value[0]).
                            and().equalTo(nameType[1], value[1])
                            .and().equalTo(nameType[2], value[2])
                            .and().equalTo(nameType[3], value[3])
                            .and().equalTo(nameType[4], value[4])
                            .and().equalTo(nameType[5], value[5])
                            .findAll();
                    list = realm.copyFromRealm(realmResults);
                    break;

                case 7:
                    realmResults = realm.where(classType).equalTo(nameType[0], value[0]).
                            and().equalTo(nameType[1], value[1])
                            .and().equalTo(nameType[2], value[2])
                            .and().equalTo(nameType[3], value[3])
                            .and().equalTo(nameType[4], value[4])
                            .and().equalTo(nameType[5], value[5])
                            .and().equalTo(nameType[6], value[6]).findAll();
                    list = realm.copyFromRealm(realmResults);
                    break;

                case 8:
                    realmResults = realm.where(classType).equalTo(nameType[0], value[0]).
                            and().equalTo(nameType[1], value[1])
                            .and().equalTo(nameType[2], value[2])
                            .and().equalTo(nameType[3], value[3])
                            .and().equalTo(nameType[4], value[4])
                            .and().equalTo(nameType[5], value[5])
                            .and().equalTo(nameType[6], value[6])
                            .and().equalTo(nameType[7], value[7])
                            .findAll();
                    list = realm.copyFromRealm(realmResults);
                    break;
                default:
                    list = null;

                    break;

            }


        } catch (Exception e) {
            list = null;

        }
        if (!realm.isClosed())
            realm.close();
        return list;
    }


    public boolean idNumberFound(String idNumber) {
        Realm.init(this.context);
        realm = Realm.getDefaultInstance();
        boolean isFound;
        isFound = realm.where(Student_Info.class)
                .contains("id_number", idNumber).findAll().isEmpty();


        if (!realm.isClosed())
            realm.close();
        return !isFound;
    }

}
