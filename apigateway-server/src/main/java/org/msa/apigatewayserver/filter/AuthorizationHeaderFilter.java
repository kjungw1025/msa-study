package org.msa.apigatewayserver.filter;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.msa.apigatewayserver.exception.AccessTokenRequiredException;
import org.msa.apigatewayserver.exception.LocalizedMessageException;
import org.msa.apigatewayserver.exception.NotGrantedException;
import org.msa.apigatewayserver.util.JwtUtil;
import org.msa.apigatewayserver.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.context.MessageSource;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Locale;

@Component
@Slf4j
public class AuthorizationHeaderFilter extends AbstractGatewayFilterFactory<AuthorizationHeaderFilter.Config> {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private MessageSource messageSource;

    private static final String SIGN_UP_PATH = "/sign-up";
    private static final String LOGIN_PATH = "/login";
    private static final String ACTUATOR_PATH = "/actuator";
    private static final List<String> TOKEN_AUTH_WHITELIST = List.of(SIGN_UP_PATH, LOGIN_PATH, ACTUATOR_PATH);
    private static final String ERROR_MESSAGE_FORMAT = "{\"errorCode\": \"%s\", \"errorMessage\": \"%s\"}";

    public AuthorizationHeaderFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            try {
                jwtUtil.getAccessTokenFromHeader(exchange.getRequest()).ifPresentOrElse(accessToken -> {
                        if (isTokenRefreshRequested(exchange.getRequest())) {
                            return;
                        }

                        jwtUtil.validateAccessToken(accessToken);
                        String userRole = jwtUtil.getUserRole(accessToken);

                        // 현재 요청 API 엔드포인트의 권한 검사
                        String requestPath = exchange.getRequest().getURI().getPath();
                        if (!hasPermission(requestPath, userRole)) {
                            throw new NotGrantedException();
                        }

                        exchange.getRequest().mutate()
                                .header("requestId", jwtUtil.getUserId(accessToken))
                                .header("requestRole", jwtUtil.getUserRole(accessToken)).build();

                    }, () -> {
                        if(tokenAuthenticationRequired(exchange.getRequest())) {
                            throw new AccessTokenRequiredException();
                        }
                    }
                );
            } catch (LocalizedMessageException e) {
                return onError(exchange.getResponse(), exchange.getLocaleContext().getLocale(), e);
            }
            return chain.filter(exchange);
        };
    }

    private boolean isTokenRefreshRequested(ServerHttpRequest request) {
        return request.getURI().getPath().contains("/reissue");
    }

    private boolean tokenAuthenticationRequired(ServerHttpRequest request) {
        return TOKEN_AUTH_WHITELIST.stream()
                .noneMatch(uri -> request.getURI().getPath().contains(uri));
    }

    private Mono<Void> onError(ServerHttpResponse response, Locale locale, LocalizedMessageException e) {
        response.setRawStatusCode(e.getStatusCode());
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        String responseBody = String.format(ERROR_MESSAGE_FORMAT, e.getCode(), e.getMessages(messageSource, locale));
        DataBuffer buffer = response.bufferFactory().wrap(responseBody.getBytes(StandardCharsets.UTF_8));
        return response.writeWith(Mono.just(buffer));
    }

    private boolean hasPermission(String requestPath, String userRole) {
        // 요청 경로에 필요한 권한 체크
        // 예를 들어, 경로별로 필요한 최소 권한을 정의하고, 사용자 권한 목록과 비교하여 접근 여부를 결정
        if (requestPath.contains("/admin")) {
            return userRole.equals("ADMIN");
        } else if (requestPath.startsWith("/user-service")) {
            return userRole.equals("USER");
        }
        return true; // 권한이 필요하지 않은 경우 허용
    }

    public static class Config {
    }
}
