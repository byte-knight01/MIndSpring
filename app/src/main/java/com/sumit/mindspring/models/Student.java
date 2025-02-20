package com.sumit.mindspring.models;

public class Student extends User {
    private String className;
    private String parentName;
    private String parentPhone;

    public Student() {
        super();
    }

    public Student(String registrationId, String email, String phoneNo, String name,
                   String className, String parentName, String parentPhone) {
        super(registrationId, email, phoneNo, name, "student");
        this.className = className;
        this.parentName = parentName;
        this.parentPhone = parentPhone;
    }

    // Getters and Setters
    public String getClassName() { return className; }
    public void setClassName(String className) { this.className = className; }
    public String getParentName() { return parentName; }
    public void setParentName(String parentName) { this.parentName = parentName; }
    public String getParentPhone() { return parentPhone; }
    public void setParentPhone(String parentPhone) { this.parentPhone = parentPhone; }
}
