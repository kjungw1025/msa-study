package org.msa.notificationservice.notification.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.msa.notificationservice.global.auth.role.UserRole;
import org.msa.notificationservice.notification.exception.DeviceTokenNotFoundException;
import org.msa.notificationservice.notification.model.CloudMessagingToken;
import org.msa.notificationservice.notification.repository.CloudMessagingTokenRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CloudMessagingTokenService {

    private final CloudMessagingTokenRepository cloudMessagingTokenRepository;

    public void saveDeviceToken(Long userId, UserRole userRole, String deviceToken) {
        CloudMessagingToken cloudMessagingToken = CloudMessagingToken.builder()
                .userId(userId)
                .userRole(userRole)
                .deviceToken(deviceToken)
                .build();

        cloudMessagingTokenRepository.save(cloudMessagingToken);
    }

    public String findDeviceToken(Long userId) {
        return cloudMessagingTokenRepository.findDeviceTokenByUserId(userId).orElseThrow(DeviceTokenNotFoundException::new);
    }
}
