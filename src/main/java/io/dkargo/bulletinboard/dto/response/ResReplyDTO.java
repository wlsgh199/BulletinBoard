package io.dkargo.bulletinboard.dto.response;


import io.dkargo.bulletinboard.entity.Reply;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Schema
@Getter
@Setter
public class ResReplyDTO {
    @Schema(description = "답글 아이디")
    private Long id;

    @Schema(description = "유저 아이디")
    private Long userId;

    @Schema(description = "답글 내용")
    private String content;

    @Schema(description = "답글 생성 시간")
    private LocalDateTime createTime;

    public ResReplyDTO(Reply reply) {
        this.id = reply.getId();
        this.userId = reply.getMember().getId();
        this.content = reply.getContent();
        this.createTime = reply.getCreatedDate();
    }
}
