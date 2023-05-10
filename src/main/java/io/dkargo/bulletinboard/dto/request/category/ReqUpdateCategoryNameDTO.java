package io.dkargo.bulletinboard.dto.request.category;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Schema
@Getter
@Setter
public class ReqUpdateCategoryNameDTO {
    @Schema(description = "카테고리 이름")
    @Size(max = 20, message = "카테고리 이름은 최대 20자 입니다.")
    @NotBlank
    private String categoryName;
}
