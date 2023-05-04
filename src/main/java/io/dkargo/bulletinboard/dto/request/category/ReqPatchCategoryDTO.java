package io.dkargo.bulletinboard.dto.request.category;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Schema
@Getter
@Setter
public class ReqPatchCategoryDTO {

    @Schema(description = "카테고리 ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull
    private Long categoryId;

    @Schema(description = "부모 카테고리 ID", example = "1")
    private Long parentId;

    @Schema(description = "카테고리 이름")
    @Size(max = 20, message = "카테고리 이름은 최대 20자 입니다.")
    private String categoryName;

    @Schema(description = "깊이", example = "1")
    private Integer depth;
}
