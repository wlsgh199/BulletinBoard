package io.dkargo.bulletinboard.dto.request.comment;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Schema
@Getter
@Setter
public class ReqDeleteCommentDTO {
    @Schema(description = "댓글 아이디", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull
    private Long commentId;

    @Schema(description = "유저 아이디", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull
    private Long userId;
}
