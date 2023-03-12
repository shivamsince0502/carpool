package com.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "deletedride")
public class DeleteRide {
    @Id
    @Column(name = "deleteride_id")
    private int deleteRideId;

    @Column(name = "ride_id")
    private int rideId;

    public DeleteRide() {
    }

    public DeleteRide(int deleteRideId, int rideId) {
        this.deleteRideId = deleteRideId;
        this.rideId = rideId;
    }

    public int getDeleteRideId() {
        return deleteRideId;
    }

    public void setDeleteRideId(int deleteRideId) {
        this.deleteRideId = deleteRideId;
    }

    public int getRideId() {
        return rideId;
    }

    public void setRideId(int rideId) {
        this.rideId = rideId;
    }
}
