package org.msa.apigatewayserver.util;

import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.msa.apigatewayserver.exception.ExpiredTokenException;
import org.msa.apigatewayserver.exception.InvalidTokenException;
import org.msa.apigatewayserver.exception.NotFoundUserRole;
import org.msa.apigatewayserver.exception.NotGrantedException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtUtil {

    public static final String AUTHORIZATION = "Authorization";
    public static final String BEARER = "Bearer ";

    @Value("${jwt.secret-key}")
    private String secretKey;


    public Optional<String> getAccessTokenFromHeader(ServerHttpRequest request) {
        return Optional.ofNullable(request.getHeaders().get(AUTHORIZATION))
                .map(authorization -> authorization.get(0).replace(BEARER, ""));
    }

    public Jws<Claims> validateAccessToken(String accessToken) {
        try {
            return Jwts.parser()
                    .setSigningKey(secretKey.getBytes())
                    .parseClaimsJws(accessToken);
        } catch (ExpiredJwtException e) {
            throw new ExpiredTokenException();
        } catch (JwtException e) {
            throw new InvalidTokenException();
        }
    }

    public String getUserRole(String accessToken){
        try {
            return Jwts.parser()
                    .setSigningKey(secretKey.getBytes())
                    .parseClaimsJws(accessToken)
                    .getBody()
                    .get("userRole")
                    .toString();
        } catch (Exception e) {
            throw new NotGrantedException();
        }
    }

    public String getUserId(String accessToken){
        try {
            return Jwts.parser()
                    .setSigningKey(secretKey.getBytes())
                    .parseClaimsJws(accessToken)
                    .getBody()
                    .get("userId")
                    .toString();
        } catch (Exception e) {
            throw new NotGrantedException();
        }
    }
}
