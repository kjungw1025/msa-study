package org.msa.notificationservice.notification.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.msa.notificationservice.notification.model.request.FCMPushRequestDto;
import org.msa.notificationservice.notification.model.request.UserDeviceTokenRequestDto;
import org.msa.notificationservice.notification.service.FirebaseCloudMessageService;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class NotificationController {

    private final FirebaseCloudMessageService firebaseCloudMessageService;

    /**
     * 사용자의 디바이스 토큰을 등록합니다.
     */
    @PostMapping("/device-token")
    public void registerDeviceToken(@RequestHeader("requestId") String requestId,
                                    @Valid @RequestBody UserDeviceTokenRequestDto requestDto) {

    }

    /**
     * 사용자에게 알람을 보냅니다. (테스트)
     */
    @PostMapping("/sendMessage")
    public String sendMessage(@RequestHeader("requestId") String requestId,
                            @Valid @RequestBody FCMPushRequestDto fcmPushRequestDto) throws IOException {
        System.out.println("requestId: " + requestId);
        return firebaseCloudMessageService.pushAlarm(fcmPushRequestDto);
    }
}
