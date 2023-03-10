package com.model;
import javax.persistence.*;
@Entity
@Table(name = "pooler")
public class Pooler {
    @Id
    @Column(name = "pooler_id")
    private int poolerId;
    @Column(name = "pooler_name")
    private String poolerName;

    @Column(name = "pooler_email")
    private String poolerEmail;

    @Column(name = "pooler_mob")
    private String poolerMob;

    @Column(name = "pooler_username")
    private String userName;

    @Column(name = "pooler_password")
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

    public int getPoolerId() {
        return poolerId;
    }

    public void setPoolerId(int poolerId) {
        this.poolerId = poolerId;
    }

    public String getPoolerName() {
        return poolerName;
    }

    public void setPoolerName(String poolerName) {
        this.poolerName = poolerName;
    }

    public String getPoolerEmail() {
        return poolerEmail;
    }

    public void setPoolerEmail(String poolerEmail) {
        this.poolerEmail = poolerEmail;
    }

    public String getPoolerMob() {
        return poolerMob;
    }

    public void setPoolerMob(String poolerMob) {
        this.poolerMob = poolerMob;
    }

    public Pooler() {
    }

    public Pooler(int poolerId, String poolerName, String poolerEmail, String poolerMob, String userName, String password) {
        this.poolerId = poolerId;
        this.poolerName = poolerName;
        this.poolerEmail = poolerEmail;
        this.poolerMob = poolerMob;
        this.userName = userName;
        this.password = password;
    }
}
