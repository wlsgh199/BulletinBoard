package io.dkargo.bulletinboard.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

@ApiModel
@Getter @Setter
public class ReqCategoryDTO {

    @ApiModelProperty(value = "부모 카테고리 ID")
    @Nullable
    private Long parentId;

    @ApiModelProperty(value = "카테고리 이름")
    private String categoryName;

    @ApiModelProperty(value = "깊이")
    private Integer depth;
}
