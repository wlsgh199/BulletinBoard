package io.dkargo.bulletinboard.dto.request.post;

import com.sun.istack.NotNull;
import io.dkargo.bulletinboard.dto.common.OrderByListEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel
@Getter
@Setter
public class ReqGetDTO {
    @ApiModelProperty(value = "유저 아이디")
    @NotNull
    private Long userId;

    @ApiModelProperty(value = "카테고리 아이디")
    private Long categoryId;
    @ApiModelProperty(value = "게시물 제목")
    private String title;

    @ApiModelProperty(value = "게시물 내용")
    private String content;

    @ApiModelProperty(value = "페이징 offset", example = "0")
    private int page;
    @ApiModelProperty(value = "페이징 사이즈", example = "10")
    private int size;
    @ApiModelProperty(value = "정렬 기준", required = true)
    private OrderByListEnum orderByListEnum;

}
