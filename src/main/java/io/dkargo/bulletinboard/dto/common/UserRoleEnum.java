package io.dkargo.bulletinboard.dto.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserRoleEnum {

    USER("ROLE_USER", "사용자"),
    ADMIN("ROLE_ADMIN", "관리자");
    private final String role;
    private final String description;
}
