package com.payload;

public class NewRidePayload {
    private int noOfSeats;
    private String startPoint;
    private String inter1Point;

    private String inter2Point;

    private String inter3Point;
    private String endPoint;

    public NewRidePayload() {
    }

    public NewRidePayload(int noOfSeats, String startPoint, String inter1Point, String inter2Point, String inter3Point, String endPoint) {
        this.noOfSeats = noOfSeats;
        this.startPoint = startPoint;
        this.inter1Point = inter1Point;
        this.inter2Point = inter2Point;
        this.inter3Point = inter3Point;
        this.endPoint = endPoint;
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

    public String getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }
}
