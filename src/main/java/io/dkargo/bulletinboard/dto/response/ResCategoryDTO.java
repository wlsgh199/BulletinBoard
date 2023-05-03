package io.dkargo.bulletinboard.dto.response;

import io.dkargo.bulletinboard.entity.Category;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResCategoryDTO {

    @ApiModelProperty(value = "카테고리 아이디")
    private Long id;

    @ApiModelProperty(value = "카테고리 부모 아이디")
    private Long parentId;

    @ApiModelProperty(value = "카테고리 이름", required = true)
    private String categoryName;

    @ApiModelProperty(value = "깊이" ,  required = true, example = "1")
    private Integer depth;

    @Builder
    public ResCategoryDTO(Category category) {
        this.id = category.getId();
        this.parentId = category.getParentId();
        this.categoryName = category.getCategoryName();
        this.depth = category.getDepth();
    }
}
