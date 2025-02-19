package com.example.boltdogapp.model;

import java.io.Serializable;

public class User implements Serializable {
    private String lastname;
    private String firstname;
    private String email;
    private String phoneNr;
    private String username;
    private String password;
    private boolean petsitter;
    private String photoUrl;
    public User() {
    }

    public User(String lastname, String firstname, String email, String phoneNr, String username, String password,boolean petsitter, String photoUrl) {
        this.lastname = lastname;
        this.firstname = firstname;
        this.email = email;
        this.phoneNr = phoneNr;
        this.username = username;
        this.password = password;
        this.petsitter=petsitter;
        this.photoUrl=photoUrl;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNr() {
        return phoneNr;
    }

    public void setPhoneNr(String phoneNr) {
        this.phoneNr = phoneNr;
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

    public boolean isPetsitter() {
        return petsitter;
    }

    public void setPetsitter(boolean petsitter) {
        this.petsitter = petsitter;
    }
}
