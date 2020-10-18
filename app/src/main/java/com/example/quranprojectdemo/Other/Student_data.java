package com.example.quranprojectdemo.Other;

import io.realm.RealmObject;
import io.realm.annotations.Index;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class Student_data extends RealmObject {


    private String date__student;

    private String day_student;

    private String save_student;

    private String review_student;

    private String attendess_student;
    private double counnt_page_save;
        
    private double counnt_page_review;

    private String month_save;

    private String year_save;
    private long time_save;

    private String id_student;
    @PrimaryKey
    private String date_id;
    private String id_group;

    public Student_data(String date__student,
                        String day_student, String save_student,
                        String review_student, String attendess_student,
                        double counnt_page_save, double counnt_page_review, String month_save,
                        String year_save, long time_save, String id_student, String date_id, String id_group) {
        this.date__student = date__student;
        this.day_student = day_student;
        this.save_student = save_student;
        this.review_student = review_student;
        this.attendess_student = attendess_student;
        this.counnt_page_save = counnt_page_save;
        this.counnt_page_review = counnt_page_review;
        this.month_save = month_save;
        this.year_save = year_save;
        this.time_save = time_save;
        this.id_student = id_student;
        this.date_id = date_id;
        this.id_group = id_group;
    }

//
//    public Student_data(String date__student, String day_student, String save_student,
//                        String review_student, String attendess_student, double counnt_page_save, double counnt_page_review) {
//
//        this.date__student = date__student;
//        this.day_student = day_student;
//        this.save_student = save_student;
//        this.review_student = review_student;
//        this.attendess_student = attendess_student;
//        this.counnt_page_save = counnt_page_save;
//        this.counnt_page_review = counnt_page_review;
//    }

    public Student_data() {

    }

    public String getDate__student() {
        return date__student;
    }

    public void setDate__student(String date__student) {
        this.date__student = date__student;
    }

    public String getDay_student() {
        return day_student;
    }

    public void setDay_student(String day_student) {
        this.day_student = day_student;
    }

    public String getSave_student() {
        return save_student;
    }

    public void setSave_student(String save_student) {
        this.save_student = save_student;
    }

    public String getReview_student() {
        return review_student;
    }

    public void setReview_student(String review_student) {
        this.review_student = review_student;
    }

    public String getAttendess_student() {
        return attendess_student;
    }

    public void setAttendess_student(String attendess_student) {
        this.attendess_student = attendess_student;
    }

    public double getCounnt_page_save() {
        return counnt_page_save;
    }

    public void setCounnt_page_save(double counnt_page_save) {
        this.counnt_page_save = counnt_page_save;
    }

    public double getCounnt_page_review() {
        return counnt_page_review;
    }

    public void setCounnt_page_review(double counnt_page_review) {
        this.counnt_page_review = counnt_page_review;
    }

    public String getMonth_save() {
        return month_save;
    }

    public void setMonth_save(String month_save) {
        this.month_save = month_save;
    }

    public String getYear_save() {
        return year_save;
    }

    public void setYear_save(String year_save) {
        this.year_save = year_save;
    }

    public long getTime_save() {
        return time_save;
    }

    public void setTime_save(long time_save) {
        this.time_save = time_save;
    }

    public String getId_student() {
        return id_student;
    }

    public void setId_student(String id_student) {
        this.id_student = id_student;
    }

    public String getDate_id() {
        return date_id;
    }

    public void setDate_id(String date_id) {
        this.date_id = date_id;
    }

    public String getId_group() {
        return id_group;
    }

    public void setId_group(String id_group) {
        this.id_group = id_group;
    }
}


