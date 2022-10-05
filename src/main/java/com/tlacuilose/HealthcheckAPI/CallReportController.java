package com.tlacuilose.HealthcheckAPI;

import java.lang.Long;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CallReportController {
    
    @GetMapping("/call/report") // Missing id
    public CallReport callReport(@RequestParam(value = "id", defaultValue = "1") String id) {
        return new CallReport(Long.parseLong(id), "today", 0.0);
    }
}
