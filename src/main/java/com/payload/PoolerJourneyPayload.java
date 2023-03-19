package com.payload;

public class PoolerJourneyPayload {
    private String start;
    private String end;
    private String dateOfJourney;
    private String endOfJourney;

    public String getEndDateOfJourney() {
        return endOfJourney;
    }

    public void setEndDateOfJourney(String endDateOfJourney) {
        this.endOfJourney = endDateOfJourney;
    }

    public String getDateOfJourney() {
        return dateOfJourney;
    }

    public void setDateOfJourney(String dateOfJourney) {
        this.dateOfJourney = dateOfJourney;
    }

    public PoolerJourneyPayload(){}
    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public PoolerJourneyPayload(String start, String end, String dateOfJourney, String endDateOfJourney) {
        this.start = start;
        this.end = end;
        this.dateOfJourney = dateOfJourney;
        this.endOfJourney = endDateOfJourney;
    }
}
