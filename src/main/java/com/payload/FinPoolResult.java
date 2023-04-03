package com.payload;

import com.model.Car;
import com.model.Owner;
import com.model.Ride;

import java.sql.Timestamp;
import java.util.List;

public class FinPoolResult {
   private Ride ride;
   private Owner owner;
   private Car car;
   private List<String> citiesOfRide;
    public FinPoolResult() {
    }

    public FinPoolResult(Ride ride, Owner owner, Car car, List<String> citiesOfRide) {
        this.ride = ride;
        this.owner = owner;
        this.car = car;
        this.citiesOfRide = citiesOfRide;
    }

    public Ride getRide() {
        return ride;
    }

    public void setRide(Ride ride) {
        this.ride = ride;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public List<String> getCitiesOfRide() {
        return citiesOfRide;
    }

    public void setCitiesOfRide(List<String> citiesOfRide) {
        this.citiesOfRide = citiesOfRide;
    }
}
