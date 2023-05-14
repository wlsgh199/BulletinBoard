package io.dkargo.bulletinboard.dto.response.comment;

import io.dkargo.bulletinboard.entity.Comment;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Schema
@Getter
@Setter
public class ResCreateCommentDTO {
    @Schema(description = "댓글 내용")
    private String content;

    public ResCreateCommentDTO(Comment comment) {
        this.content = comment.getContent();
    }
}
