package io.dkargo.bulletinboard.dto.request.post;

import io.dkargo.bulletinboard.dto.common.OrderByListEnum;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Schema
@Getter
@Setter
public class ReqFindOptionPostDTO {
    @Schema(description = "유저 아이디")
    private Long userId;

    @Schema(description = "카테고리 아이디")
    private Long categoryId;

    @Schema(description = "게시물 제목")
    @Size(max = 200, message = "제목은 최대 200자 까지 검색 옵션으로 정할수 있습니다.")
    private String title;

    @Schema(description = "게시물 내용")
    private String content;

    @Schema(description = "페이징 offset", example = "0")
    private int page;

    @Schema(description = "페이징 사이즈", example = "10")
    @Max(value = 50, message = "페이징 사이즈는 최대 50까지 할수 있습니다.")
    private int size;

    @Schema(type = "Enum", description = "정렬 기준", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    private OrderByListEnum orderByListEnum;
}
