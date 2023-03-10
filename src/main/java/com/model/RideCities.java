package com.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ride_cities")
public class RideCities {
    @Id
    @Column(name = "ride_cities_id")
    private int rideCitiesId;


    @Column(name = "ride_id")
    private int rideId;

    @Column(name = "city_id")
    private int cityId;
    public RideCities(){}
    public int getRideId() {
        return rideId;
    }

    public void setRideId(int rideId) {
        this.rideId = rideId;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public RideCities(int rideId, int cityId) {
        this.rideId = rideId;
        this.cityId = cityId;
    }
}
