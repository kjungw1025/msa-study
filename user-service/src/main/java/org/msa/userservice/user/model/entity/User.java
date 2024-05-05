package org.msa.userservice.user.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.msa.userservice.global.auth.role.UserRole;
import org.msa.userservice.global.base.BaseEntity;
import org.msa.userservice.user.model.UserStatus;

import static jakarta.persistence.EnumType.STRING;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    @NotNull
    private String loginId;

    @NotNull
    private String password;

    @NotNull
    @Column(length = 20)
    private String name;

    @NotNull
    @Column
    private String phone;

    @NotNull
    @Column(length = 20)
    private String nickname;

    @Enumerated(STRING)
    private UserStatus status;

    @Enumerated(STRING)
    private UserRole userRole;

    @Builder
    private User (@NonNull String loginId,
                  @NonNull String password,
                  @NonNull String name,
                  @NonNull String phone,
                  @NonNull String nickname,
                  UserStatus status,
                  UserRole userRole) {
        this.loginId = loginId;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.nickname = nickname;
        this.status = status;
        this.userRole = userRole;
    }
}
