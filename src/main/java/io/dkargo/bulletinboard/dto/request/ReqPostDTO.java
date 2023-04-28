package io.dkargo.bulletinboard.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel
@Getter
@Setter
public class ReqPostDTO {
    @ApiModelProperty(value = "유저 아이디")
    private Long userId;
    @ApiModelProperty(value = "게시물 제목")
    private String title;
    @ApiModelProperty(value = "게시물 내용")
    private String content;
    @ApiModelProperty(value = "게시물 비공개 여부")
    private String postOpenUseFlag;
    @ApiModelProperty(value = "게시물 비밀번호")
    private String postPassword;
    @ApiModelProperty(value = "댑글/답글 사용 여부")
    private String replyCommentUseFlag;
    @ApiModelProperty(value = "카테고리 아이디")
    private Long categoryId;
}
