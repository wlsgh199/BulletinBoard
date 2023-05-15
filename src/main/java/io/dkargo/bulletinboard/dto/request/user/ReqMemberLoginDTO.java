package io.dkargo.bulletinboard.dto.request.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Schema
@Getter
@Setter
public class ReqMemberLoginDTO {
    @Schema(description = "이메일", example = "jhpark@dkargo.io", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank
    private String email;

    @Schema(description = "비밀번호", example = "Aasd!12345", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank
    private String password;
}
