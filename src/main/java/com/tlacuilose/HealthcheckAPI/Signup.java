package com.tlacuilose.HealthcheckAPI;

public class Signup {
    private final String email;
    private final String apiKey;

    public Signup(String email, String apiKey) {
        this.email = email;
        this.apiKey = apiKey;
    }

    public String getEmail() {
        return email;
    }

    public String getApiKey() {
        return apiKey;
    }
}
