package com.example.bookingapplication.model;

import com.example.bookingapplication.model.enums.UserRole;

public class User {

    private Long id;
    private String email;
    private String password;
    private UserRole userRole;
    private String jwt;

    public User() {
    }

    public User(Long id, String email, String password, UserRole userRole, String jwt) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.userRole = userRole;
        this.jwt = jwt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }
}
