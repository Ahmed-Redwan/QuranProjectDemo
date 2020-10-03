package com.example.quranprojectdemo.Other;

import java.io.Serializable;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class CenterUser extends RealmObject  implements Serializable {

    @Required
    private String centerName;
    @Required
    private String managerName;
    @Required
    private String phone;
    @PrimaryKey
    @Required
    private String email;
    @Required
    private String country;
    @Required
    private String city;
    @Required
    private String address;
    @Required
    private String password;
    @Required
    private String id;

    private String auto_id_group;


    public CenterUser(String centerName, String managerName, String phone,
                      String email, String country, String city, String address, String password, String id, String auto_id_group) {
        this.centerName = centerName;
        this.managerName = managerName;
        this.phone = phone;
        this.email = email;
        this.auto_id_group = auto_id_group;
        this.country = country;
        this.city = city;
        this.address = address;
        this.password = password;
        this.id = id;
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

    public String getCenterName() {
        return centerName;
    }

    public void setCenterName(String centerName) {
        this.centerName = centerName;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAuto_id_group() {
        return auto_id_group;
    }

    public void setAuto_id_group(String auto_id_group) {
        this.auto_id_group = auto_id_group;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
