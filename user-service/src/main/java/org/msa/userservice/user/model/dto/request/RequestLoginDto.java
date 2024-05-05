package org.msa.userservice.user.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RequestLoginDto {

    @NotBlank
    private final String loginId;

    @NotBlank
    private final String password;
}