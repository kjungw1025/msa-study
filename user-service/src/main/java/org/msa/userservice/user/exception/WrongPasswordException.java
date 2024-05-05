package org.msa.userservice.user.exception;

import org.springframework.http.HttpStatus;

public class WrongPasswordException extends LocalizedMessageException {
    public WrongPasswordException() {
        super(HttpStatus.BAD_REQUEST, "invalid.password");
    }
}
