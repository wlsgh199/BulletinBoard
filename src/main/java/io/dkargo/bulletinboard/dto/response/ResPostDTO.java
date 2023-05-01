package io.dkargo.bulletinboard.dto.response;

import io.dkargo.bulletinboard.entity.Post;
import io.swagger.annotations.ApiModel;
import lombok.*;

import javax.persistence.Column;
import java.time.LocalDateTime;

@ApiModel
@Getter @Setter
public class ResPostDTO {
    private Long postId;
    private String title;
    private String content;
    private String postOpenUseFlag;
    private String replyCommentUseFlag;
    private Long clickCount;
    private LocalDateTime createTime;

    public ResPostDTO(Post post) {
        this.postId = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.postOpenUseFlag = post.getPostOpenUseFlag();
        this.replyCommentUseFlag = post.getReplyCommentUseFlag();
        this.clickCount = post.getClickCount();
        this.createTime = post.getCreatedDate();
    }
}
