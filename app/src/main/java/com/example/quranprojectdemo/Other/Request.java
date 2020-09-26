package com.example.quranprojectdemo.Other;

public class Request {

    private int Img;
    private String Name;
    private String Id;
    private String Date;
    private String Email;
    private String Phone;
    private String Grade;


    public Request(int img, String name, String id, String date, String email, String grade,String phone) {
        Img = img;
        Name = name;
        Id = id;
        Date = date;
        Email = email;
        Grade = grade;
        this.Phone=phone;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public int getImg() {
        return Img;
    }

    public void setImg(int img) {
        Img = img;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getGrade() {
        return Grade;
    }

    public void setGrade(String grade) {
        Grade = grade;
    }
}
