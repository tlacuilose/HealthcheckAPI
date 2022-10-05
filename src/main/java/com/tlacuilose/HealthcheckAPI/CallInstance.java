package com.tlacuilose.HealthcheckAPI;

public class CallInstance {
    private final long id;
    private final int status;
    private final String response;

    public CallInstance(long id, int status, String response) {
        this.id = id;
        this.status = status;
        this.response = response;
    }

    public long getId() {
        return id;
    }

    public int getStatus() {
        return status;
    }

    public String getResponse() {
        return response;
    }
}
