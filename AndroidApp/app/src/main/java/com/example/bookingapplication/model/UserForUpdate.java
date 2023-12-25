package com.example.bookingapplication.model;

public class UserForUpdate {
    private String email;
    private Address address;
    private String phoneNumber;
    private String name;
    private String lastname;
    private String oldPassword;
    private String newPassword;
    public UserForUpdate(){

    }

    public UserForUpdate(String email, Address address, String phoneNumber, String name, String lastname, String oldPassword, String newPassword) {
        this.email = email;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.name = name;
        this.lastname = lastname;
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }

    public String getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getName() {
        return name;
    }

    public String getLastname() {
        return lastname;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
