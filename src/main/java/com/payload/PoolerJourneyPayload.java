package com.payload;

public class PoolerJourneyPayload {
    private String start;
    private String end;

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

    public PoolerJourneyPayload(String start, String end) {
        this.start = start;
        this.end = end;
    }
}
