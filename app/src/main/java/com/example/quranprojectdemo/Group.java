package com.example.quranprojectdemo;

public class Group {
    private int img;
    private String GroupName;
    private String TeacherName;

    public Group(int img, String groupName, String teacherName) {
        this.img = img;
        GroupName = groupName;
        TeacherName = teacherName;
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
