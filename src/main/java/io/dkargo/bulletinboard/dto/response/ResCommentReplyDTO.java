package io.dkargo.bulletinboard.dto.response;

import io.dkargo.bulletinboard.entity.Comment;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ApiModel
@Getter
@Setter
public class ResCommentReplyDTO {
    @ApiModelProperty(value = "댓글 아이디")
    private Long id;

    @ApiModelProperty(value = "유저 아이디")
    private Long userId;

    @ApiModelProperty(value = "댓글 내용")
    private String content;

    @ApiModelProperty(value = "댓글 생성 시간")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "답글 리스트")
    private List<ResReplyDTO> resReplyDTOList;

    public ResCommentReplyDTO(Comment comment) {
        this.id = comment.getId();
        this.userId = comment.getUser().getId();
        this.content = comment.getContent();
        this.createTime = comment.getCreatedDate();
        this.resReplyDTOList = comment.getReplyList().stream().map(ResReplyDTO::new).collect(Collectors.toList());
    }
}
