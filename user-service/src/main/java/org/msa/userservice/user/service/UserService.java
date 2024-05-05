package org.msa.userservice.user.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.msa.userservice.global.auth.jwt.AuthenticationToken;
import org.msa.userservice.global.auth.jwt.JwtProvider;
import org.msa.userservice.global.exception.AccessTokenNotFoundException;
import org.msa.userservice.user.exception.UserNotFoundException;
import org.msa.userservice.user.exception.WrongPasswordException;
import org.msa.userservice.user.model.dto.request.RequestLoginDto;
import org.msa.userservice.user.model.dto.response.ResponseLoginDto;
import org.msa.userservice.user.model.dto.response.ResponseRefreshTokenDto;
import org.msa.userservice.user.model.entity.User;
import org.msa.userservice.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;

    private final UserInfoService userInfoService;

    public ResponseLoginDto login(RequestLoginDto dto) {
        User user = userRepository.findByLoginId(dto.getLoginId())
                .orElseThrow(UserNotFoundException::new);

        if (passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            AuthenticationToken token = jwtProvider.issue(user);
            userInfoService.cacheUserInfo(user.getId(), user);
            return new ResponseLoginDto(token);
        } else {
            throw new WrongPasswordException();
        }
    }

    public ResponseRefreshTokenDto refreshToken(HttpServletRequest request, String refreshToken) {
        String accessToken = jwtProvider.getAccessTokenFromHeader(request).orElseThrow(AccessTokenNotFoundException::new);
        AuthenticationToken token = jwtProvider.reissue(accessToken, refreshToken);
        return new ResponseRefreshTokenDto(token);
    }
}
