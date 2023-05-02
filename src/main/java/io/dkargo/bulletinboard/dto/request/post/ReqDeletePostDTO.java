package io.dkargo.bulletinboard.dto.request.post;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel
@Getter
@Setter
public class ReqDeletePostDTO {
    @ApiModelProperty(value = "게시물 아이디", required = true, example = "1")
    private Long id;
    @ApiModelProperty(value = "유저 아이디", example = "1")
    private Long userId;
}
