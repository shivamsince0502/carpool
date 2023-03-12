package com.payload;

public class PoolerJourneyPayload {
    private String start;
    private String end;
    private String dateOfJourney;

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

    public PoolerJourneyPayload(String start, String end, String dateOfJourney) {
        this.start = start;
        this.end = end;
        this.dateOfJourney = dateOfJourney;
    }
}