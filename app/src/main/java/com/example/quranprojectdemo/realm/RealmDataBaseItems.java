package com.example.quranprojectdemo.realm;

import android.content.Context;

import com.example.quranprojectdemo.models.students.Student_Info;
import com.example.quranprojectdemo.models.students.Student_data;

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


    public int[] getMaxAndMinValue(String filedType, String[] nameType, String[] value, Class classType) {
        Realm.init(this.context);
        realm = Realm.getDefaultInstance();
        RealmQuery<Student_data> query = realm.where(classType);
        int length = nameType.length;
        Number max;
        Number min;
        int[] arr = {0, 0};

        try {

            switch (length) {
                case 0:
                    max = query.max("year_save");
                    min = query.min("year_save");
                    arr[0] = min.intValue();
                    arr[1] = max.intValue();
                    break;
                case 1:
                    max = realm.where(Student_data.class)
                            .equalTo(nameType[0], value[0]).max(filedType);
                    min = realm.where(Student_data.class)
                            .equalTo(nameType[0], value[0]).max(filedType);
                    arr[0] = min.intValue();
                    arr[1] = max.intValue();


                    break;
                case 2:
                    max = realm.where(Student_data.class)
                            .equalTo(nameType[0], value[0]).and().equalTo(nameType[1], value[1])
                            .max(filedType);
                    min = realm.where(Student_data.class)
                            .equalTo(nameType[0], value[0]).and().equalTo(nameType[1], value[1])
                            .max(filedType);
                    arr[0] = min.intValue();
                    arr[1] = max.intValue();
                    break;
                case 3:
                    max = realm.where(Student_data.class)
                            .equalTo(nameType[0], value[0]).and().equalTo(nameType[1], value[1]).and().equalTo(nameType[2], value[2])
                            .max(filedType);
                    min = realm.where(Student_data.class)
                            .equalTo(nameType[0], value[0]).and().equalTo(nameType[1], value[1]).and().equalTo(nameType[2], value[2])
                            .max(filedType);
                    arr[0] = min.intValue();
                    arr[1] = max.intValue();
                    break;
                case 4:

                    max = realm.where(Student_data.class)
                            .equalTo(nameType[0], value[0]).and().equalTo(nameType[1], value[1]).and().equalTo(nameType[2], value[2]).and().equalTo(nameType[3], value[3])
                            .max(filedType);
                    min = realm.where(Student_data.class)
                            .equalTo(nameType[0], value[0]).and().equalTo(nameType[1], value[1]).and().equalTo(nameType[2], value[2]).and().equalTo(nameType[3], value[3])
                            .max(filedType);
                    arr[0] = min.intValue();
                    arr[1] = max.intValue();
                    break;
                case 5:
                    max = realm.where(Student_data.class)
                            .equalTo(nameType[0], value[0]).and().equalTo(nameType[1], value[1]).and().equalTo(nameType[2], value[2]).and().equalTo(nameType[3], value[3]).and().equalTo(nameType[4], value[4])
                            .max(filedType);
                    min = realm.where(Student_data.class)
                            .equalTo(nameType[0], value[0]).and().equalTo(nameType[1], value[1]).and().equalTo(nameType[2], value[2]).and().equalTo(nameType[3], value[3]).and().equalTo(nameType[4], value[4])
                            .max(filedType);
                    arr[0] = min.intValue();
                    arr[1] = max.intValue();
                    break;

                default:
                    break;
            }


        } catch (Exception e) {


        }
        if (!realm.isClosed())
            realm.close();
        return arr;
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
