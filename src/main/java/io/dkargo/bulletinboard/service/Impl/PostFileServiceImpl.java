package io.dkargo.bulletinboard.service.Impl;

import io.dkargo.bulletinboard.entity.Post;
import io.dkargo.bulletinboard.entity.PostFile;
import io.dkargo.bulletinboard.repository.PostFileRepository;
//import io.dkargo.bulletinboard.repository.support.PostFileRepositorySupport;
import io.dkargo.bulletinboard.service.PostFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.transaction.annotation.Transactional;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class PostFileServiceImpl implements PostFileService {

    private final PostFileRepository postFileRepository;
//    private final PostFileRepositorySupport postFileRepositorySupport;

    @Value("${file.save.path}")
    private String path;

    @Override
    public void createAllPostFile(Post post, List<MultipartFile> fileList) throws IOException {
        for (MultipartFile multipartFile : fileList) {
            makeFileAndSave(post, multipartFile);
        }
    }

    @Override
    public void updateAllPostFile(Post post, List<MultipartFile> fileList) throws IOException {
        List<PostFile> postFileList = post.getPostFileList();
        Set<PostFile> deletePostFile = new HashSet<>(postFileList);
        boolean equals;

        for (MultipartFile multipartFile : fileList) {
            equals = false;
            //기존에 중복 파일이 있는지 확인
            for (PostFile postFile : postFileList) {
                if (postFile.getOriginalFilename().equals(multipartFile.getOriginalFilename()) &&  //서로 파일이름이 같을때
                        postFile.getFileSize().equals(multipartFile.getSize()) &&   //서로 파일사이즈가 같을때
                        postFile.getContentType().equals(multipartFile.getContentType())) {    //서로 타입이 같을때
                    deletePostFile.remove(postFile);
                    equals = true;
                    break;
                }
            }
            //신규파일은 생성
            if (!equals) {
                makeFileAndSave(post, multipartFile);
            }
        }

        //중복파일이 아니고 신규파일도 아니면 테이블 에서 삭제 및 실제 로컬경로에서도 삭제
        deleteAllPostFile(new ArrayList<>(deletePostFile));
    }

    @Override
    public void deleteAllPostFile(List<PostFile> postFileList) {
        //파일 리스트만큼 삭제
        for (PostFile postFile : postFileList) {
            //실제 경로에서도 삭제
            File file = new File(postFile.getFilePath(), postFile.getFileName());
            if (file.exists()) {
                file.delete();
            }
        }
        postFileList.clear();
    }

    //파일 로컬에 생성 및 PostFile 테이블에 저장
    private void makeFileAndSave(Post post, MultipartFile multipartFile) throws IOException {
        UUID uuid = UUID.randomUUID();
        String fileName = uuid + "_" + multipartFile.getOriginalFilename();

        PostFile postFile = PostFile.builder()
                .post(post)
                .originalFilename(multipartFile.getOriginalFilename())
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
