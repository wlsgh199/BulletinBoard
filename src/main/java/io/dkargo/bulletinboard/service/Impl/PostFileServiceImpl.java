package io.dkargo.bulletinboard.service.Impl;

import io.dkargo.bulletinboard.entity.PostFile;
import io.dkargo.bulletinboard.entity.Post;
import io.dkargo.bulletinboard.repository.PostFileRepository;
import io.dkargo.bulletinboard.repository.support.PostFileRepositorySupport;
import io.dkargo.bulletinboard.service.PostFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class PostFileServiceImpl implements PostFileService {

    private final PostFileRepository postFileRepository;
    private final PostFileRepositorySupport postFileRepositorySupport;

    @Value("${file.save.path}")
    private String path;

    @Override
    public void saveAllPostFile(Post post, List<MultipartFile> fileList) throws IOException {
        for (MultipartFile multipartFile : fileList) {
            UUID uuid = UUID.randomUUID();
            String fileName = uuid + "_" + multipartFile.getOriginalFilename();

            PostFile postFile = PostFile.builder()
                    .post(post)
                    .fileName(fileName)
                    .filePath(path)
                    .fileSize(multipartFile.getSize())
                    .contentType(multipartFile.getContentType())
                    .build();

            postFileRepository.save(postFile);

            File file = new File(postFile.getFilePath(), postFile.getFileName());
            multipartFile.transferTo(file);
        }
    }

    public void deleteAllPostFileByPostId(long postId) {
        List<PostFile> postFileList = postFileRepositorySupport.findAllByPostId(postId);

        //로컬 저장소 파일 삭제
        for(PostFile postfile : postFileList) {

            File file = new File(postfile.getFilePath(), postfile.getFileName());
            file.delete();
        }

        postFileRepositorySupport.deleteAllByPostId(postId);
    }
}
