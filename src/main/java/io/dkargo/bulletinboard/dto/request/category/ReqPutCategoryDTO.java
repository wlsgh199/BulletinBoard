package io.dkargo.bulletinboard.dto.request.category;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel
@Getter
@Setter
public class ReqPutCategoryDTO {
    @ApiModelProperty(value = "카테고리 ID", required = true)
    private Long categoryId;

    @ApiModelProperty(value = "부모 카테고리 ID")
    private Long parentId;

    @ApiModelProperty(value = "카테고리 이름")
    private String categoryName;

    @ApiModelProperty(value = "깊이", example = "1")
    private Integer depth;
}
