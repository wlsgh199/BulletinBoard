package io.dkargo.bulletinboard.dto.response.post;

import io.dkargo.bulletinboard.dto.common.BooleanToYNConverter;
import io.dkargo.bulletinboard.entity.Post;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.persistence.Convert;
import java.time.LocalDateTime;

@Schema
@Getter
@Setter
public class ResFindOptionPostDTO {
    @Schema(description = "게시물 아이디")
    private Long postId;

    @Schema(description = "유저 아이디")
    private Long userId;

    @Schema(description = "게시물 제목")
    private String title;

    @Schema(description = "게시물 공개 여부")
    private Boolean postOpenUseFlag;

    @Schema(description = "게시물 비밀번호")
    private String postPassword;

    @Schema(description = "댓글/답글 사용 여부")
    private Boolean replyCommentUseFlag;

    @Schema(description = "댓글 개수")
    private int commentCount;

    @Schema(description = "조회수")
    private Long clickCount;

    @Schema(description = "게시물 생성 시간")
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
