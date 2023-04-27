package io.dkargo.bulletinboard.dto.request;

import io.dkargo.bulletinboard.entity.File;
import io.dkargo.bulletinboard.entity.Member;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

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
