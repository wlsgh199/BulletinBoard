package io.dkargo.bulletinboard.dto.response.comment;

import io.dkargo.bulletinboard.dto.response.reply.ResFindReplyDTO;
import io.dkargo.bulletinboard.entity.Comment;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Schema
@Getter
@Setter
public class ResFindCommentReplyDTO {
    @Schema(description = "댓글 아이디")
    private Long id;

    @Schema(description = "유저 아이디")
    private Long userId;

    @Schema(description = "댓글 내용")
    private String content;

    @Schema(description = "댓글 생성 시간")
    private LocalDateTime createTime;

    @Schema(description = "답글 리스트")
    private List<ResFindReplyDTO> resFindReplyDTOList;

    public ResFindCommentReplyDTO(Comment comment) {
        this.id = comment.getId();
        this.userId = comment.getMember().getId();
        this.content = comment.getContent();
        this.createTime = comment.getCreatedDate();
        this.resFindReplyDTOList = comment.getReplyList().stream()
                .map(ResFindReplyDTO::new)
                .collect(Collectors.toList());
    }
}
