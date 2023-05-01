package io.dkargo.bulletinboard.dto.response;


import io.dkargo.bulletinboard.entity.Reply;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@ApiModel
@Getter @Setter
public class ResReplyDTO {
    private Long id;
    private Long userId;
    private String content;
    private LocalDateTime createTime;

    public ResReplyDTO(Reply reply) {
        this.id = reply.getId();
        this.userId = reply.getMember().getId();
        this.content = reply.getContent();
        this.createTime = reply.getCreatedDate();
    }
}
