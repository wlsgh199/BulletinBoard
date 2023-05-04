package io.dkargo.bulletinboard.dto.request.category;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@ApiModel
@Getter
@Setter
public class ReqPutCategoryDTO {
    @ApiModelProperty(value = "카테고리 ID", required = true, example = "1")
    @NotNull
    private Long categoryId;

    @ApiModelProperty(value = "부모 카테고리 ID", required = true, example = "1")
    private Long parentId;

    @ApiModelProperty(value = "카테고리 이름", required = true)
    @Size(max = 20, message = "카테고리 이름은 최대 20자 입니다.")
    @NotBlank
    private String categoryName;

    @ApiModelProperty(value = "깊이", example = "1", required = true)
    private int depth;
}
