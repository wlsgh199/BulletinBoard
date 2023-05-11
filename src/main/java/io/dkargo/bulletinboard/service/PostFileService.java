package io.dkargo.bulletinboard.service;

import io.dkargo.bulletinboard.entity.Post;
import io.dkargo.bulletinboard.entity.PostFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface PostFileService {
    //게시물 파일 저장
    void createAllPostFile(Post post, List<MultipartFile> fileList) throws IOException;

    void updateAllPostFile(Post post, List<MultipartFile> fileList) throws IOException;

    //해당 게시물 파일 전체 삭제
    void deleteAllPostFile(List<PostFile> postFileList);
}
