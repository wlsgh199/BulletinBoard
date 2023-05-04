package io.dkargo.bulletinboard.dto.request.post;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;
import java.util.List;

@ApiModel
@Getter
@Setter
public class ReqAddPostDTO {
    @ApiModelProperty(value = "유저 아이디", required = true)
    @NotNull
    private Long userId;

    @ApiModelProperty(value = "게시물 제목", required = true)
    @Size(max = 200, message = "제목은 200자 까지 작성할 수 있습니다.")
    @NotBlank
    private String title;

    @ApiModelProperty(value = "게시물 내용", required = true)
    @NotBlank
    private String content;

    @ApiModelProperty(value = "게시물 공개 여부", example = "false", required = true)
    // TODO: boolean convert 으로 리팩토링 하기 https://memostack.tistory.com/194
    @NotNull
    private Boolean postOpenUseFlag;

    @ApiModelProperty(value = "게시물 비밀번호")
    @Size(max = 20, message = "비밀번호는 20자까지 입력할 수 있습니다.")
    private String postPassword;

    @ApiModelProperty(value = "댓글/답글 사용 여부", example = "true", required = true)
    @NotNull
    private Boolean replyCommentUseFlag;

    @ApiModelProperty(value = "카테고리 아이디", required = true)
    @NotNull
    private Long categoryId;

    @ApiModelProperty(value = "업로드 파일 리스트")
    private List<MultipartFile> files;
}
