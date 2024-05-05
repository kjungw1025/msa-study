package org.msa.userservice.global.exception;

import org.msa.userservice.user.exception.LocalizedMessageException;
import org.springframework.http.HttpStatus;

public class ExpiredTokenException extends LocalizedMessageException {
    public ExpiredTokenException() {
        super(HttpStatus.NOT_ACCEPTABLE, "invalid.expired-token");
    }
}
