package io.dkargo.bulletinboard.dto.request.category;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@ApiModel
@Getter
@Setter
public class ReqAddCategoryDTO {

    @ApiModelProperty(value = "부모 카테고리 ID", example = "1")
    @Nullable
    private Long parentId;

    @ApiModelProperty(value = "카테고리 이름", required = true, example = "카테고리 이름")
    @Size(max = 20, message = "카테고리 이름은 최대 20자 입니다.")
    @NotBlank
    private String categoryName;


    @ApiModelProperty(value = "깊이", required = true, example = "1")
    private int depth;
}
