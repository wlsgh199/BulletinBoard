package io.dkargo.bulletinboard.service;

import io.dkargo.bulletinboard.entity.Post;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FileService {
    void saveAllFile(Post post, List<MultipartFile> fileList) throws IOException;
}
