package com.B21DCVT398.test.model;


public class User {
    private String username;
    private String password;
    private String fullname;
    private String email;


    public User(String username, String password,String fullname,String email) {
        this.username = username;
        this.password = password;
        this.fullname= fullname;
        this.email=email;
    }


    public String getUsername() {
        return username;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public String getFullname() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void update(String password, String fullname, String email){
        this.password = password;
        this.fullname = fullname;
        this.email = email;
    }
    public String getPassword() {
        return password;
    }


    public void setPassword(String password) {
        this.password = password;
    }

}
