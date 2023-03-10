package com.model;


import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "ride")
public class Ride {
    @Id
    @Column(name = "ride_id")
    private int rideId;

    @Column(name = "owner_id")
    private int ownerId;

    @Column(name = "car_id")
    private int carId;

    @Column(name = "no_of_seats")
    private int noOfSeats;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "ride_datetime")
    private Date rideDate;


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

    public Ride(){}
    public Ride(int rideId, int ownerId, int carId, int noOfSeats, Boolean isActive, Date rideDate) {
        this.rideId = rideId;
        this.ownerId = ownerId;
        this.carId = carId;
        this.noOfSeats = noOfSeats;
        this.isActive = isActive;
        this.rideDate = rideDate;
    }
}
