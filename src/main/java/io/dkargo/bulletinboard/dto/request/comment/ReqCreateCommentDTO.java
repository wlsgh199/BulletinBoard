package io.dkargo.bulletinboard.dto.request.comment;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Schema
@Getter
@Setter
public class ReqCreateCommentDTO {
    @Schema(description = "댓글 내용", requiredMode = Schema.RequiredMode.REQUIRED)
    @Size(max = 3000, message = "댓글은 최대 3000자 까지 작성할 수 있습니다.")
    @NotBlank
    private String content;
}
