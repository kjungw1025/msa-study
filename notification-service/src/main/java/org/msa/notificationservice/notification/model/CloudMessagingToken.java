package org.msa.notificationservice.notification.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.msa.notificationservice.global.auth.role.UserRole;
import org.msa.notificationservice.global.base.BaseEntity;

import static jakarta.persistence.EnumType.STRING;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CloudMessagingToken extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "token_id")
    private Long id;

    @NotNull
    private Long userId;

    @Enumerated(STRING)
    private UserRole userRole;

    @NotNull
    private String deviceToken;

    @Builder
    private CloudMessagingToken (@NonNull Long userId,
                                 @NonNull UserRole userRole,
                                 @NonNull String deviceToken) {
        this.userId = userId;
        this.userRole = userRole;
        this.deviceToken = deviceToken;
    }
}
