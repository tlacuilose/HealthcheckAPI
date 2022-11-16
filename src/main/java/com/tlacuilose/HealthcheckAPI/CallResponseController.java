package com.tlacuilose.HealthcheckAPI;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.hibernate.hql.spi.id.inline.InlineIdsSubSelectValueListBulkIdStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tlacuilose.HealthcheckAPI.exceptions.CallNotFoundException;
import com.tlacuilose.HealthcheckAPI.exceptions.CallResponseInvalidException;
import com.tlacuilose.HealthcheckAPI.exceptions.CallResponseNotFoundException;
import com.tlacuilose.HealthcheckAPI.exceptions.ForbiddenApiKeyException;

@RestController
public class CallResponseController {

    @Autowired
    private CallResponseRepository callResponseRepository;

    @Autowired
    private CallRepository callRepository;

    @Autowired
    private ApiKeyRepository apiKeyRepository;

    @PostMapping(path = "/call/{callId}/response")
    public ResponseEntity<CallResponse> createCallResponse(@PathVariable Integer callId, @RequestParam String apiKey,
            @RequestParam Integer responseCode,
            @RequestBody HttpEntity<String> httpEntity) {
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

        CallResponse newCallResponse = new CallResponse();
        newCallResponse.setDateCalled(Timestamp.from(Instant.now()));
        newCallResponse.setResponseCode(responseCode);
        newCallResponse.setCall(dbCall);

        try {
            newCallResponse.setIsValid(isCallResponseValid(dbCall, httpEntity.getBody()));
        } catch (Exception e) {
            throw new CallResponseInvalidException();
        }

        callResponseRepository.save(newCallResponse);

        Set<CallResponse> callResponses = dbCall.getCallResponses();
        callResponses.add(newCallResponse);
        dbCall.setCallResponses(callResponses);

        callRepository.save(dbCall);

        return new ResponseEntity<CallResponse>(newCallResponse, HttpStatus.CREATED);
    }

    private boolean isCallResponseValid(Call call, String responseJson) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> receivedFields = mapper.readValue(responseJson, Map.class);
        for (CallResponseField field : call.getReponseStructure()) {
            String receivedValue = receivedFields.get(field.getName());
            if (field.isRequired() && receivedValue == null) {
                return false;
            }

            if (receivedValue != null) {
                boolean fieldIsValid = receivedValue.matches(field.getMatch());
                if (!fieldIsValid) {
                    return false;
                }
            }
        }

        return true;
    }

    @GetMapping(path = "/call/response/{id}")
    public ResponseEntity<CallResponse> getCallResponse(@PathVariable Integer callResponseId,
            @RequestParam String apiKey) {
        Optional<ApiKey> foundApiKey = apiKeyRepository.findByKey(apiKey);
        if (!foundApiKey.isPresent()) {
            throw new ForbiddenApiKeyException();
        }

        Optional<CallResponse> foundCallResponse = callResponseRepository.findById(callResponseId);
        if (!foundCallResponse.isPresent()) {
            throw new CallResponseNotFoundException();
        }

        ApiKey dbApiKey = foundApiKey.get();
        CallResponse dbCallResponse = foundCallResponse.get();

        if (dbApiKey.getUser().getId() != dbCallResponse.getCall().getUser().getId()) {
            throw new ForbiddenApiKeyException();
        }

        return new ResponseEntity<CallResponse>(dbCallResponse, HttpStatus.OK);

    }

}
