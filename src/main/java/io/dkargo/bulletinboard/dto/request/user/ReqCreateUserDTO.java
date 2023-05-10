package io.dkargo.bulletinboard.dto.request.user;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;

@Schema
@Getter
@Setter
public class ReqCreateUserDTO {
    @Schema(description = "유저 이름", requiredMode = Schema.RequiredMode.REQUIRED, example = "박진호")
    @Size(max = 20, message = "이름은 최대 20까지 받을수 있습니다.")
    @NotBlank(message = "이름은 필수 입력 값입니다.")
    private String userName;

    @Schema(description = "유저 메일", requiredMode = Schema.RequiredMode.REQUIRED, example = "jhpark@dkargo.io")
    @Email
//    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$", message = "이메일 형식에 맞지 않습니다.")
    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    private String userEmail;

    @Schema(description = "유저 비밀번호", requiredMode = Schema.RequiredMode.REQUIRED, example = "1234")
    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    private String userPassword;

    //TODO : 비밀번호 양식 설정
    // @Pattern(regexp="(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}",
    //            message = "비밀번호는 영문 대,소문자와 숫자, 특수기호가 적어도 1개 이상씩 포함된 8자 ~ 20자의 비밀번호여야 합니다.")
    // (?=.*[0-9])
    // 숫자 적어도 하나
    // (?=.*[a-zA-Z])
    // 영문 대,소문자중 적어도 하나
    // (?=.*\\W)
    // 특수문자 적어도 하나
    // (?=\\S+$)
    // 공백 제거
    // 정규표현식에 맞는 문자열이어야 함
}
