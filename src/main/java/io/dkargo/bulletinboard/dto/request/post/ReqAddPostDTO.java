package io.dkargo.bulletinboard.dto.request.post;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@ApiModel
@Getter
@Setter
public class ReqAddPostDTO {
    @ApiModelProperty(value = "유저 아이디", required = true)
    private Long userId;
    @ApiModelProperty(value = "게시물 제목", required = true)
    private String title;
    @ApiModelProperty(value = "게시물 내용", required = true)
    private String content;
    @ApiModelProperty(value = "게시물 비공개 여부", required = true)
    private String postOpenUseFlag;
    @ApiModelProperty(value = "게시물 비밀번호")
    private String postPassword;
    @ApiModelProperty(value = "댓글/답글 사용 여부", required = true)
    private String replyCommentUseFlag;
    @ApiModelProperty(value = "카테고리 아이디", required = true)
    private Long categoryId;
    @ApiModelProperty(value = "업로드 파일 리스트")
    private List<MultipartFile> files;
}
