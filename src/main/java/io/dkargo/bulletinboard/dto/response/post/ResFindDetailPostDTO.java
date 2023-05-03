package io.dkargo.bulletinboard.dto.response.post;

import io.dkargo.bulletinboard.dto.response.ResPostFileDTO;
import io.dkargo.bulletinboard.entity.Post;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ApiModel
@Getter
@Setter
public class ResFindDetailPostDTO {

    @ApiModelProperty(value = "게시물 아이디")
    private Long postId;

    @ApiModelProperty(value = "유저 아이디")
    private Long userId;

    @ApiModelProperty(value = "게시물 제목")
    private String title;

    @ApiModelProperty(value = "게시물 내용")
    private String content;

    @ApiModelProperty(value = "게시물 공개 여부")
    private String postOpenUseFlag;

    @ApiModelProperty(value = "댓글/답글 사용 여부")
    private String replyCommentUseFlag;

    @ApiModelProperty(value = "조회수")
    private Long clickCount;

    @ApiModelProperty(value = "파일 리스트")
    private List<ResPostFileDTO> postFileList;

    @ApiModelProperty(value = "게시물 생성 시간")
    private LocalDateTime createTime;

    @Builder
    public ResFindDetailPostDTO(Post post) {
        this.postId = post.getId();
        this.userId = post.getUser().getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.postOpenUseFlag = post.getPostOpenUseFlag();
        this.replyCommentUseFlag = post.getReplyCommentUseFlag();
        this.clickCount = post.getClickCount();
        this.createTime = post.getCreatedDate();
        this.postFileList = post.getPostFileList().stream().map(ResPostFileDTO::new).collect(Collectors.toList());
    }

}
