package com.model;

import org.springframework.web.bind.annotation.PutMapping;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "owner_cars")
public class OwnerCar {
    @Id
    @Column(name = "owner_cars_id")
    private int ownerCarId;

    @Column(name = "owner_id")
    private int ownerId;

    @Column(name = "car_id")
    private int carId;

    public int getOwnerCarId() {
        return ownerCarId;
    }

    public void setOwnerCarId(int ownerCarId) {
        this.ownerCarId = ownerCarId;
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

    public OwnerCar(int ownerCarId, int ownerId, int carId) {
        this.ownerCarId = ownerCarId;
        this.ownerId = ownerId;
        this.carId = carId;
    }
    public OwnerCar(){}
}
