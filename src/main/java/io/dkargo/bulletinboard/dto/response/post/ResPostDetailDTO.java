package io.dkargo.bulletinboard.dto.response.post;

import io.dkargo.bulletinboard.dto.response.ResPostFileDTO;
import io.dkargo.bulletinboard.entity.Post;
import io.dkargo.bulletinboard.entity.PostFile;
import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ApiModel
@Getter @Setter
public class ResPostDetailDTO {
    private Long postId;
    private Long userId;
    private String title;
    private String content;
    private String postOpenUseFlag;
    private String replyCommentUseFlag;
    private Long clickCount;
    private List<ResPostFileDTO> postFileList;
    private LocalDateTime createTime;

    @Builder
    public ResPostDetailDTO(Post post) {
        this.postId = post.getId();
        this.userId = post.user.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.postOpenUseFlag = post.getPostOpenUseFlag();
        this.replyCommentUseFlag = post.getReplyCommentUseFlag();
        this.clickCount = post.getClickCount();
        this.createTime = post.getCreatedDate();
        this.postFileList = post.getPostFileList().stream().map(ResPostFileDTO::new).collect(Collectors.toList());
    }

}
