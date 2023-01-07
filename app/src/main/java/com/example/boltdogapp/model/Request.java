package com.example.boltdogapp.model;

public class Request {
    String petsitter;
    String ownername;
    String petname;
    String status;
    public Request(String petsitter, String ownername, String petname,String status) {
        this.petsitter = petsitter;
        this.ownername = ownername;
        this.petname = petname;
        this.status=status;
    }

    public Request() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
