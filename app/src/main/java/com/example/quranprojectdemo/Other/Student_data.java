package com.example.quranprojectdemo.Other;

public class Student_data {

    String date__student, day_student, save_student, review_student, attendess_student;
    double counnt_page_save ,counnt_page_review ;


    public Student_data(String date__student, String day_student, String save_student,
                        String review_student, String attendess_student, double counnt_page_save, double counnt_page_review) {

        this.date__student = date__student;
        this.day_student = day_student;
        this.save_student = save_student;
        this.review_student = review_student;
        this.attendess_student = attendess_student;
        this.counnt_page_save = counnt_page_save;
        this.counnt_page_review = counnt_page_review;
    }

    public Student_data() {

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
}


