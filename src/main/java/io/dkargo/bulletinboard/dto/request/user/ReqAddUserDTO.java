package io.dkargo.bulletinboard.dto.request.user;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@ApiModel
@Getter
@Setter
public class ReqAddUserDTO {
    @ApiModelProperty(value = "유저 이름", required = true, example = "박진호")
    @Size(max = 20, message = "이름은 최대 20까지 받을수 있습니다.")
    @NotBlank
    private String userName;
}
