package io.dkargo.bulletinboard.dto.request.reply;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@ApiModel
@Getter
@Setter
public class ReqDeleteReplyDTO {
    @ApiModelProperty(value = "답글 아이디", required = true)
    @NotNull
    private Long replyId;

    @ApiModelProperty(value = "유저 아이디", required = true)
    @NotNull
    private Long userId;
}
