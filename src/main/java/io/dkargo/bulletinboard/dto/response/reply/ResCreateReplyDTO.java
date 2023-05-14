package io.dkargo.bulletinboard.dto.response.reply;


import io.dkargo.bulletinboard.entity.Reply;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Schema
@Getter
@Setter
public class ResCreateReplyDTO {
    @Schema(description = "답글 내용")
    private String content;

    public ResCreateReplyDTO(Reply reply) {
        this .content = reply.getContent();
    }
}
