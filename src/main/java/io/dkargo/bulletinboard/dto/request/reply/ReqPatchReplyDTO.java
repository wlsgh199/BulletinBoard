package io.dkargo.bulletinboard.dto.request.reply;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel
@Getter
@Setter
public class ReqPatchReplyDTO {
    @ApiModelProperty(value = "답글 아이디", required = true)
    private Long replyId;
    @ApiModelProperty(value = "유저 아이디", required = true)
    private Long userId;
    @ApiModelProperty(value = "답글 내용", required = true)
    private String content;
}
