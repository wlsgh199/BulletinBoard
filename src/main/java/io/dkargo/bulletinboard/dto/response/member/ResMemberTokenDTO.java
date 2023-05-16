package io.dkargo.bulletinboard.dto.response.member;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema
public class ResMemberTokenDTO {
//    private String grantType;
    @Schema(description = "액세스 토큰")
    private String accessToken;
    @Schema(description = "리프레쉬 토큰")
    private String refreshToken;
}