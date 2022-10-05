package com.tlacuilose.HealthcheckAPI;

public class Call {
    private final long id;
    private final String structure;

    public Call(long id, String structure) {
        this.id = id;
        this.structure = structure;
    }

    public long getId() {
        return id;
    }

    public String getStructure() {
        return structure;
    }
}
