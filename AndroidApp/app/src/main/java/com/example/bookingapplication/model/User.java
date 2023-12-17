package com.example.bookingapplication.model;

import com.example.bookingapplication.model.enums.UserType;

public class User {

    private Long id;
    private Address address;
    private String phoneNumber;
    private String name;
    private String lastname;
    private String email;
    private String password;
    private UserType userType;
    private String jwt;

    public User() {
    }

    public User(Long id, String email, String password, UserType userType, String jwt) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.userType = userType;
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

    public void setUserRole(UserType userType) {
        this.userType = userType;
    }

    public UserType getUserRole() {
        return userType;
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

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
}
