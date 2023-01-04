package com.example.boltdogapp.model;

public class Request {
    String petsitter;
    String ownername;
    String petname;

    public Request(String petsitter, String ownername, String petname) {
        this.petsitter = petsitter;
        this.ownername = ownername;
        this.petname = petname;
    }

    public Request() {
    }

    public String getPetsitter() {
        return petsitter;
    }

    public void setPetsitter(String petsitter) {
        this.petsitter = petsitter;
    }

    public String getOwnername() {
        return ownername;
    }

    public void setOwnername(String ownername) {
        this.ownername = ownername;
    }

    public String getPetname() {
        return petname;
    }

    public void setPetname(String petname) {
        this.petname = petname;
    }
}
