package com.payload;

public class OwnerRidePayload {
    private int ownerId;
    private int noOfSeats;
    private String startPoint;
    private String inter1Point;
    private String inter2Point;
    private String inter3Point;
    private String inter4Point;
    private String endPoint;
    private String carName;

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public OwnerRidePayload() {
    }

    public OwnerRidePayload(int ownerId, int noOfSeats, String startPoint, String inter1Point, String inter2Point, String inter3Point, String inter4Point, String endPoint, String carName) {
        this.ownerId = ownerId;
        this.noOfSeats = noOfSeats;
        this.startPoint = startPoint;
        this.inter1Point = inter1Point;
        this.inter2Point = inter2Point;
        this.inter3Point = inter3Point;
        this.inter4Point = inter4Point;
        this.endPoint = endPoint;
        this.carName = carName;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
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

    public String getInter1Point() {
        return inter1Point;
    }

    public void setInter1Point(String inter1Point) {
        this.inter1Point = inter1Point;
    }

    public String getInter2Point() {
        return inter2Point;
    }

    public void setInter2Point(String inter2Point) {
        this.inter2Point = inter2Point;
    }

    public String getInter3Point() {
        return inter3Point;
    }

    public void setInter3Point(String inter3Point) {
        this.inter3Point = inter3Point;
    }

    public String getInter4Point() {
        return inter4Point;
    }

    public void setInter4Point(String inter4Point) {
        this.inter4Point = inter4Point;
    }

    public String getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }
}
