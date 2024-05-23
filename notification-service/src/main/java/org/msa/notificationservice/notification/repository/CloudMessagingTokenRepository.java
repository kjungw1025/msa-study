package org.msa.notificationservice.notification.repository;

import org.msa.notificationservice.notification.model.CloudMessagingToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CloudMessagingTokenRepository extends JpaRepository<CloudMessagingToken, Long> {
    @Query("select c.deviceToken from CloudMessagingToken c where c.userId = :userId ")
    Optional<String> findDeviceTokenByUserId(@Param("userId") Long userId);
}
