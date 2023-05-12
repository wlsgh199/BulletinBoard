package io.dkargo.bulletinboard.dto.request.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReqUserLoginDTO {
    private String email;
    private String password;
}
