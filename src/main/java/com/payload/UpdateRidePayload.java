package com.payload;

public class UpdateRidePayload {

//    {"noOfSeats":"3","rideId":"79","carName":"MG-HECTRE","dateOfJourney":"2023-03-15","ownerId":"2"}
    private  int rideId;
    private long dateOfJourney;
    private  int ownerId;
    private String carName;
    private int noOfSeats;

    public UpdateRidePayload() {
    }

    public UpdateRidePayload(int rideId, long dateOfJourney, int ownerId, String carName, int noOfSeats) {
        this.rideId = rideId;
        this.dateOfJourney = dateOfJourney;
        this.ownerId = ownerId;
        this.carName = carName;
        this.noOfSeats = noOfSeats;
    }

    public int getRideId() {
        return rideId;
    }

    public void setRideId(int rideId) {
        this.rideId = rideId;
    }

    public long getDateOfJourney() {
        return dateOfJourney;
    }

    public void setDateOfJourney(long dateOfJourney) {
        this.dateOfJourney = dateOfJourney;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public int getNoOfSeats() {
        return noOfSeats;
    }

    public void setNoOfSeats(int noOfSeats) {
        this.noOfSeats = noOfSeats;
    }
}
