package com.example.quranprojectdemo.Other;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class Group_Info extends RealmObject {
    @Required
    private String email;
    @Required
    private String group_name;
    @Required
    private String password;
    @Required
    private String phone;
    @Required
    private String teacher_name;
    @PrimaryKey
    private String group_id;

    private String center_id;

    private String auto_sutdent_id;

    public Group_Info(String email, String group_name, String password, String phone, String teacher_name, String group_id, String center_id,
                      String auto_sutdent_id) {
        this.email = email;
        this.auto_sutdent_id = auto_sutdent_id;
        this.center_id = center_id;
        this.group_name = group_name;
        this.password = password;
        this.phone = phone;
        this.teacher_name = teacher_name;
        this.group_id = group_id;
    }
//
//    public Group_Info(String email, String group_name, String password, String phone, String teacher_name) {
//        this.email = email;
//        this.group_name = group_name;
//        this.password = password;
//        this.phone = phone;
//        this.teacher_name = teacher_name;
//    }

    public Group_Info() {

    }

    public String getGroup_id() {
        return group_id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }

    public String getCenter_id() {
        return center_id;
    }

    public void setCenter_id(String center_id) {
        this.center_id = center_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    public String getPassword() {
        return password;
    }

    public String getAuto_sutdent_id() {
        return auto_sutdent_id;
    }

    public void setAuto_sutdent_id(String auto_sutdent_id) {
        this.auto_sutdent_id = auto_sutdent_id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTeacher_name() {
        return teacher_name;
    }

    public void setTeacher_name(String teacher_name) {
        this.teacher_name = teacher_name;
    }
}
