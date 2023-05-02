package io.dkargo.bulletinboard.dto.request.comment;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel
@Getter
@Setter
public class ReqPatchCommentDTO {
    @ApiModelProperty(value = "댓글 아이디", required = true)
    private Long commentId;
    @ApiModelProperty(value = "유저 아이디", required = true)
    private Long userId;
    @ApiModelProperty(value = "댓글 내용", required = true)
    private String content;
}
