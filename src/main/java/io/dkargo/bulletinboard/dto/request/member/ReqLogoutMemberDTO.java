package io.dkargo.bulletinboard.dto.request.member;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Schema
@Getter
@Setter
public class ReqLogoutMemberDTO {
    @Schema(description = "액세스 토큰")
    @NotBlank
    private String accessToken;

    @Schema(description = "리프레쉬 토큰")
    @NotBlank
    private String refreshToken;
}
