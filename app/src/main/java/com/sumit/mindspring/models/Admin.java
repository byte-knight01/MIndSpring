package com.sumit.mindspring.models;

public class Admin extends User {
    private String position;

    public Admin() {
        super();
    }

    public Admin(String registrationId, String email, String phoneNo, String name, String position) {
        super(registrationId, email, phoneNo, name, "admin");
        this.position = position;
    }

    // Getters and Setters
    public String getPosition() { return position; }
    public void setPosition(String position) { this.position = position; }
}
