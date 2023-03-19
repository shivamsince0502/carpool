package com.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "pool_request")
public class PoolRequest {
    @Id
    @Column(name = "poolrequest_id")
    private int poolRequestId;

    @Column(name = "ride_pooler_id")
    private int ridePoolerId;

    @Column(name = "is_approved")
    private Boolean isApproved;

    @Column(name = "is_seen")
    private Boolean isSeen;

    public PoolRequest() {
    }

    public PoolRequest(int poolRequestId, int ridePoolerId, Boolean isApproved, Boolean isSeen) {
        this.poolRequestId = poolRequestId;
        this.ridePoolerId = ridePoolerId;
        this.isApproved = isApproved;
        this.isSeen = isSeen;
    }

    public Boolean getSeen() {
        return isSeen;
    }

    public void setSeen(Boolean seen) {
        isSeen = seen;
    }

    public int getPoolRequestId() {
        return poolRequestId;
    }

    public void setPoolRequestId(int poolRequestId) {
        this.poolRequestId = poolRequestId;
    }

    public int getRidePoolerId() {
        return ridePoolerId;
    }

    public void setRidePoolerId(int ridePoolerId) {
        this.ridePoolerId = ridePoolerId;
    }

    public Boolean getApproved() {
        return isApproved;
    }

    public void setApproved(Boolean approved) {
        isApproved = approved;
    }
}
