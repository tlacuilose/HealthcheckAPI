package com.tlacuilose.HealthcheckAPI;

import java.lang.Long;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CallController {
    
    @GetMapping("/call")
    public Call call(@RequestParam(value = "id", defaultValue = "0") String id) {
        return new Call(Long.parseLong(id), "structure");
    }
}
