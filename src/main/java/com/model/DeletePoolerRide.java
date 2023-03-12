package com.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "deletepoolerride")
public class DeletePoolerRide {
    @Id
    @Column(name = "deletepoolerride_id")
    private int deletePoolerRideId;

    @Column(name = "ride_pooler_id")
    private int ridePoolerId;

    public DeletePoolerRide() {
    }

    public DeletePoolerRide(int deletePoolerRideId, int ridePoolerId) {
        this.deletePoolerRideId = deletePoolerRideId;
        this.ridePoolerId = ridePoolerId;
    }

    public int getDeletePoolerRideId() {
        return deletePoolerRideId;
    }

    public void setDeletePoolerRideId(int deletePoolerRideId) {
        this.deletePoolerRideId = deletePoolerRideId;
    }

    public int getRidePoolerId() {
        return ridePoolerId;
    }

    public void setRidePoolerId(int ridePoolerId) {
        this.ridePoolerId = ridePoolerId;
    }
}
