package com.example.quranprojectdemo.Other;

public class Request {

    private int Img;
    private String name;
    private String id_number;
    private String birth_date;
    private String email;
    private String phoneNo;
    private String academic_level;

    public Request() {
    }

    public Request(int img, String name, String id_number, String birth_date, String email, String phoneNo, String academic_level) {
        Img = img;
        this.name = name;
        this.id_number = id_number;
        this.birth_date = birth_date;
        this.email = email;
        this.phoneNo = phoneNo;
        this.academic_level = academic_level;
    }

    public Request(String name, String id_number, String birth_date, String email, String phoneNo, String academic_level) {

        this.name = name;
        this.id_number = id_number;
        this.birth_date = birth_date;
        this.email = email;
        this.phoneNo = phoneNo;
        this.academic_level = academic_level;
    }

    public int getImg() {
        return Img;
    }

    public void setImg(int img) {
        Img = img;
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

    public String getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(String birth_date) {
        this.birth_date = birth_date;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getAcademic_level() {
        return academic_level;
    }

    public void setAcademic_level(String academic_level) {
        this.academic_level = academic_level;
    }
}
