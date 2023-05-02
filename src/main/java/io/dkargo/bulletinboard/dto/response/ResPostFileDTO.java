package io.dkargo.bulletinboard.dto.response;


import io.dkargo.bulletinboard.entity.PostFile;
import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@ApiModel
@Getter @Setter
public class ResPostFileDTO {
    private String fileName;
    private String filePath;
    private Long fileSize;
    private String contentType;

    @Builder
    public ResPostFileDTO(PostFile postFile) {
        this.fileName = postFile.getFileName();
        this.filePath = postFile.getFilePath();
        this.fileSize = postFile.getFileSize();
        this.contentType = postFile.getContentType();
    }
}
