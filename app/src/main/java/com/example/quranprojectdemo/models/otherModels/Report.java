package com.example.quranprojectdemo.models.otherModels;

public class Report {

    private int num;
    private int numOfAttendanceDays;
    private int numOfNonAttendanceDays;
    private int numOfSavePages;
    private int numOfReviewPages;
    private String name;


    public Report(){
    }

    public Report(int num, int numOfAttendanceDays, int numOfNonAttendanceDays, int numOfSavePages, int numOfReviewPages, String name) {
        this.num = num;
        this.numOfAttendanceDays = numOfAttendanceDays;
        this.numOfNonAttendanceDays = numOfNonAttendanceDays;
        this.numOfSavePages = numOfSavePages;
        this.numOfReviewPages = numOfReviewPages;
        this.name = name;
    }

    public Report( int numOfAttendanceDays, int numOfNonAttendanceDays, int numOfSavePages, int numOfReviewPages) {
        this.numOfAttendanceDays = numOfAttendanceDays;
        this.numOfNonAttendanceDays = numOfNonAttendanceDays;
        this.numOfSavePages = numOfSavePages;
        this.numOfReviewPages = numOfReviewPages;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getNumOfAttendanceDays() {
        return numOfAttendanceDays;
    }

    public void setNumOfAttendanceDays(int numOfAttendanceDays) {
        this.numOfAttendanceDays = numOfAttendanceDays;
    }

    public int getNumOfNonAttendanceDays() {
        return numOfNonAttendanceDays;
    }

    public void setNumOfNonAttendanceDays(int numOfNonAttendanceDays) {
        this.numOfNonAttendanceDays = numOfNonAttendanceDays;
    }

    public int getNumOfSavePages() {
        return numOfSavePages;
    }

    public void setNumOfSavePages(int numOfSavePages) {
        this.numOfSavePages = numOfSavePages;
    }

    public int getNumOfReviewPages() {
        return numOfReviewPages;
    }

    public void setNumOfReviewPages(int numOfReviewPages) {
        this.numOfReviewPages = numOfReviewPages;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
