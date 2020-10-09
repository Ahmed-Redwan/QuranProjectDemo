package com.example.quranprojectdemo.Other;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.annotations.Index;
import io.realm.annotations.PrimaryKey;

public class Student_Info extends RealmObject {
    private String name;
    @PrimaryKey
    @Index
    private String id_number;
    private String phoneNo;
    private String email;
    private String academic_level;
    private String birth_date;
    @Index
    private String id_Student;
    private String img_student;
    @Index
    private String id_center;
    @Index
    private String id_group;


    public Student_Info(String name,
                        String id_number,
                        String phoneNo, String email,
                        String academic_level, String birth_date,
                        String img_student, String id_center,
                        String id_group, String id_Student) {
        this.id_Student = id_Student;
        this.name = name;
        this.id_number = id_number;
        this.phoneNo = phoneNo;
        this.email = email;
        this.academic_level = academic_level;
        this.birth_date = birth_date;
        this.img_student = img_student;
        this.id_center = id_center;
        this.id_group = id_group;
    }

    public Student_Info() {
    }

    public Student_Info(String name, String id_Student, String img_student) {
        this.name = name;
        this.id_Student = id_Student;
        this.img_student = img_student;
    }

    public Student_Info(String img_student, String name, String id_Student, String id_group, String id_center) {
        this.name = name;
        this.id_Student = id_Student;
        this.img_student = img_student;
        this.id_center = id_center;
        this.id_group = id_group;
    }

//
//    public Student_Info(String name, String id_number, String phoneNo, String email, String academic_level, String birth_date) {
//        this.name = name;
//        this.id_number = id_number;
//        this.phoneNo = phoneNo;
//        this.email = email;
//        this.academic_level = academic_level;
//        this.birth_date = birth_date;
//    }

//    public Student_Info(String CenterId, String name, String id_number, String phoneNo, String email, String academic_level, String birth_date) {
//        this.id_center = CenterId;
//        this.name = name;
//        this.id_number = id_number;
//        this.phoneNo = phoneNo;
//        this.email = email;
//        this.academic_level = academic_level;
//        this.birth_date = birth_date;
//    }

    public String getImg_student() {
        return img_student;
    }

    public void setImg_student(String img_student) {
        this.img_student = img_student;
    }

    public String getId_center() {
        return id_center;
    }

    public void setId_center(String id_center) {
        this.id_center = id_center;
    }

    public String getId_group() {
        return id_group;
    }

    public void setId_group(String id_group) {
        this.id_group = id_group;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId_number() {
        return id_number;
    }

    public void setId_number(String id_number) {
        this.id_number = id_number;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public String getId_Student() {
        return id_Student;
    }

    public void setId_Student(String id_Student) {
        this.id_Student = id_Student;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAcademic_level() {
        return academic_level;
    }

    public void setAcademic_level(String academic_level) {
        this.academic_level = academic_level;
    }

    public String getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(String birth_date) {
        this.birth_date = birth_date;
    }
}
