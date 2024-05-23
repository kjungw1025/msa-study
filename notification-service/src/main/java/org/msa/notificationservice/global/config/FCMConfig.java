package org.msa.notificationservice.global.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;


@Configuration
@Slf4j
public class FCMConfig {

    @Value("${fcm.key.path}")
    private String SERVICE_ACCOUNT_JSON;

    @Value("${fcm.key.project-id}")
    private String PROJECT_ID;

    @Bean
    FirebaseMessaging firebaseMessaging() {
        try {
            ClassPathResource resource = new ClassPathResource(SERVICE_ACCOUNT_JSON);
            InputStream inputStream = resource.getInputStream();

            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(inputStream))
                    .setProjectId(PROJECT_ID)
                    .build();

            FirebaseApp firebaseApp = FirebaseApp.initializeApp(options);
            log.info("파이어베이스 서버와의 연결에 성공했습니다.");

            return FirebaseMessaging.getInstance(firebaseApp);
        } catch (IOException e) {
            log.error("파이어베이스 서버와의 연결에 실패했습니다.", e);

            return null;
        }
    }
}