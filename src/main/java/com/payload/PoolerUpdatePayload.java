package com.payload;

public class PoolerUpdatePayload {
    private String poolerName;
    private String poolerEmail;
    private String userName;
    private String poolerMob;

    public PoolerUpdatePayload() {
    }

    public PoolerUpdatePayload(String poolerName, String poolerEmail, String userName, String poolerMob) {
        this.poolerName = poolerName;
        this.poolerEmail = poolerEmail;
        this.userName = userName;
        this.poolerMob = poolerMob;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPoolerMob() {
        return poolerMob;
    }

    public void setPoolerMob(String poolerMob) {
        this.poolerMob = poolerMob;
    }
}
