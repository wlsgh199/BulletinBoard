package io.dkargo.bulletinboard.service;

import io.dkargo.bulletinboard.entity.Post;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface PostFileService {
    void saveAllPostFile(Post post, List<MultipartFile> fileList) throws IOException;

    void deleteAllPostFileByPostId(Long postId);
}
