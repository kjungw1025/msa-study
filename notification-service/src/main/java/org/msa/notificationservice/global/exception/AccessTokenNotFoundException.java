package org.msa.notificationservice.global.exception;

import org.springframework.http.HttpStatus;

public class AccessTokenNotFoundException extends LocalizedMessageException {
    public AccessTokenNotFoundException() {
        super(HttpStatus.NOT_FOUND, "notfound.access-token");
    }
}
