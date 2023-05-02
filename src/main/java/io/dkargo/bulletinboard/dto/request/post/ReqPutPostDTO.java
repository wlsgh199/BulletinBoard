package io.dkargo.bulletinboard.dto.request.post;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel
@Getter
@Setter
public class ReqPutPostDTO {

    @ApiModelProperty(value = "게시물 아이디", required = true, example = "1")
    private Long id;
    @ApiModelProperty(value = "유저 아이디", example = "1")
    private Long userId;
    @ApiModelProperty(value = "게시물 제목")
    private String title;
    @ApiModelProperty(value = "게시물 내용")
    private String content;
    @ApiModelProperty(value = "게시물 비공개 여부")
    private String postOpenUseFlag;
    @ApiModelProperty(value = "게시물 비밀번호")
    private String postPassword;
    @ApiModelProperty(value = "댓글/답글 사용 여부")
    private String replyCommentUseFlag;
    @ApiModelProperty(value = "카테고리 아이디", example = "1")
    private Long categoryId;
}
