package io.dkargo.bulletinboard.dto.request.post;

import io.dkargo.bulletinboard.exception.CustomException;
import io.dkargo.bulletinboard.exception.ErrorCodeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;
import java.util.List;

@Schema
@Getter
@Setter
public class ReqCreatePostDTO {
    @Schema(description = "게시물 제목", requiredMode = Schema.RequiredMode.REQUIRED)
    @Size(max = 200, message = "제목은 200자 까지 작성할 수 있습니다.")
    @NotBlank
    private String title;

    @Schema(description = "게시물 내용", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank
    private String content;

    @Schema(description = "게시물 공개 여부", example = "true", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    private Boolean postOpenUseFlag;

    @Schema(description = "게시물 비밀번호")
    @Size(max = 20, message = "비밀번호는 20자까지 입력할 수 있습니다.")
    private String postPassword;

    @Schema(description = "댓글/답글 사용 여부", example = "true", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    private Boolean replyCommentUseFlag;

    @Schema(description = "카테고리 아이디", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    private Long categoryId;

    @Schema(description = "업로드 파일 리스트")
    private List<MultipartFile> files;

    //파일 사이즈 체크
    public void fileSizeCheck(int maxCount) {
        if (this.files.size() > maxCount) {
            throw new CustomException(ErrorCodeEnum.FILES_TOTO);
        }
    }
}
