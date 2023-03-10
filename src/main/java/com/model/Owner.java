package com.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "owner")
public class Owner {
    @Id
    @Column(name = "owner_id")
    private int ownerId;

    @Column(name = "owner_name")
    private String ownerName;

    @Column(name = "owner_email")
    private String ownerEmail;

    @Column(name = "owner_mob")
    private String ownerMob;

    @Column(name = "owner_username")
    private String userName;

    @Column(name = "owner_password")
    private String password;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
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

    public String getOwnerMob() {
        return ownerMob;
    }

    public void setOwnerMob(String ownerMob) {
        this.ownerMob = ownerMob;
    }

    public Owner() {
    }

    public Owner(int ownerId, String ownerName, String ownerEmail, String ownerMob, String userName, String password) {
        this.ownerId = ownerId;
        this.ownerName = ownerName;
        this.ownerEmail = ownerEmail;
        this.ownerMob = ownerMob;
        this.userName = userName;
        this.password = password;
    }
}
