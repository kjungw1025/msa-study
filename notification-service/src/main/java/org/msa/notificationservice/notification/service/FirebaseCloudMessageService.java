package org.msa.notificationservice.notification.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.msa.notificationservice.notification.client.FirebaseClient;
import org.msa.notificationservice.notification.model.FCMMessage;
import org.msa.notificationservice.notification.model.request.FCMPushRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Slf4j
@RequiredArgsConstructor
public class FirebaseCloudMessageService {

    private final ObjectMapper objectMapper;

    private final FirebaseClient firebaseClient;

    /**
     * 단일 기기
     * Firebase에 메시지를 수신하는 함수 (헤더와 바디 직접 만들기)
     */
    public void pushAlarm(FCMPushRequestDto dto) throws IOException {
        String message = makeSingleMessage(dto);
        sendPushMessage(message);
    }

    // Firebase 서버로 푸시 메시지를 전송하는 메서드
    private void sendPushMessage(String message) {
        try {
            ResponseEntity<String> responseEntity = firebaseClient.sendPushMessage(message);
            log.info("단일 기기 알림 전송 성공");
            log.info("알림 전송 결과: {}", responseEntity.getBody());
        } catch (FeignException ex) {
            log.error("알림 전송 실패: {}", ex.getMessage());
        }
    }

    // 요청 파라미터를 FCM의 body 형태로 만들어주는 메서드 (단일 기기)
    private String makeSingleMessage(FCMPushRequestDto requestDto) throws JsonProcessingException {
        FCMMessage fcmMessage = FCMMessage.builder()
                .message(FCMMessage.Message.builder()
                        .token(requestDto.getTargetToken())
                        .notification(FCMMessage.Notification.builder()
                                .title(requestDto.getTitle())
                                .body(requestDto.getBody())
                                .image("")
                                .build())
                        .build()
                ).validateOnly(false)
                .build();
        return objectMapper.writeValueAsString(fcmMessage);
    }
}
