package io.dkargo.bulletinboard.service.Impl;

import io.dkargo.bulletinboard.entity.PostFile;
import io.dkargo.bulletinboard.entity.Post;
import io.dkargo.bulletinboard.repository.FileRepository;
import io.dkargo.bulletinboard.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final FileRepository fileRepository;

    @Override
    public void saveAllFile(Post post, List<MultipartFile> fileList) throws IOException {

        for (MultipartFile multipartFile : fileList) {
            UUID uuid = UUID.randomUUID();
            String fileName = uuid + "_" + multipartFile.getOriginalFilename();

            PostFile postFile = PostFile.builder()
                    .post(post)
                    .fileName(fileName)
                    .filePath("/Users/jhpark/Documents/files") //TODO : 컨피그 파일로 옮기기
                    .fileSize(multipartFile.getSize())
                    .contentType(multipartFile.getContentType())
                    .build();

            fileRepository.save(postFile);

            File file = new File(postFile.getFilePath(), postFile.getFileName());
            multipartFile.transferTo(file);
        }
    }
}
