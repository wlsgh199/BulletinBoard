package io.dkargo.bulletinboard.dto.request.post;

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
    private Long userId;
    @ApiModelProperty(value = "카테고리 아이디")
    private Long categoryId;
    @ApiModelProperty(value = "게시물 제목")
    private String title;
    @ApiModelProperty(value = "게시물 내용")
    private String content;

    @ApiModelProperty(value = "페이징 offset" , example = "0")
    private Long page;
    @ApiModelProperty(value = "페이징 사이즈", example = "10")
    private Long size;
    @ApiModelProperty(value = "정렬 기준", required = true)
    private OrderByListEnum orderByListEnum;

}
