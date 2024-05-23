package org.msa.notificationservice.notification.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.msa.notificationservice.notification.model.request.FCMPushRequestDto;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
@Slf4j
@RequiredArgsConstructor
public class FirebaseCloudMessageAsyncService {

    private final FirebaseMessaging firebaseMessaging;

    public void pushAlarm(String deviceToken, FCMPushRequestDto requestDto) {

        Notification notification = createNotification(requestDto);
        Message message = createMessage(deviceToken, notification);

        try {
            firebaseMessaging.sendAsync(message).get();
            log.info("단일 기기 알림 전송 성공");
        } catch(InterruptedException | ExecutionException e) {
            log.error("알림 보내기를 실패했습니다.", e);
        }
    }

    private Notification createNotification(FCMPushRequestDto requestDto) {
        return Notification.builder()
                .setTitle(requestDto.getTitle())
                .setBody(requestDto.getBody())
                .setImage("")
                .build();
    }

    private Message createMessage(String deviceToken, Notification notification) {
        return Message.builder()
                .setToken(deviceToken)
                .setNotification(notification)
                .build();
    }
}
