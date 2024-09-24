package com.B21DCVT398.test.model;

import java.time.LocalDate;

public class User {
    private String username;
    private String password;
    private String fullname;
    private String email;
    private String phone;
    private String dob;  // Date of birth stored as String
    private String gender;  // Can be "nam" or "nữ"

    public User(String username, String password, String fullname, String email, String phone, String dob, String gender) {
        this.username = username;
        this.password = password;
        this.fullname = fullname;
        this.email = email;
        this.phone = phone;
        this.dob = dob;
        this.gender = gender;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        if (gender.equalsIgnoreCase("nam") || gender.equalsIgnoreCase("nữ")) {
            this.gender = gender;
        } else {
            throw new IllegalArgumentException("Invalid gender value. Choose 'nam' or 'nữ'.");
        }
    }

    public void update(String password, String fullname, String email, String phone, String dob, String gender) {
        this.password = password;
        this.fullname = fullname;
        this.email = email;
        this.phone = phone;
        this.dob = dob;
        this.gender = gender;
    }
}
