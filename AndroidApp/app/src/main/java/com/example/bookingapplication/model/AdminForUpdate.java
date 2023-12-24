package com.example.bookingapplication.model;

public class AdminForUpdate {
    private String email;
    private String newPassword;
    private String oldPassword;
    public AdminForUpdate(String email,String newPassword,String oldPassword){
        this.email = email;
        this.newPassword = newPassword;
        this.oldPassword = oldPassword;
    }
}
