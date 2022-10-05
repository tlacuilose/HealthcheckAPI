package com.tlacuilose.HealthcheckAPI;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SignupController {
    
    @GetMapping("/signup")
    public Signup signup(@RequestParam(value = "email", defaultValue = "none") String email) {
        return new Signup(email, "api_key");
    }
}
