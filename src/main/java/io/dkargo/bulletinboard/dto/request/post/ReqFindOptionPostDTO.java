package io.dkargo.bulletinboard.dto.request.post;

import io.dkargo.bulletinboard.dto.common.OrderByListEnum;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.Getter;
import lombok.Setter;
import org.springdoc.api.annotations.ParameterObject;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import com.querydsl.core.types.Order;

@ParameterObject
@Getter
@Setter
public class ReqFindOptionPostDTO {
    @Parameter(description = "유저 아이디")
    private Long userId;

    @Parameter(description = "카테고리 아이디")
    private Long categoryId;

    @Parameter(description = "게시물 제목")
    @Size(max = 200, message = "제목은 최대 200자 까지 검색 옵션으로 정할수 있습니다.")
    private String title;

    @Parameter(description = "게시물 내용")
    private String content;

    @Parameter(description = "페이징 offset", example = "0")
    private int page = 0;

    @Parameter(description = "페이징 사이즈", example = "10")
    @Max(value = 50, message = "페이징 사이즈는 최대 50까지 할수 있습니다.")
    private int size = 10;

    @Parameter(description = "정렬", example = "DESC", required = true)
    private Order order;

    @Parameter(description = "정렬 기준", required = true)
    @NotNull
    private OrderByListEnum orderByListEnum;
}
