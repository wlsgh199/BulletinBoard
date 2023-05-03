package io.dkargo.bulletinboard.dto.response;


import io.dkargo.bulletinboard.entity.PostFile;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@ApiModel
@Getter
@Setter
public class ResPostFileDTO {
    @ApiModelProperty(value = "파일 이름")
    private String fileName;

    @ApiModelProperty(value = "파일 경로")
    private String filePath;

    @ApiModelProperty(value = "파일 사이즈")
    private Long fileSize;

    @ApiModelProperty(value = "파일 타입")
    private String contentType;

    @Builder
    public ResPostFileDTO(PostFile postFile) {
        this.fileName = postFile.getFileName();
        this.filePath = postFile.getFilePath();
        this.fileSize = postFile.getFileSize();
        this.contentType = postFile.getContentType();
    }
}
