package org.msa.userservice.user.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.msa.userservice.user.model.dto.request.RequestLoginDto;
import org.msa.userservice.user.model.dto.request.RequestRefreshTokenDto;
import org.msa.userservice.user.model.dto.request.RequestSignUpDto;
import org.msa.userservice.user.model.dto.response.ResponseLoginDto;
import org.msa.userservice.user.model.dto.response.ResponseRefreshTokenDto;
import org.msa.userservice.user.service.SignupService;
import org.msa.userservice.user.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final SignupService signupService;
    private final UserService userService;

    @GetMapping("/health_check")
    public String status() {
        return "It's Working in User Service";
    }

    /**
     * 회원가입
     *
     * @param dto 요청 body
     */
    @PostMapping("/sign-up")
    public void signup(@Valid @RequestBody RequestSignUpDto dto) {
        signupService.signup(dto);
    }

    /**
     * 로그인
     *
     * @param dto 요청 body
     * @return 로그인 인증 정보
     */
    @PostMapping("/login")
    public ResponseLoginDto login(@Valid @RequestBody RequestLoginDto dto) {
        return userService.login(dto);
    }

    /**
     * 토큰 재발급
     *
     * @param dto 요청 body
     * @return 재발급된 로그인 인증 정보
     */
    @PostMapping("/reissue")
    public ResponseRefreshTokenDto refreshToken(HttpServletRequest request,
                                                @Valid @RequestBody RequestRefreshTokenDto dto) {
        System.out.println(request);
        System.out.println(request.getHeader("Authorization"));
        System.out.println(dto.getRefreshToken());
        return userService.refreshToken(request, dto.getRefreshToken());
    }
}
