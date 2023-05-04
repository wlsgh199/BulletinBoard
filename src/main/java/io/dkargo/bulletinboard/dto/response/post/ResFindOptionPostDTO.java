package io.dkargo.bulletinboard.dto.response.post;

import io.dkargo.bulletinboard.dto.common.BooleanToYNConverter;
import io.dkargo.bulletinboard.entity.Post;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.Convert;
import java.time.LocalDateTime;

@ApiModel
@Getter
@Setter
public class ResFindOptionPostDTO {
    @ApiModelProperty(value = "게시물 아이디")
    private Long postId;

    @ApiModelProperty(value = "유저 아이디")
    private Long userId;

    @ApiModelProperty(value = "게시물 제목")
    private String title;

    @ApiModelProperty(value = "게시물 공개 여부")
    private Boolean postOpenUseFlag;

    @ApiModelProperty(value = "게시물 비밀번호")
    private String postPassword;

    @ApiModelProperty(value = "댓글/답글 사용 여부")
    private Boolean replyCommentUseFlag;

    @ApiModelProperty(value = "댓글 개수")
    private int commentCount;

    @ApiModelProperty(value = "조회수")
    private Long clickCount;

    @ApiModelProperty(value = "게시물 생성 시간")
    private LocalDateTime createTime;

    @Builder
    public ResFindOptionPostDTO(Post post) {
        this.postId = post.getId();
        this.userId = post.getUser().getId();
        this.title = post.getTitle();
        this.postOpenUseFlag = post.getPostOpenUseFlag();
        this.postPassword = post.getPostPassword();
        this.replyCommentUseFlag = post.getReplyCommentUseFlag();
        this.commentCount = post.getCommentList().size();
        this.clickCount = post.getClickCount();
        this.createTime = post.getCreatedDate();
    }
}
