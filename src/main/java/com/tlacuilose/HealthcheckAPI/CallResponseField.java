package com.tlacuilose.HealthcheckAPI;

import javax.persistence.Embeddable;

@Embeddable
public class CallResponseField {

    public String name;
    public String match;
    public boolean required;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMatch() {
        return match;
    }

    public void setMatch(String match) {
        this.match = match;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

}
