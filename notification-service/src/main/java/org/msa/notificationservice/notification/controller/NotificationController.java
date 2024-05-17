package org.msa.notificationservice.notification.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.msa.notificationservice.global.auth.role.UserRole;
import org.msa.notificationservice.notification.model.request.FCMPushRequestDto;
import org.msa.notificationservice.notification.model.request.UserDeviceTokenRequestDto;
import org.msa.notificationservice.notification.service.CloudMessagingTokenService;
import org.msa.notificationservice.notification.service.FirebaseCloudMessageService;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

import static java.lang.Long.parseLong;

@RestController
@RequiredArgsConstructor
public class NotificationController {

    private final FirebaseCloudMessageService firebaseCloudMessageService;
    private final CloudMessagingTokenService cloudMessagingTokenService;

    /**
     * 사용자의 디바이스 토큰을 등록합니다.
     */
    @PostMapping("/device-token")
    public void registerDeviceToken(@RequestHeader("requestId") String requestId,
                                    @RequestHeader("requestRole") String requestRole,
                                    @Valid @RequestBody UserDeviceTokenRequestDto requestDto) {
        cloudMessagingTokenService.saveDeviceToken(
                parseLong(requestId),
                UserRole.of(requestRole),
                requestDto.getUserDeviceToken()
        );
    }

    /**
     * 사용자에게 알람을 보냅니다. (테스트)
     */
    @PostMapping("/sendMessage")
    public void sendMessage(@Valid @RequestBody FCMPushRequestDto fcmPushRequestDto) throws IOException {
        firebaseCloudMessageService.pushAlarm(fcmPushRequestDto);
    }
}
