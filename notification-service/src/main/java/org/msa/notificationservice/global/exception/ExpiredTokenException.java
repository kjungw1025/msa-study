package org.msa.notificationservice.global.exception;

import org.springframework.http.HttpStatus;

public class ExpiredTokenException extends LocalizedMessageException {
    public ExpiredTokenException() {
        super(HttpStatus.NOT_ACCEPTABLE, "invalid.expired-token");
    }
}
