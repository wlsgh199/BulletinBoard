package io.dkargo.bulletinboard.dto.response;


import io.dkargo.bulletinboard.entity.Reply;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@ApiModel
@Getter
@Setter
public class ResReplyDTO {
    @ApiModelProperty(value = "답글 아이디")
    private Long id;

    @ApiModelProperty(value = "유저 아이디")
    private Long userId;

    @ApiModelProperty(value = "답글 내용")
    private String content;

    @ApiModelProperty(value = "답글 생성 시간")
    private LocalDateTime createTime;

    public ResReplyDTO(Reply reply) {
        this.id = reply.getId();
        this.userId = reply.getUser().getId();
        this.content = reply.getContent();
        this.createTime = reply.getCreatedDate();
    }
}
