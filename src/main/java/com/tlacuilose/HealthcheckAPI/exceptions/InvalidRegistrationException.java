package com.tlacuilose.HealthcheckAPI.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Invalid email or password.")
public class InvalidRegistrationException extends RuntimeException {

}
