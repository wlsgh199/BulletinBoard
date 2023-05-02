package io.dkargo.bulletinboard.dto.response.post;

import io.dkargo.bulletinboard.entity.Post;
import io.swagger.annotations.ApiModel;
import lombok.*;

import java.time.LocalDateTime;

@ApiModel
@Getter @Setter
public class ResPostDTO {
    private Long postId;
    private Long userId;
    private String title;
    private String postOpenUseFlag;
    private String postPassword;
    private String replyCommentUseFlag;
    private int commentCount;
    private Long clickCount;
    private LocalDateTime createTime;

    @Builder
    public ResPostDTO(Post post) {
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
