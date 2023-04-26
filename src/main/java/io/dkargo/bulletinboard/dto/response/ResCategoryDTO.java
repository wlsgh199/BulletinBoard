package io.dkargo.bulletinboard.dto.response;

import io.dkargo.bulletinboard.entity.Category;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResCategoryDTO {
    /**
     * 카테고리 아이디
     */
    private Long id;

    /**
     * 카테고리 부모 아이디
     */
    private Integer parentId;

    /**
     * 카테고리 이름
     */
    private String categoryName;

    /**
     * 깊이
     */
    private Integer depth;

    public ResCategoryDTO(Category category) {
        this.id = category.getId();
        this.parentId = category.getParentId();
        this.categoryName = category.getCategoryName();
        this.depth = category.getDepth();
    }
}
