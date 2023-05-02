package io.dkargo.bulletinboard.dto.request;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel
@Getter @Setter
public class ReqUserDTO {
    @ApiModelProperty(value = "유저 이름", required = true, example = "박진호")
    private String userName;
}
