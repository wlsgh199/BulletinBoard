package io.dkargo.bulletinboard.dto.request;

import io.dkargo.bulletinboard.entity.Member;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReqPostDTO {
    private Member member;
    private String title;
    private String content;
    private String postOpenUseFlag;
    private String postPassword;
    private String replyCommentUseFlag;
}
