package io.dkargo.bulletinboard.service;

import io.dkargo.bulletinboard.dto.request.ReqPostDTO;
import io.dkargo.bulletinboard.dto.response.ResPostDTO;
import io.dkargo.bulletinboard.entity.Member;
import io.dkargo.bulletinboard.entity.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface PostService {
    List<ResPostDTO> findAllPost(Pageable pageable);
//    List<ResPostDTO> findPostById(Long id);
    List<ResPostDTO> findPostByMemberId(Long memberId, Pageable pageable);
    List<ResPostDTO> findPostByTitle(String title, Pageable pageable);
    List<ResPostDTO> findPostByContent(String title, Pageable pageable);
    List<ResPostDTO> findPostByCategory(Long categoryId, Pageable pageable);

    Post savePost(ReqPostDTO reqPostDTO, List<MultipartFile> fileList) throws IOException;

}
