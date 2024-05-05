package org.msa.userservice.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.msa.userservice.global.auth.role.UserRole;
import org.msa.userservice.user.model.UserStatus;
import org.msa.userservice.user.model.dto.request.RequestSignUpDto;
import org.msa.userservice.user.model.entity.User;
import org.msa.userservice.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class SignupService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Transactional
    public void signup(RequestSignUpDto dto) {
        // 진짜 간단하게
        String encryptedPassword = passwordEncoder.encode(dto.getPassword());

        User user = User.builder()
                .loginId(dto.getLoginId())
                .password(encryptedPassword)
                .name(dto.getName())
                .phone(dto.getPhone())
                .nickname(dto.getNickname())
                .status(UserStatus.ACTIVE)
                .userRole(UserRole.USER)
                .build();
        userRepository.save(user);
    }

}
