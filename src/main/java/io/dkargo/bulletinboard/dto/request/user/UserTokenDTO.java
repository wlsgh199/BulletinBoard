package io.dkargo.bulletinboard.dto.request.user;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class UserTokenDTO {

    private String grantType;
    private String accessToken;
    private String refreshToken;
}