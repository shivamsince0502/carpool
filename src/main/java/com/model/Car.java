package com.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "car")
public class Car {
    @Id
    @Column(name = "car_id")
    private int carId;

    @Column(name = "car_name")
    private String carName;

    @Column(name = "car_color")
    private String carColor;

    @Column(name = "car_number")
    private String carNumber;
    public Car() {}
    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public String getCarColor() {
        return carColor;
    }

    public void setCarColor(String carColor) {
        this.carColor = carColor;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public Car(int carId, String carName, String carColor, String carNumber) {
        this.carId = carId;
        this.carName = carName;
        this.carColor = carColor;
        this.carNumber = carNumber;
    }
}
