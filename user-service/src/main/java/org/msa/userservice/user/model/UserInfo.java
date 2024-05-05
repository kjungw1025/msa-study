package org.msa.userservice.user.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.msa.userservice.user.model.entity.User;

@Getter
@RequiredArgsConstructor
@EqualsAndHashCode
public class UserInfo {
    private final Long userId;
    private final String name;
    private final String nickname;
    private final String phone;
    private final UserStatus status;

    public UserInfo(User user) {
        this.userId = user.getId();
        this.name = user.getName();
        this.nickname = user.getNickname();
        this.phone = user.getPhone();
        this.status = user.getStatus();
    }
}
