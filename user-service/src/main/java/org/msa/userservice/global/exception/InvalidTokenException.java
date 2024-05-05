package org.msa.userservice.global.exception;

import org.msa.userservice.user.exception.LocalizedMessageException;
import org.springframework.http.HttpStatus;

public class InvalidTokenException extends LocalizedMessageException {
    public InvalidTokenException() {
        super(HttpStatus.UNAUTHORIZED, "invalid.token");
    }
}
