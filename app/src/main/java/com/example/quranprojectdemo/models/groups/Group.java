package com.example.quranprojectdemo.models.groups;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Group  extends RealmObject {
    private int img;
    private String GroupName;
    private String TeacherName;

    private int id;
    @PrimaryKey
    private String id_group;

    private String id_center;

    public Group(String id_group, String groupName, String teacherName,String id_center) {
        this.id_group = id_group;
        GroupName = groupName;
        TeacherName = teacherName;
        this.id_center=id_center;

    }
    public Group() {
    }

    public String getId_group() {
        return id_group;
    }

    public String getId_center() {
        return id_center;
    }

    public void setId_center(String id_center) {
        this.id_center = id_center;
    }

    public void setId_group(String id_group) {
        this.id_group = id_group;
    }

    public Group(String id_group, String groupName) {
        this.id_group = id_group;
        GroupName = groupName;

    }



    public Group(int img, String groupName, String teacherName,String id_group,String id_center) {
        this.img = img;
        GroupName = groupName;
        TeacherName = teacherName;
        this.id_group=id_group;
        this.id_center=id_center;
    }

    public Group(int img, String groupName, String teacherName) {
        this.img = img;
        GroupName = groupName;
        TeacherName = teacherName;
    }

    public Group(int id, int img, String groupName, String teacherName) {
        this.id = id;
        this.img = img;
        GroupName = groupName;
        TeacherName = teacherName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getImg() {//
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getGroupName() {
        return GroupName;
    }

    public void setGroupName(String groupName) {
        GroupName = groupName;
    }

    public String getTeacherName() {
        return TeacherName;
    }

    public void setTeacherName(String teacherName) {
        TeacherName = teacherName;
    }
}