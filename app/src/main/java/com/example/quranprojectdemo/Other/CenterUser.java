package com.example.quranprojectdemo.Other;

public class CenterUser {
    private String centerName;
    private String managerName;
    private String phone;
    private String email;
    private String country;
    private String city;
    private String address;
    private String password;
    private int id;

    public CenterUser(String centerName, String managerName, String phone, String email, String country, String city, String address, String password,int id) {
        this.centerName = centerName;
        this.managerName = managerName;
        this.phone = phone;
        this.email = email;
        this.country = country;
        this.city = city;
        this.address = address;
        this.password = password;
        this.id=id;
    }

    public CenterUser() {

    }

    public String getcenterName() {
        return centerName;
    }

    public void setcenterName(String centerName) {
        this.centerName = centerName;
    }

    public String getmanagerName() {
        return managerName;
    }

    public void setmanagerName(String managerName) {
        this.managerName = managerName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getemail() {
        return email;
    }

    public void setemail(String email) {
        this.email = email;
    }

    public String getcountry() {
        return country;
    }

    public void setcountry(String country) {
        this.country = country;
    }

    public String getcity() {
        return city;
    }

    public void setcity(String city) {
        this.city = city;
    }

    public String getaddress() {
        return address;
    }

    public void setaddress(String address) {
        this.address = address;
    }

    public String getpassword() {
        return password;
    }

    public void setpassword(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
