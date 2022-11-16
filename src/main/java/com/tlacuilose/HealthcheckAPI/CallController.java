package com.tlacuilose.HealthcheckAPI;

import java.lang.Long;
import java.lang.StackWalker.Option;
import java.util.Optional;
import java.util.concurrent.ThreadPoolExecutor.CallerRunsPolicy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tlacuilose.HealthcheckAPI.exceptions.CallNotFoundException;
import com.tlacuilose.HealthcheckAPI.exceptions.ForbiddenApiKeyException;

@RestController
public class CallController {

    @Autowired
    private CallRepository callRepository;

    @Autowired
    private ApiKeyRepository apiKeyRepository;

    @PostMapping(path = "/call")
    public ResponseEntity<Call> createCall(@RequestParam String apiKey, @RequestBody Call call) {
        Optional<ApiKey> foundApiKey = apiKeyRepository.findByKey(apiKey);
        if (!foundApiKey.isPresent()) {
            throw new ForbiddenApiKeyException();
        }

        ApiKey dbApiKey = foundApiKey.get();
        call.setUser(dbApiKey.getUser());
        callRepository.save(call);

        return new ResponseEntity<Call>(call, HttpStatus.CREATED);
    }

    @GetMapping(path = "/call/{id}")
    public ResponseEntity<Call> getCall(@PathVariable Integer callId, @RequestParam String apiKey) {
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

        return new ResponseEntity<Call>(dbCall, HttpStatus.OK);
    }

    @PutMapping(path = "/call/{id}")
    public ResponseEntity<Call> updateCall(@PathVariable Integer callId, @RequestParam String apiKey,
            @RequestBody Call call) {
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

        call.setUser(dbCall.getUser());
        call.setId(dbCall.getId());
        callRepository.save(call);

        return new ResponseEntity<Call>(call, HttpStatus.OK);
    }

    @DeleteMapping(path = "/call/{id}")
    public @ResponseBody String deleteCall(@PathVariable Integer callId, @RequestParam String apiKey) {
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

        callRepository.deleteById(callId);

        return "Deleted";
    }

}
