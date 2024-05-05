package org.msa.userservice.user.model.dto.response;

import lombok.Getter;
import org.msa.userservice.global.auth.jwt.AuthenticationToken;

@Getter
public class ResponseLoginDto {
    private final String accessToken;
    private final String refreshToken;

    public ResponseLoginDto(AuthenticationToken token) {
        this.accessToken = token.getAccessToken();
        this.refreshToken = token.getRefreshToken();
    }
}