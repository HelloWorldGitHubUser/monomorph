package com.lakesidemutual.interfaces.dtos.management;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Indicates that required customer master data is temporarily unavailable.
 * Spring converts this exception into an HTTP 502 response.
 */
@ResponseStatus(code = HttpStatus.BAD_GATEWAY)
public class CustomerCoreNotAvailableException extends RuntimeException {
	private static final long serialVersionUID = 2146599135907479601L;

	public CustomerCoreNotAvailableException(String errorMessage) {
		super(errorMessage);
	}
}
