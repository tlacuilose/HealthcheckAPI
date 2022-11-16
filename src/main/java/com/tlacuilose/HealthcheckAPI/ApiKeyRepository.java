package com.tlacuilose.HealthcheckAPI;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface ApiKeyRepository extends CrudRepository<ApiKey, Integer> {

    Optional<ApiKey> findByKey(String key);

}
