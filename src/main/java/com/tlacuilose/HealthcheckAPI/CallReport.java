package com.tlacuilose.HealthcheckAPI;

public class CallReport {
    private final long id;
    private final String date;
    private final double successRate;

    public CallReport(long id, String date, double successRate) {
        this.id = id;
        this.date = date;
        this.successRate = successRate;
    }

    public long getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public double getSuccessRate() {
        return successRate;
    }
}
