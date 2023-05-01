package io.dkargo.bulletinboard.dto.response;


import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@ApiModel
@Getter @Setter
public class ResReplyDTO {
    private Long id;
    private Long userId;
    private String content;
    private String createTime;
}
