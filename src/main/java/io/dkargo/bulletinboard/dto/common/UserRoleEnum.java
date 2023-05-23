package io.dkargo.bulletinboard.dto.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserRoleEnum {

    USER("ROLE_USER", "사용자"),
    ADMIN("ROLE_ADMIN", "관리자");

    private final String value;
    private final String description;



    public static UserRoleEnum valueToEnum(String value) {
        for (UserRoleEnum userRoleEnum : values()) {
            if (userRoleEnum.value.equals(value)) {
                return userRoleEnum;
            }
        }
        return null;
    }


//    private UserRoleEnum(String value, String description) {
//        this.value = value;
//        this.description = description;
//    }
//
//    private UserRoleEnum(String value) {
//        this.value = value;
//    }
}
