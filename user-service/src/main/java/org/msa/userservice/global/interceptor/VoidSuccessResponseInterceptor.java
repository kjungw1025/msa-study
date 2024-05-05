package org.msa.userservice.global.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.msa.userservice.global.model.dto.ResponseSuccessDto;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;


@RequiredArgsConstructor
@Component
public class VoidSuccessResponseInterceptor implements HandlerInterceptor {
    private final ObjectMapper objectMapper;

    @Override
    public void afterCompletion(@NonNull HttpServletRequest request, HttpServletResponse response, Object object, Exception ex) throws Exception {
        HttpStatus status = HttpStatus.valueOf(response.getStatus());
        if (!status.is2xxSuccessful()) {
            return;
        }

        if (response.getContentType() != null) {
            return;
        }

        String wrappedBody = objectMapper.writeValueAsString(new ResponseSuccessDto());

        byte[] bytesData = wrappedBody.getBytes();
        response.setContentType("application/json");
        response.resetBuffer();
        response.getOutputStream().write(bytesData, 0, bytesData.length);
    }
}
