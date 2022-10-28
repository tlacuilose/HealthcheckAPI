package com.tlacuilose.HealthcheckAPI;

import org.springframework.data.repository.CrudRepository;

import com.tlacuilose.HealthcheckAPI.User;

public interface UserRepository extends CrudRepository<User, Integer> {
}
