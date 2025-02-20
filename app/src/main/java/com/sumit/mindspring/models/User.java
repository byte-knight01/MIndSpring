package com.sumit.mindspring.models;

public class User {
    private String registrationId;
    private String email;
    private String phoneNo;
    private String name;
    private String role;

    public User() {}

    public User(String registrationId, String email, String phoneNo, String name, String role) {
        this.registrationId = registrationId;
        this.email = email;
        this.phoneNo = phoneNo;
        this.name = name;
        this.role = role;
    }

    // Getters and Setters
    public String getRegistrationId() { return registrationId; }
    public void setRegistrationId(String registrationId) { this.registrationId = registrationId; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhoneNo() { return phoneNo; }
    public void setPhoneNo(String phoneNo) { this.phoneNo = phoneNo; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}