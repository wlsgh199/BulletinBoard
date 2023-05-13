package io.dkargo.bulletinboard.dto.request.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema
public class UserTokenDTO {
    private String grantType;
    private String accessToken;
    private String refreshToken;
}