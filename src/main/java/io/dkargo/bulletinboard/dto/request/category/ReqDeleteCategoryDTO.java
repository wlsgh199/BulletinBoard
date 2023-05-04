package io.dkargo.bulletinboard.dto.request.category;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ReqDeleteCategoryDTO {

    @ApiModelProperty(value = "카테고리 ID", required = true, example = "1")
    @NotNull
    private Long categoryId;
}
