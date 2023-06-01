package com.example.busticketsystem;

public class User {
    private String name;
    private String email;
    private String rollNo;
    private String routeNo;
    private String password;
    private String profilePictureUrl;
    private boolean verified; // New field for verification status

    public User() {
        // Default constructor required for Firebase
    }

    public User(String name, String email, String rollNo, String routeNo, String password, String profilePictureUrl) {
        this.name = name;
        this.email = email;
        this.rollNo = rollNo;
        this.routeNo = routeNo;
        this.password = password;
        this.profilePictureUrl = profilePictureUrl;
        this.verified = false; // Set default verification status to false
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRollNo() {
        return rollNo;
    }

    public void setRollNo(String rollNo) {
        this.rollNo = rollNo;
    }

    public String getRouteNo() {
        return routeNo;
    }

    public void setRouteNo(String routeNo) {
        this.routeNo = routeNo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProfilePictureUrl() {
        return profilePictureUrl;
    }

    public void setProfilePictureUrl(String profilePictureUrl) {
        this.profilePictureUrl = profilePictureUrl;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }
}

