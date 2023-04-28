package io.dkargo.bulletinboard.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReqPostDTO {
    private Long userId;
    private String title;
    private String content;
    private String postOpenUseFlag;
    private String postPassword;
    private String replyCommentUseFlag;
    private Long categoryId;

//    private List<File> fileList;
}
