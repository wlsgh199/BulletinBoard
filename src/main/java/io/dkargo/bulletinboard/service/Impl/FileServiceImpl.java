package io.dkargo.bulletinboard.service.Impl;

import io.dkargo.bulletinboard.entity.PostFile;
import io.dkargo.bulletinboard.entity.Post;
import io.dkargo.bulletinboard.repository.FileRepository;
import io.dkargo.bulletinboard.service.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    FileRepository fileRepository;

    public FileServiceImpl(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    @Override
    public void saveAllFile(Post post, List<MultipartFile> fileList) throws IOException {
        for(MultipartFile multipartFile : fileList) {
            PostFile postFile = new PostFile();
            postFile.setPostId(post);

            UUID uuid = UUID.randomUUID();

            postFile.setFileName(uuid + "_" + multipartFile.getOriginalFilename());
            postFile.setFileSize(multipartFile.getSize());
            postFile.setCreateTime(LocalDateTime.now());
            postFile.setFullPath("/Users/jhpark/Documents/files");
            fileRepository.save(postFile);

            File file = new File(postFile.getFullPath(), postFile.getFileName());
            multipartFile.transferTo(file);

        }
    }
}
