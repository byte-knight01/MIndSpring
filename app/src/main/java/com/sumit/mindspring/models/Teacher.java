package com.sumit.mindspring.models;

public class Teacher extends User {
    private String subject;

    public Teacher() {
        super();
    }

    public Teacher(String registrationId, String email, String phoneNo, String name, String subject) {
        super(registrationId, email, phoneNo, name, "teacher");
        this.subject = subject;
    }

    // Getters and Setters
    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }
}