package org.msa.apigatewayserver.exception;

import org.springframework.http.HttpStatus;

public class NotGrantedException extends LocalizedMessageException {
    public NotGrantedException() {
        super(HttpStatus.UNAUTHORIZED, "required.granted");
    }
}
