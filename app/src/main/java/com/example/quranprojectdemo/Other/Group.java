package com.example.quranprojectdemo.Other;

public class Group {
    private int img;
    private String GroupName;
    private String TeacherName;
    private int id;

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
