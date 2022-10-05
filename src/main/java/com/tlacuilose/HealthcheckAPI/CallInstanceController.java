package com.tlacuilose.HealthcheckAPI;

import java.lang.Long;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CallInstanceController {
    
    @GetMapping("/call/instance") // Mising id
    public CallInstance callInstance(@RequestParam(value = "id", defaultValue = "0") String id) {
        return new CallInstance(Long.parseLong(id), 201, "structure");
    }
}

