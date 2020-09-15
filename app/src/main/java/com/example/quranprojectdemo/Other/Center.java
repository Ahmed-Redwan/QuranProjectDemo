package com.example.quranprojectdemo.Other;

public class Center {

    private int img;
    private String Name;
    private String phone;

    public Center(int img, String name, String phone) {
        this.img = img;
        Name = name;
        this.phone = phone;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
