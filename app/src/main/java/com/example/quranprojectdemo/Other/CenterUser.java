package com.example.quranprojectdemo.Other;

public class CenterUser {
    private String CenterName;
    private String ManagerName;
    private String phone;
    private String Email;
    private String Country;
    private String City;
    private String Address;
    private String Password;
    private int id;

    public CenterUser(String centerName, String managerName, String phone, String email, String country, String city, String address, String password,int id) {
        CenterName = centerName;
        ManagerName = managerName;
        this.phone = phone;
        Email = email;
        Country = country;
        City = city;
        Address = address;
        Password = password;
        this.id=id;
    }

    public String getCenterName() {
        return CenterName;
    }

    public void setCenterName(String centerName) {
        CenterName = centerName;
    }

    public String getManagerName() {
        return ManagerName;
    }

    public void setManagerName(String managerName) {
        ManagerName = managerName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
