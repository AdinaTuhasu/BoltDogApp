package com.example.boltdogapp.model;

import java.io.Serializable;

public class Review implements Serializable {
    private String feedback;
    private String name;
    private float stars_number;
    public Review(String feedback, String name) {
        this.feedback = feedback;
        this.name = name;
    }

    public Review(String feedback, String name, float stars_number) {
        this.feedback = feedback;
        this.name = name;
        this.stars_number = stars_number;
    }

    public Review() {
    }

    public float getStars_number() {
        return stars_number;
    }

    public void setStars_number(float stars_number) {
        this.stars_number = stars_number;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Review{" +
                "feedback='" + feedback + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
