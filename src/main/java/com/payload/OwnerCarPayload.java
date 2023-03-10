package com.payload;

public class OwnerCarPayload {
    private String carName;
    private String carColor;
    private String carNumber;
    private int ownerId;
public OwnerCarPayload(){}
    public OwnerCarPayload(String carName, String carColor, String carNumber, int ownerId) {
        this.carName = carName;
        this.carColor = carColor;
        this.carNumber = carNumber;
        this.ownerId = ownerId;
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

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }
}
