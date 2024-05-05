package org.msa.userservice.user.service;

import lombok.RequiredArgsConstructor;
import org.msa.userservice.user.model.UserInfo;
import org.msa.userservice.user.model.entity.User;
import org.msa.userservice.user.repository.UserInfoMemoryRepository;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.Instant;

@Service
@RequiredArgsConstructor
public class UserInfoService {

    private final Clock clock;
    private final UserInfoMemoryRepository memoryRepository;

    public void cacheUserInfo(Long userId, User user) {
        UserInfo userInfo = new UserInfo(user);
        memoryRepository.setUserInfo(userId, userInfo, Instant.now(clock));
    }
}
