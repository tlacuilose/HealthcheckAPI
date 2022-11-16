package com.tlacuilose.HealthcheckAPI.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNAUTHORIZED, reason = "User with email and password combination does not exist.")
public class UnauthorizedException extends RuntimeException {

}
