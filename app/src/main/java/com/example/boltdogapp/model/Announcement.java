package com.example.boltdogapp.model;

import java.io.Serializable;
import java.util.Objects;
import java.util.SplittableRandom;

public class Announcement implements Serializable {
    private String ownername;
    private String name;
    private String breed;
    private int age;
    private String descripion;
    private String address;
    private String photoUrl;

    public Announcement() {
    }


    public Announcement(String ownername, String name, String breed, int age, String descripion, String address) {
        this.name = name;
        this.breed = breed;
        this.age = age;
        this.descripion = descripion;
        this.address = address;
        this.ownername = ownername;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public int getAge() {
        return age;
    }

    public String getOwnername() {
        return ownername;
    }

    public void setOwnername(String ownername) {
        this.ownername = ownername;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getDescripion() {
        return descripion;
    }

    public void setDescripion(String descripion) {
        this.descripion = descripion;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }


    @Override
    public String toString() {
        return "Announcement{" +
                "ownername='" + ownername + '\'' +
                ", name='" + name + '\'' +
                ", breed='" + breed + '\'' +
                ", age=" + age +
                ", descripion='" + descripion + '\'' +
                ", address='" + address + '\'' +
                '}';
    }


}
