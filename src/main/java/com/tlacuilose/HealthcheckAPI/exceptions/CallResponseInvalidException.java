package com.tlacuilose.HealthcheckAPI.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Invalid call response.")
public class CallResponseInvalidException extends RuntimeException {

}
