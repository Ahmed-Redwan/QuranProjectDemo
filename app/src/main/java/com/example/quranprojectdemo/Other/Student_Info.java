package com.example.quranprojectdemo.Other;

public class Student_Info {
    private String name;
    private int id_number;
    private String phoneNo;
    private String email;
    private String academic_level;
    private String birth_date;

    public Student_Info(String name, int id_number, String phoneNo, String email, String academic_level, String birth_date) {
        this.name = name;
        this.id_number = id_number;
        this.phoneNo = phoneNo;
        this.email = email;
        this.academic_level = academic_level;
        this.birth_date = birth_date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId_number() {
        return id_number;
    }

    public void setId_number(int id_number) {
        this.id_number = id_number;
    }

    public String getPhoneNo() {
        return phoneNo;
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
