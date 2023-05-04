package io.dkargo.bulletinboard.service;

import io.dkargo.bulletinboard.entity.Post;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface PostFileService {
    //게시물 파일 저장
    void saveAllPostFile(Post post, List<MultipartFile> fileList) throws IOException;
    //해당 게시물 파일 전체 삭제
    void deleteAllPostFileByPostId(Long postId);
}
