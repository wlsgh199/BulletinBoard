package io.dkargo.bulletinboard.dto.request.category;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ReqDeleteCategoryDTO {
    //TODO : 전체적으로 DTO 에서 id 부분은 @PathVariable 으로 변경해야함
    @Schema(description = "카테고리 ID", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    private Long categoryId;
}
