package org.msa.notificationservice.notification.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.apache.http.HttpHeaders;
import org.msa.notificationservice.notification.model.FCMMessage;
import org.msa.notificationservice.notification.model.request.FCMPushRequestDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class FirebaseCloudMessageService {

    @Value("${fcm.key.path}")
    private String SERVICE_ACCOUNT_JSON;

    @Value("${fcm.key.scope}")
    private String SERVICE_SCOPE;

    @Value("${fcm.api.url}")
    private String API_URL;

    private final ObjectMapper objectMapper;

    /**
     * 단일 기기
     * Firebase에 메시지를 수신하는 함수 (헤더와 바디 직접 만들기)
     */
    @Transactional
    public String pushAlarm(FCMPushRequestDto dto) throws IOException {
        String message = makeSingleMessage(dto);
        sendPushMessage(message);
        return "success";
    }

    // Firebase 서버로 푸시 메시지를 전송하는 메서드
    private void sendPushMessage(String message) throws IOException {
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(message, MediaType.get("application/json; charset=utf-8"));
        Request httpRequest = new Request.Builder()
                .url(API_URL)
                .post(requestBody)
                .addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken())
                .addHeader(HttpHeaders.CONTENT_TYPE, "application/json; UTF-8")
                .build();

        Response response = client.newCall(httpRequest).execute();

        log.info("단일 기기 알림 전송 성공");
        log.info("알림 전송: {}", response.body().string());
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

    // Firebase에서 access token 가져오기
    private String getAccessToken() throws IOException {
        GoogleCredentials googleCredentials = GoogleCredentials
                .fromStream(new ClassPathResource(SERVICE_ACCOUNT_JSON).getInputStream())
                .createScoped(List.of(SERVICE_SCOPE));
        googleCredentials.refreshIfExpired();
        log.info("getAccessToken() - googleCredentials: {} ", googleCredentials.getAccessToken().getTokenValue());

        return googleCredentials.getAccessToken().getTokenValue();
    }

}
