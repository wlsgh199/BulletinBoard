package io.dkargo.bulletinboard.dto.request.member;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Schema
@Getter
@Setter
public class ReqReissueTokenDTO {
    @Schema(description = "액세스 토큰")
    private String accessToken;
    @Schema(description = "리프레쉬 토큰")
    private String refreshToken;
}
