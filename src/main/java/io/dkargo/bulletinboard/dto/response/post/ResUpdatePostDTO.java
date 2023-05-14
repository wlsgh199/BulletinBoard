package io.dkargo.bulletinboard.dto.response.post;

import io.dkargo.bulletinboard.entity.Post;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Schema
@Getter
@Setter
public class ResUpdatePostDTO {
//    @Schema(description = "유저 아이디")
//    private Long userId;

    @Schema(description = "게시물 제목")
    private String title;

    @Schema(description = "게시물 내용")
    private String content;

    @Schema(description = "게시물 공개 여부")
    private Boolean postOpenUseFlag;

//    @Schema(description = "게시물 비밀번호")
//    private String postPassword;

    @Schema(description = "댓글/답글 사용 여부")
    private Boolean replyCommentUseFlag;

//    @Schema(description = "카테고리 아이디")
//    private Long categoryId;
//
//    @Schema(description = "업로드 파일 리스트")
//    private List<MultipartFile> files;


    public ResUpdatePostDTO(Post post) {
        this.title = post.getTitle();
        this.content = post.getContent();
        this.postOpenUseFlag = post.getPostOpenUseFlag();
        this.replyCommentUseFlag = post.getReplyCommentUseFlag();
    }
}
