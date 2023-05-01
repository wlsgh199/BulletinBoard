package io.dkargo.bulletinboard.dto.response;

import io.dkargo.bulletinboard.entity.Comment;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ApiModel
@Getter
@Setter
public class ResCommentReplyDTO {
    private Long id;
    private Long memberId;
    private String content;
    private LocalDateTime createTime;
    private List<ResReplyDTO> resReplyDTOList;

    public ResCommentReplyDTO(Comment comment) {
        this.id = comment.getId();
        this.memberId = comment.getMember().getId();
        this.content = comment.getContent();
        this.createTime = comment.getCreatedDate();
        this.resReplyDTOList = comment.getReplyList().stream().map(ResReplyDTO::new).collect(Collectors.toList());
    }
}
