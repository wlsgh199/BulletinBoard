package io.dkargo.bulletinboard.dto.request.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema
public class MemberTokenDTO {
    private String grantType;
    private String accessToken;
    private String refreshToken;
}