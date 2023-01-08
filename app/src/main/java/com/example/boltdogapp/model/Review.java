package com.example.boltdogapp.model;

import java.io.Serializable;

public class Review implements Serializable {
    private String feedback;
    private String name;

    public Review(String feedback, String name) {
        this.feedback = feedback;
        this.name = name;
    }

    public Review() {
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
