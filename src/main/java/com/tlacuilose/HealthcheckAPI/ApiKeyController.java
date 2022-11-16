package com.tlacuilose.HealthcheckAPI;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.tlacuilose.HealthcheckAPI.exceptions.ForbiddenApiKeyException;
import com.tlacuilose.HealthcheckAPI.exceptions.UnauthorizedException;
import com.tlacuilose.HealthcheckAPI.exceptions.UserNotFoundException;

@RestController
public class ApiKeyController {

    @Autowired
    private ApiKeyRepository apiKeyRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/apikey/generate")
    public ResponseEntity<ApiKey> generateApiKey(@RequestBody User user) {
        Optional<User> foundUser = userRepository.findByEmail(user.getEmail());
        if (!foundUser.isPresent()) {
            throw new UserNotFoundException();
        }

        User dbUser = foundUser.get();
        if (!user.getPassword().equals(dbUser.getPassword())) {
            throw new UnauthorizedException();
        }

        Timestamp now = Timestamp.from(Instant.now());
        Timestamp validUntil = Timestamp.from(now.toInstant().plusSeconds(ApiKey.KEY_TTL_SECS));
        ApiKey apiKey = new ApiKey();
        apiKey.setUser(dbUser);
        apiKey.setDateCreated(Timestamp.from(Instant.now()));
        apiKey.setValidUntil(validUntil);

        String key = createKey(apiKey);
        apiKey.setKey(key);
        apiKeyRepository.save(apiKey);

        return new ResponseEntity<ApiKey>(apiKey, HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/apikey/revoke")
    public @ResponseBody String revokeApiKey(@RequestParam String apiKey) {
        Optional<ApiKey> foundApiKey = apiKeyRepository.findByKey(apiKey);
        if (!foundApiKey.isPresent()) {
            throw new ForbiddenApiKeyException();
        }

        ApiKey updatedApiKey = foundApiKey.get();
        updatedApiKey.setValidUntil(Timestamp.from(Instant.now()));
        apiKeyRepository.save(updatedApiKey);

        return "Revoked api key.";
    }

    private String createKey(ApiKey apikey) {
        Integer userId = apikey.getUser().getId();
        int apiKeyHash = apikey.hashCode();
        return userId.toString() + '+' + Integer.toString(apiKeyHash);
    }

}
