package io.dkargo.bulletinboard.dto.request.comment;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@ApiModel
@Getter
@Setter
public class ReqPutCommentDTO {
    @ApiModelProperty(value = "댓글 아이디", required = true)
    @NotNull
    private Long commentId;

    @ApiModelProperty(value = "유저 아이디", required = true)
    @NotNull
    private Long userId;

    @ApiModelProperty(value = "댓글 내용", required = true)
    @Size(max = 3000, message = "댓글은 3000자 까지 작성할 수 있습니다.")
    @NotBlank
    private String content;
}
