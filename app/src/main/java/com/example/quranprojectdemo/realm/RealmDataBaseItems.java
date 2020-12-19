package com.example.quranprojectdemo.realm;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import com.example.quranprojectdemo.models.students.Student_Info;
import com.example.quranprojectdemo.models.students.Student_data;
import com.example.quranprojectdemo.models.students.Student_data_cash;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmModel;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class RealmDataBaseItems {
    Context context;
    Realm realm;
    @SuppressLint("StaticFieldLeak")
    private static RealmDataBaseItems instance;

    private RealmDataBaseItems(Context context) {
        this.context = context;

    }

    public static RealmDataBaseItems getInstance(Context context) {

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
        if (object != null) {
            Realm.init(this.context);
            realm = Realm.getDefaultInstance();
            if (!realm.isInTransaction())
                realm.beginTransaction();

            realm.insertOrUpdate((RealmModel) classType.cast(object));

            realm.commitTransaction();
            if (!realm.isClosed())
                realm.close();
        }
    }

    public void insertListDataToRealm(List object) {
        Log.d("asd", object.size() + ";;;");

        if (object != null) {
            Realm.init(this.context);
            realm = Realm.getDefaultInstance();
            if (!realm.isInTransaction())
                realm.beginTransaction();

            realm.insertOrUpdate(object);

            realm.commitTransaction();


            if (!realm.isClosed())
                realm.close();
        }


    }

    public <T> List<T> getAllDataFromRealm(Class classType) {
        List list;

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

    public void deleteTable(Class classTableName) {
        Realm.init(this.context);
        realm = Realm.getDefaultInstance();
        if (!realm.isInTransaction())
            realm.beginTransaction();
        realm.delete(classTableName);

        realm.commitTransaction();
        if (!realm.isClosed())
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
                    realmResults = realm.where(classType)
                            .findAll().where().equalTo(nameType[0], value[0]).findAll();

                    list = realm.copyFromRealm(realmResults);
                    Log.d("sssss", list.size() + "size" + length + nameType[0] + " h " + value[0]);

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
        try {
            if (!realm.isClosed())
                realm.close();
        }catch (Exception exception){}

        return list;
    }


    public long[] getMaxAndMinAndCountValue(String filedType, String[] nameType, String[] value, Class classType) {
        Realm.init(this.context);
        realm = Realm.getDefaultInstance();
        RealmQuery query = realm.where(classType);
        int length = nameType.length;
        Number max;
        Number min;
        long[] arr = {0, 0, 0};

        try {

            switch (length) {
                case 0:
                    try {
                        max = query.max(filedType);
                        min = query.min(filedType);
                        arr[0] = min.intValue();
                        arr[1] = max.intValue();
                    } catch (Exception e) {
                    }


                    arr[2] = query.count();

                    Log.d("zzz", " e e " + arr[2]);

                    break;
                case 1:
                    try {
                        max = query.equalTo(nameType[0], value[0]).max(filedType);
                        min = query.equalTo(nameType[0], value[0]).max(filedType);
                        Log.d("zzz", max + " towd " + min + " e e " + arr[2]);
                        arr[0] = min.intValue();
                        arr[1] = max.intValue();
                    } catch (Exception e) {
                    }


                    arr[2] = query.equalTo(nameType[0], value[0]).count();

                    break;
                case 2:
                    try {
                        max = query.equalTo(nameType[0], value[0]).and().equalTo(nameType[1], value[1])
                                .max(filedType);
                        min = query.equalTo(nameType[0], value[0]).and().equalTo(nameType[1], value[1])
                                .max(filedType);
                        arr[1] = max.intValue();
                        arr[0] = min.intValue();

                        Log.d("zzz", max + " tr d " + min + " e e " + arr[2]);
                    } catch (Exception e) {
                    }

                    arr[2] = query.equalTo(nameType[0], value[0]).and().equalTo(nameType[1], value[1])
                            .count();

                    break;
                case 3:
                    try {
                        max = query.equalTo(nameType[0], value[0]).and().equalTo(nameType[1], value[1]).and().equalTo(nameType[2], value[2])
                                .max(filedType);
                        min = query.equalTo(nameType[0], value[0]).and().equalTo(nameType[1], value[1]).and().equalTo(nameType[2], value[2])
                                .max(filedType);

                        arr[0] = min.intValue();
                        arr[1] = max.intValue();
                    } catch (Exception e) {
                    }

                    arr[2] = query.equalTo(nameType[0], value[0]).and().equalTo(nameType[1], value[1]).and().equalTo(nameType[2], value[2])
                            .count();
                    break;
                case 4:
                    try {

                        max = query.equalTo(nameType[0], value[0]).and().equalTo(nameType[1], value[1]).and().equalTo(nameType[2], value[2]).and().equalTo(nameType[3], value[3])
                                .max(filedType);
                        min = query.equalTo(nameType[0], value[0]).and().equalTo(nameType[1], value[1]).and().equalTo(nameType[2], value[2]).and().equalTo(nameType[3], value[3])
                                .max(filedType);
                        arr[0] = min.intValue();
                        arr[1] = max.intValue();
                    } catch (Exception e) {
                    }

                    arr[2] = query.equalTo(nameType[0], value[0]).and().equalTo(nameType[1], value[1]).and().equalTo(nameType[2], value[2]).and().equalTo(nameType[3], value[3])
                            .count();

                    break;
                case 5:
                    try {

                        max = query.equalTo(nameType[0], value[0]).and().equalTo(nameType[1], value[1]).and().equalTo(nameType[2], value[2]).and().equalTo(nameType[3], value[3]).and().equalTo(nameType[4], value[4])
                                .max(filedType);
                        min = query.equalTo(nameType[0], value[0]).and().equalTo(nameType[1], value[1]).and().equalTo(nameType[2], value[2]).and().equalTo(nameType[3], value[3]).and().equalTo(nameType[4], value[4])
                                .max(filedType);
                        arr[0] = min.intValue();
                        arr[1] = max.intValue();
                    } catch (Exception e) {
                    }


                    arr[2] = query.equalTo(nameType[0], value[0]).and().equalTo(nameType[1], value[1]).and().equalTo(nameType[2], value[2]).and().equalTo(nameType[3], value[3]).and().equalTo(nameType[4], value[4])
                            .count();

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

    public boolean idEmpty(Class c) {
        Realm.init(this.context);
        realm = Realm.getDefaultInstance();
        boolean isFound;
        Object o = realm.where(c).findFirst();
        if (o == null)
            isFound = false;
        else
            isFound = true;
        if (!realm.isClosed())
            realm.close();
        return isFound;
    }

}
