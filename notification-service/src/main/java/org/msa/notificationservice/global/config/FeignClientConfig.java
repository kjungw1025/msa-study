package org.msa.notificationservice.global.config;

import com.google.auth.oauth2.GoogleCredentials;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class FeignClientConfig {

    @Value("${fcm.key.path}")
    private String SERVICE_ACCOUNT_JSON;

    @Value("${fcm.key.scope}")
    private String SERVICE_SCOPE;

    // firebase에서 accessToken을 가져오는 메서드
    private String getAccessToken() throws IOException {
        GoogleCredentials googleCredentials = GoogleCredentials
                .fromStream(new ClassPathResource(SERVICE_ACCOUNT_JSON).getInputStream())
                .createScoped(List.of(SERVICE_SCOPE));
        googleCredentials.refreshIfExpired();
        return googleCredentials.getAccessToken().getTokenValue();
    }

    @Bean
    public RequestInterceptor requestInterceptor() {
        return new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate template) {
                try {
                    template.header(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                template.header(HttpHeaders.CONTENT_TYPE, "application/json; UTF-8");
            }
        };
    }
}
