package io.dkargo.bulletinboard.dto.request;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@ApiModel
@Getter @Setter
public class ReqCommentDTO {
    private Long postId;
    private Long memberId;
    private String content;
    private Integer depth;
}
