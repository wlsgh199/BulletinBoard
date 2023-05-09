package io.dkargo.bulletinboard.dto.request.post;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Convert;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Schema
@Getter
@Setter
public class ReqPatchPostDTO {
    @Schema(description = "게시물 아이디", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    private Long id;

    @Schema(description = "유저 아이디")
    private Long userId;

    @Schema(description = "게시물 제목")
    @Size(max = 200, message = "제목은 200자 까지 작성할 수 있습니다.")
    private String title;

    @Schema(description = "게시물 내용")
    private String content;

    @Schema(description = "게시물 비공개 여부")
    private Boolean postOpenUseFlag;

    @Schema(description = "게시물 비밀번호")
    @Size(max = 20, message = "비밀번호는 20자까지 입력할 수 있습니다.")
    private String postPassword;

    @Schema(description = "댓글/답글 사용 여부")
    private Boolean replyCommentUseFlag;

    @Schema(description = "카테고리 아이디")
    private Long categoryId;

    @Schema(description = "업로드 파일 리스트")
    private List<MultipartFile> files;
}
