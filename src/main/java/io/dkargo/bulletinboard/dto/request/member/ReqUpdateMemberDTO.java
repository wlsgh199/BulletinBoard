package io.dkargo.bulletinboard.dto.request.member;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Schema
@Getter
@Setter
public class ReqUpdateMemberDTO {
    @Schema(description = "유저 이름", requiredMode = Schema.RequiredMode.REQUIRED, example = "박진호")
    @Size(max = 20, message = "이름은 최대 20까지 받을수 있습니다.")
    @NotBlank(message = "이름은 필수 입력 값입니다.")
    private String name;

    @Schema(description = "유저 비밀번호", requiredMode = Schema.RequiredMode.REQUIRED, example = "Aasd!12345")
    @Pattern(regexp="(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}",
            message = "비밀번호는 영문 대,소문자와 숫자, 특수기호가 적어도 1개 이상씩 포함된 8자 ~ 20자의 비밀번호여야 합니다.")
    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    private String password;

    @Schema(description = "새로운 비밀번호", requiredMode = Schema.RequiredMode.REQUIRED, example = "Basd!12345")
    @Pattern(regexp="(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}",
            message = "비밀번호는 영문 대,소문자와 숫자, 특수기호가 적어도 1개 이상씩 포함된 8자 ~ 20자의 비밀번호여야 합니다.")
    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    private String newPassword;
}
