package io.dkargo.bulletinboard.dto.response;


import io.dkargo.bulletinboard.entity.PostFile;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Schema
@Getter
@Setter
public class ResPostFileDTO {
    @Schema(description = "파일 이름")
    private String fileName;

    @Schema(description = "파일 경로")
    private String filePath;

    @Schema(description = "파일 사이즈")
    private Long fileSize;

    @Schema(description = "파일 타입")
    private String contentType;

    @Builder
    public ResPostFileDTO(PostFile postFile) {
        this.fileName = postFile.getFileName();
        this.filePath = postFile.getFilePath();
        this.fileSize = postFile.getFileSize();
        this.contentType = postFile.getContentType();
    }
}
