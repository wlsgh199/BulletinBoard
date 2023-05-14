package io.dkargo.bulletinboard.dto.response.category;

import io.dkargo.bulletinboard.entity.Category;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Schema
@Getter
@Setter
public class ResCreateCategoryDTO {
    @Schema(description = "카테고리 이름")
    private String categoryName;

    public ResCreateCategoryDTO(Category category) {
        this.categoryName = category.getCategoryName();
    }
}
