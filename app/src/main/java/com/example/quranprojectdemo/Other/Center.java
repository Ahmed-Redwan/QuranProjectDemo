package com.example.quranprojectdemo.Other;

import io.realm.RealmObject;

public class Center  {
    private String id;
    private int img;
    private String Name;
    private String phone;

    public Center(int img, String name, String phone,String id) {
        this.img = img;
        Name = name;
        this.phone = phone;
        this.id=id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
