package com.payload;

import java.util.List;

public class OwnerRidePayload {
    private int noOfSeats;
    private String startPoint;
    private String carName;
    private int ownerId;
    private List<String> citiesList;

    private long dateOfJourney;

    public long getDateOfJourney() {
        return dateOfJourney;
    }

    public void setDateOfJourney(long dateOfJourney) {
        this.dateOfJourney = dateOfJourney;
    }

    public OwnerRidePayload() {
    }

    public OwnerRidePayload(int noOfSeats, String startPoint, String carName, int ownerId, List<String> citiesList, long dateOfJourney) {
        this.noOfSeats = noOfSeats;
        this.startPoint = startPoint;
        this.carName = carName;
        this.ownerId = ownerId;
        this.citiesList = citiesList;
        this.dateOfJourney = dateOfJourney;
    }

    public int getNoOfSeats() {
        return noOfSeats;
    }

    public void setNoOfSeats(int noOfSeats) {
        this.noOfSeats = noOfSeats;
    }

    public String getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(String startPoint) {
        this.startPoint = startPoint;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public List<String> getCitiesList() {
        return citiesList;
    }

    public void setCitiesList(List<String> citiesList) {
        this.citiesList = citiesList;
    }
}
