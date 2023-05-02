package io.dkargo.bulletinboard.dto.request.category;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReqDeleteCategoryDTO {
    @ApiModelProperty(value = "카테고리 ID")
    private Long categoryId;
}
