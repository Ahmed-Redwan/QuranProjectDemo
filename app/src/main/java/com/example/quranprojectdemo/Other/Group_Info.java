package com.example.quranprojectdemo.Other;

public class Group_Info {
    private String email;
    private String group_name;
    private String password;
    private String phone;
    private String teacher_name;

    public Group_Info(String email, String group_name, String password, String phone, String teacher_name) {
        this.email = email;
        this.group_name = group_name;
        this.password = password;
        this.phone = phone;
        this.teacher_name = teacher_name;
    }

    public Group_Info() {

    }

        public String getEmail() {
                return email;
        }

        public void setEmail(String email) {
                this.email = email;
        }

        public String getGroup_name() {
                return group_name;
        }

        public void setGroup_name(String group_name) {
                this.group_name = group_name;
        }

        public String getPassword() {
                return password;
        }

        public void setPassword(String password) {
                this.password = password;
        }

        public String getPhone() {
                return phone;
        }

        public void setPhone(String phone) {
                this.phone = phone;
        }

        public String getTeacher_name() {
                return teacher_name;
        }

        public void setTeacher_name(String teacher_name) {
                this.teacher_name = teacher_name;
        }
}
