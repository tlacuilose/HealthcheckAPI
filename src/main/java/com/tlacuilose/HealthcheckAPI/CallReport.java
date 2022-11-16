package com.tlacuilose.HealthcheckAPI;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class CallReport {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private Timestamp dateCreated;

    private int numSuccessStatus;

    private int numErrorStatus;

    private float responseSuccessRate;

    private int numValidCalls;

    private int numInvalidCalls;

    private float validRate;

    @ManyToOne
    @JoinColumn(name = "call_id", nullable = false)
    private Call call;

    public Timestamp getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Timestamp dateCreated) {
        this.dateCreated = dateCreated;
    }

    public int getNumSuccessStatus() {
        return numSuccessStatus;
    }

    public void setNumSuccessStatus(int numSuccessStatus) {
        this.numSuccessStatus = numSuccessStatus;
    }

    public int getNumErrorStatus() {
        return numErrorStatus;
    }

    public void setNumErrorStatus(int numErrorStatus) {
        this.numErrorStatus = numErrorStatus;
    }

    public float getResponseSuccessRate() {
        return responseSuccessRate;
    }

    public void setResponseSuccessRate(float responseSuccessRate) {
        this.responseSuccessRate = responseSuccessRate;
    }

    public int getNumValidCalls() {
        return numValidCalls;
    }

    public void setNumValidCalls(int numValidCalls) {
        this.numValidCalls = numValidCalls;
    }

    public int getNumInvalidCalls() {
        return numInvalidCalls;
    }

    public void setNumInvalidCalls(int numInvalidCalls) {
        this.numInvalidCalls = numInvalidCalls;
    }

    public float getValidRate() {
        return validRate;
    }

    public void setValidRate(float validRate) {
        this.validRate = validRate;
    }

    public Call getCall() {
        return call;
    }

    public void setCall(Call call) {
        this.call = call;
    }
}
