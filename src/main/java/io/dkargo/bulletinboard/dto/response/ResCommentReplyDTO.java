package io.dkargo.bulletinboard.dto.response;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@ApiModel
@Getter @Setter
public class ResCommentReplyDTO {
    private Long id;
    private Long userId;
    private String content;
    private String createTime;
    private List<ResReplyDTO> resReplyDTOList;
}
