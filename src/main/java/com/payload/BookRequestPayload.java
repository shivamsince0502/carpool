package com.payload;


import java.sql.Timestamp;

public class BookRequestPayload {
    private int rideId;
    private int poolerId;

    private String start;
    private String end;

    private long dateOfJourney;

    public BookRequestPayload() {
    }

    public BookRequestPayload(int rideId, int poolerId, String start, String end, long dateOfJourney) {
        this.rideId = rideId;
        this.poolerId = poolerId;
        this.start = start;
        this.end = end;
        this.dateOfJourney = dateOfJourney;
    }

    public long getDateOfJourney() {
        return dateOfJourney;
    }

    public void setDateOfJourney(long dateOfJourney) {
        this.dateOfJourney = dateOfJourney;
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
