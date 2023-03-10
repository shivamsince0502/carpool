package com.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ride_pooler")
public class RidePooler {
    @Id
    @Column(name = "ride_pooler_id")
    private int ridePoolerId;

    @Column(name = "ride_id")
    private int rideId;

    @Column(name = "pooler_id")
    private int poolerId;

    @Column(name = "seat_no")
    private int seatNo;

    @Column(name = "is_active")
    private Boolean isActive;

    public int getRidePoolerId() {
        return ridePoolerId;
    }

    public void setRidePoolerId(int ridePoolerId) {
        this.ridePoolerId = ridePoolerId;
    }

    public int getRideId() {
        return rideId;
    }

    public void setRideId(int rideId) {
        this.rideId = rideId;
    }

    public int getPoolerId() {
        return poolerId;
    }

    public void setPoolerId(int poolerId) {
        this.poolerId = poolerId;
    }

    public int getSeatNo() {
        return seatNo;
    }

    public void setSeatNo(int seatNo) {
        this.seatNo = seatNo;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }
    public RidePooler(){}
    public RidePooler(int ridePoolerId, int rideId, int poolerId, int seatNo, Boolean isActive) {
        this.ridePoolerId = ridePoolerId;
        this.rideId = rideId;
        this.poolerId = poolerId;
        this.seatNo = seatNo;
        this.isActive = isActive;
    }
}
