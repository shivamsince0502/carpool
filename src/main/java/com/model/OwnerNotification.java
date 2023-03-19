package com.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ownernotification")
public class OwnerNotification {
    @Id
    @Column(name = "notification_id")
    private int notificationId;

    @Column(name = "owner_id")
    private int ownerId;

    @Column(name = "message")
    private String message;

    @Column(name = "is_read")
    private Boolean isRead;

    public OwnerNotification() {}
    public OwnerNotification(int notificationId, int ownerId, String message, Boolean isRead) {
        this.notificationId = notificationId;
        this.ownerId = ownerId;
        this.message = message;
        this.isRead = isRead;
    }

    public int getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(int notificationId) {
        this.notificationId = notificationId;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
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
