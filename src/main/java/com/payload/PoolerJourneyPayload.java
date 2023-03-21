package com.payload;

public class PoolerJourneyPayload {
    private String start;
    private String end;
    private long dateOfJourney;
    private long endOfJourney;

    public long getEndDateOfJourney() {
        return endOfJourney;
    }

    public void setEndDateOfJourney(long endDateOfJourney) {
        this.endOfJourney = endDateOfJourney;
    }

    public long getDateOfJourney() {
        return dateOfJourney;
    }

    public void setDateOfJourney(long dateOfJourney) {
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

    public PoolerJourneyPayload(String start, String end, long dateOfJourney, long endDateOfJourney) {
        this.start = start;
        this.end = end;
        this.dateOfJourney = dateOfJourney;
        this.endOfJourney = endDateOfJourney;
    }
}
