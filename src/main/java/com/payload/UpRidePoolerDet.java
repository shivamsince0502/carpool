package com.payload;

import java.sql.Timestamp;

public class UpRidePoolerDet {
    private int rideId;
    private String carName;
    private String ownerName;
    private String ownerMob;
    private String start;
    private String end;
    private Timestamp date;
    private int poolerId;

    public int getPoolerId() {
        return poolerId;
    }

    public void setPoolerId(int poolerId) {
        this.poolerId = poolerId;
    }

    public UpRidePoolerDet(){}
    public UpRidePoolerDet(int rideId, String carName, String ownerName, String ownerMob, String start, String end, Timestamp date, int poolerId) {
        this.rideId = rideId;
        this.carName = carName;
        this.ownerName = ownerName;
        this.ownerMob = ownerMob;
        this.start = start;
        this.end = end;
        this.date = date;
        this.poolerId = poolerId;
    }

    public int getRideId() {
        return rideId;
    }

    public void setRideId(int rideId) {
        this.rideId = rideId;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getOwnerMob() {
        return ownerMob;
    }

    public void setOwnerMob(String ownerMob) {
        this.ownerMob = ownerMob;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }
}
