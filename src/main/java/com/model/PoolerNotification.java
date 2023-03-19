package com.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "poolernotification")
public class PoolerNotification {
    @Id
    @Column(name = "notification_id")
    private int notificationId;

    @Column(name = "pooler_id")
    private int poolerId;

    @Column(name = "message")
    private String message;

    @Column(name = "is_read")
    private  Boolean isRead;

    public PoolerNotification() {
    }

    public PoolerNotification(int notificationId, int poolerId, String message, Boolean isRead) {
        this.notificationId = notificationId;
        this.poolerId = poolerId;
        this.message = message;
        this.isRead = isRead;
    }

    public int getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(int notificationId) {
        this.notificationId = notificationId;
    }

    public int getPoolerId() {
        return poolerId;
    }

    public void setPoolerId(int poolerId) {
        this.poolerId = poolerId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getRead() {
        return isRead;
    }

    public void setRead(Boolean read) {
        isRead = read;
    }
}
