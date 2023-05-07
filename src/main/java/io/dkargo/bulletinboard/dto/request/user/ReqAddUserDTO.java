package io.dkargo.bulletinboard.dto.request.user;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Schema
@Getter
@Setter
public class ReqAddUserDTO {
    @Schema(description = "유저 이름", requiredMode = Schema.RequiredMode.REQUIRED, example = "박진호")
    @Size(max = 20, message = "이름은 최대 20까지 받을수 있습니다.")
    @NotBlank
    private String userName;
}
