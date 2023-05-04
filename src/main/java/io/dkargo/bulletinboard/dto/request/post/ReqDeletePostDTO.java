package io.dkargo.bulletinboard.dto.request.post;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Schema
@Getter
@Setter
public class ReqDeletePostDTO {
    @Schema(description = "게시물 아이디", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull
    private Long id;

    @Schema(description = "유저 아이디", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull
    private Long userId;
}
