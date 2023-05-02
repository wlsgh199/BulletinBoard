package io.dkargo.bulletinboard.dto.request;


import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@ApiModel
@Getter @Setter
public class ReqReplyDTO {
    private Long commentId;
    private Long userId;
    private String content;
    private Integer depth;
}
