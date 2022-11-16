package com.tlacuilose.HealthcheckAPI.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "There is no call with that id.")
public class CallNotFoundException extends RuntimeException {

}
