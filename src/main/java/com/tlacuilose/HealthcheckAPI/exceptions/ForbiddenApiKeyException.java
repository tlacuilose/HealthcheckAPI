package com.tlacuilose.HealthcheckAPI.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "Api Key invalid.")
public class ForbiddenApiKeyException extends RuntimeException {

}
