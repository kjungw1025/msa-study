package org.msa.apigatewayserver.exception;

import org.springframework.http.HttpStatus;

public class NotFoundUserRole extends LocalizedMessageException {
    public NotFoundUserRole() {
        super(HttpStatus.NOT_FOUND, "notfound.user-role");
    }
}
