package com.payload;

import com.model.City;

import java.sql.Date;
import java.util.List;

public class RidePayloadResponse {
    private int rideId;
    private int ownerId;
    private int carId;
    private int noOfSeats;
    private Boolean isActive;
    private java.sql.Date rideDate;

    private String carName;
    private String carNumber;

    public RidePayloadResponse() {
    }

    public RidePayloadResponse(int rideId, int ownerId, int carId, int noOfSeats, Boolean isActive, Date rideDate, String carName, String carNumber, List<City> cities) {
        this.rideId = rideId;
        this.ownerId = ownerId;
        this.carId = carId;
        this.noOfSeats = noOfSeats;
        this.isActive = isActive;
        this.rideDate = rideDate;
        this.carName = carName;
        this.cities = cities;
        this.carNumber = carNumber;
    }

    List<City> cities;

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public int getRideId() {
        return rideId;
    }

    public void setRideId(int rideId) {
        this.rideId = rideId;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public int getNoOfSeats() {
        return noOfSeats;
    }

    public void setNoOfSeats(int noOfSeats) {
        this.noOfSeats = noOfSeats;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Date getRideDate() {
        return rideDate;
    }

    public void setRideDate(Date rideDate) {
        this.rideDate = rideDate;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public List<City> getCities() {
        return cities;
    }

    public void setCities(List<City> cities) {
        this.cities = cities;
    }
}
