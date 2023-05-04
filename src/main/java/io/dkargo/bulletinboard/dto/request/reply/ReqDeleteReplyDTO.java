package io.dkargo.bulletinboard.dto.request.reply;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Schema
@Getter
@Setter
public class ReqDeleteReplyDTO {
    @Schema(description = "답글 아이디", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull
    private Long replyId;

    @Schema(description = "유저 아이디", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull
    private Long userId;
}
