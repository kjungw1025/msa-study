package org.msa.userservice.global.exception;

import org.msa.userservice.user.exception.LocalizedMessageException;
import org.springframework.http.HttpStatus;

public class AccessTokenNotFoundException extends LocalizedMessageException {
    public AccessTokenNotFoundException() {
        super(HttpStatus.NOT_FOUND, "notfound.access-token");
    }
}
