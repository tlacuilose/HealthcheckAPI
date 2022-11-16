package com.tlacuilose.HealthcheckAPI;

import java.lang.Long;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tlacuilose.HealthcheckAPI.exceptions.CallNotFoundException;
import com.tlacuilose.HealthcheckAPI.exceptions.ForbiddenApiKeyException;

@RestController
public class CallReportController {

    @Autowired
    private CallRepository callRepository;

    @Autowired
    private ApiKeyRepository apiKeyRepository;

    @GetMapping("/call/{id}/report")
    public ResponseEntity<CallReport> callReport(@PathVariable Integer callId, @RequestParam String apiKey) {
        Optional<ApiKey> foundApiKey = apiKeyRepository.findByKey(apiKey);
        if (!foundApiKey.isPresent()) {
            throw new ForbiddenApiKeyException();
        }

        Optional<Call> foundCall = callRepository.findById(callId);
        if (!foundCall.isPresent()) {
            throw new CallNotFoundException();
        }

        ApiKey dbApiKey = foundApiKey.get();
        Call dbCall = foundCall.get();
        if (dbApiKey.getUser().getId() != dbCall.getUser().getId()) {
            throw new ForbiddenApiKeyException();
        }

        CallReport newCallReport = new CallReport();
        newCallReport.setDateCreated(Timestamp.from(Instant.now()));
        newCallReport.setCall(dbCall);

        fillReport(newCallReport, dbCall);

        Set<CallReport> reports = dbCall.getReports();
        reports.add(newCallReport);
        dbCall.setReports(reports);
        callRepository.save(dbCall);

        return new ResponseEntity<CallReport>(newCallReport, HttpStatus.CREATED);
    }

    private void fillReport(CallReport report, Call call) {
        int successCalls = 0;
        int errorCalls = 0;
        int validCalls = 0;
        int invalidCalls = 0;
        Set<CallResponse> callResponses = call.getCallResponses();

        for (CallResponse response : callResponses) {
            Integer responseCode = response.getResponseCode();
            if (200 <= responseCode.intValue() && responseCode.intValue() < 300) {
                successCalls++;
            }
            if (400 <= responseCode.intValue() && responseCode.intValue() < 600) {
                errorCalls++;
            }

            boolean isValid = response.getIsValid();
            if (isValid) {
                validCalls++;
            } else {
                invalidCalls++;
            }
        }

        report.setNumSuccessStatus(successCalls);
        report.setNumErrorStatus(errorCalls);
        report.setResponseSuccessRate(successCalls / errorCalls);
        report.setValidRate(validCalls / invalidCalls);
    }
}
