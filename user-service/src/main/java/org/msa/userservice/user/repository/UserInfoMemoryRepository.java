package org.msa.userservice.user.repository;

import org.msa.userservice.user.model.UserInfo;

import java.time.Instant;
import java.util.Optional;

public interface UserInfoMemoryRepository {

    Optional<UserInfo> getUserInfo(Long userId, Instant now);

    void setUserInfo(Long userId, UserInfo userInfo, Instant now);

    void removeUserInfo(Long userId);
}
