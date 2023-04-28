package io.dkargo.bulletinboard.service.Impl;

import io.dkargo.bulletinboard.entity.BoardFile;
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
            BoardFile boardFile = new BoardFile();
            boardFile.setPostId(post);

            UUID uuid = UUID.randomUUID();

            boardFile.setFileName(uuid + "_" + multipartFile.getOriginalFilename());
            boardFile.setFileSize(multipartFile.getSize());
            boardFile.setCreateTime(LocalDateTime.now());
            boardFile.setFullPath("/Users/jhpark/Documents/files");
            fileRepository.save(boardFile);

            File file = new File(boardFile.getFullPath(), boardFile.getFileName());
            multipartFile.transferTo(file);

        }
    }
}
