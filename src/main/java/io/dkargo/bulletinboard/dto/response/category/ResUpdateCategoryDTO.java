package io.dkargo.bulletinboard.dto.response.category;

import io.dkargo.bulletinboard.entity.Category;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Schema
@Getter
@Setter
public class ResUpdateCategoryDTO {
    @Schema(description = "부모 카테고리 ID")
    private Long parentId;

    @Schema(description = "카테고리 이름")
    private String categoryName;

    public ResUpdateCategoryDTO(Category category) {
        this.parentId = category.getParentId();
        this.categoryName = category.getCategoryName();
    }
}