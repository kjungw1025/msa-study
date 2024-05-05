package org.msa.userservice.user.repository;

import org.msa.userservice.user.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u from User u where u.status = 'ACTIVE' and u.loginId = :loginId ")
    Optional<User> findByLoginId(@Param("loginId") String loginId);
}
