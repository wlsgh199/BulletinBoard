package io.dkargo.bulletinboard.dto.response.post;

import io.dkargo.bulletinboard.entity.Post;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Schema
@Getter
@Setter
public class ResFindDetailPostDTO {

    @Schema(description = "게시물 아이디")
    private Long postId;

    @Schema(description = "유저 아이디")
    private Long userId;

    @Schema(description = "게시물 제목")
    private String title;

    @Schema(description = "게시물 내용")
    private String content;

    @Schema(description = "게시물 공개 여부")
    private boolean postOpenUseFlag;

    @Schema(description = "댓글/답글 사용 여부")
    private Boolean replyCommentUseFlag;

    @Schema(description = "조회수")
    private Long clickCount;

    @Schema(description = "파일 리스트")
    private List<ResFindPostFileDTO> postFileList;

    @Schema(description = "게시물 생성 시간")
    private LocalDateTime createTime;

    @Builder
    public ResFindDetailPostDTO(Post post) {
        this.postId = post.getId();
        this.userId = post.getMember().getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.postOpenUseFlag = post.getPostOpenUseFlag();
        this.replyCommentUseFlag = post.getReplyCommentUseFlag();
        this.clickCount = post.getClickCount();
        this.createTime = post.getCreatedDate();
        this.postFileList = post.getPostFileList().stream().map(ResFindPostFileDTO::new).collect(Collectors.toList());
    }

}
