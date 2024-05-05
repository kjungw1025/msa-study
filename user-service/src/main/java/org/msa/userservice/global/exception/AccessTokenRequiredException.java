package org.msa.userservice.global.exception;

import org.msa.userservice.user.exception.LocalizedMessageException;
import org.springframework.http.HttpStatus;

public class AccessTokenRequiredException extends LocalizedMessageException {
    public AccessTokenRequiredException() {
        super(HttpStatus.UNAUTHORIZED, "required.access-token");
    }
}
