package io.dkargo.bulletinboard.dto.response;

import io.dkargo.bulletinboard.entity.Category;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResFindCategoryDTO {
    @Schema(description = "카테고리 부모 아이디")
    private Integer parentId;

    @Schema(description = "카테고리 이름")
    private String categoryName;

    @Builder
    public ResFindCategoryDTO(Category category) {
        this.parentId = category.getParentId();
        this.categoryName = category.getCategoryName();
    }
}
