package org.msa.notificationservice.notification.repository;

import org.msa.notificationservice.notification.model.CloudMessagingToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CloudMessagingTokenRepository extends JpaRepository<CloudMessagingToken, Long> {
}
