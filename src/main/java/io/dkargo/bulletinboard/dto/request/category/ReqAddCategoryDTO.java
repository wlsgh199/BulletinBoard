package io.dkargo.bulletinboard.dto.request.category;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel
@Getter
@Setter
public class ReqAddCategoryDTO {

    @ApiModelProperty(value = "부모 카테고리 ID")
    private Long parentId;

    @ApiModelProperty(value = "카테고리 이름")
    private String categoryName;

    @ApiModelProperty(value = "깊이")
    private Integer depth;
}
