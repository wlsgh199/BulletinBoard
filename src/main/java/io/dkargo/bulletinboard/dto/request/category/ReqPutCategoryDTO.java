package io.dkargo.bulletinboard.dto.request.category;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Schema
@Getter
@Setter
public class ReqPutCategoryDTO {
    @Schema(description = "카테고리 ID", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    private Long categoryId;

    @Schema(description = "부모 카테고리 ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long parentId;

    @Schema(description = "카테고리 이름", requiredMode = Schema.RequiredMode.REQUIRED)
    @Size(max = 20, message = "카테고리 이름은 최대 20자 입니다.")
    @NotBlank
    private String categoryName;
}
