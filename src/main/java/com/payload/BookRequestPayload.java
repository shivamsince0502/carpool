package com.payload;

public class BookRequestPayload {
    private int rideId;
    private int poolerId;

    public BookRequestPayload() {
    }

    public BookRequestPayload(int rideId, int poolerId) {
        this.rideId = rideId;
        this.poolerId = poolerId;
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
}
