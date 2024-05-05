package org.msa.userservice.user.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RequestSignUpDto {

    @NotBlank
    @Size(min = 3, max = 200)
    private final String loginId;

    @NotBlank
    @Size(min = 3, max = 200)
    private final String password;

    @NotBlank
    private final String name;

    @NotBlank
    @Size(min = 11, max = 11)
    private final String phone;

    @NotBlank
    @Size(min = 3, max = 16)
    @Pattern(regexp = "^(?!.*\\s{2,})[A-Za-z\\dㄱ-ㅎㅏ-ㅣ가-힣_ ]{3,16}$")
    private final String nickname;
}
