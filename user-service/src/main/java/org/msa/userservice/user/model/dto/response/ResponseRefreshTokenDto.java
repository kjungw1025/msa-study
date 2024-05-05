package org.msa.userservice.user.model.dto.response;

import lombok.Getter;
import org.msa.userservice.global.auth.jwt.AuthenticationToken;

@Getter
public class ResponseRefreshTokenDto {
    private final String accessToken;
    private final String refreshToken;

    public ResponseRefreshTokenDto(AuthenticationToken token) {
        this.accessToken = token.getAccessToken();
        this.refreshToken = token.getRefreshToken();
    }
}
