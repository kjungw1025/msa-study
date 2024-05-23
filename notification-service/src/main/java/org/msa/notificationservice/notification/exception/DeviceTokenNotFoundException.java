package org.msa.notificationservice.notification.exception;

import org.msa.notificationservice.global.exception.LocalizedMessageException;
import org.springframework.http.HttpStatus;

public class DeviceTokenNotFoundException extends LocalizedMessageException {
    public DeviceTokenNotFoundException() { super(HttpStatus.NOT_FOUND, "notfound.device-token"); }
}
