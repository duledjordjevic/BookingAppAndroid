package com.example.bookingapplication.model;

public class UserDelete {
    private String password;
    public UserDelete(String password){
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
