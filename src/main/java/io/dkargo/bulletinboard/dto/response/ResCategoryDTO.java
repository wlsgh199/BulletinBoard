package io.dkargo.bulletinboard.dto.response;

import io.dkargo.bulletinboard.entity.Category;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResCategoryDTO {

    @Schema(description = "카테고리 아이디")
    private Long id;

    @Schema(description = "카테고리 부모 아이디")
    private Long parentId;

    @Schema(description = "카테고리 이름")
    private String categoryName;

    @Schema(description = "깊이", example = "1")
    private Integer depth;

    @Builder
    public ResCategoryDTO(Category category) {
        this.id = category.getId();
        this.parentId = category.getParentId();
        this.categoryName = category.getCategoryName();
        this.depth = category.getDepth();
    }
}
