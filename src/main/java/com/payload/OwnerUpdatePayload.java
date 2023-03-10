package com.payload;

public class OwnerUpdatePayload {
    private String ownerName;
    private String ownerEmail;
    private String userName;
    private String ownerMob;

    public OwnerUpdatePayload() {
    }

    public OwnerUpdatePayload(String ownerName, String ownerEmail, String userName, String ownerMob) {
        this.ownerName = ownerName;
        this.ownerEmail = ownerEmail;
        this.userName = userName;
        this.ownerMob = ownerMob;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getOwnerEmail() {
        return ownerEmail;
    }

    public void setOwnerEmail(String ownerEmail) {
        this.ownerEmail = ownerEmail;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getOwnerMob() {
        return ownerMob;
    }

    public void setOwnerMob(String ownerMob) {
        this.ownerMob = ownerMob;
    }
}
