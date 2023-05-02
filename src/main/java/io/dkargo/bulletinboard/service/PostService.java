package io.dkargo.bulletinboard.service;

import io.dkargo.bulletinboard.dto.request.post.ReqDeletePostDTO;
import io.dkargo.bulletinboard.dto.request.post.ReqPatchPostDTO;
import io.dkargo.bulletinboard.dto.request.post.ReqSavePostDTO;
import io.dkargo.bulletinboard.dto.request.post.ReqPutPostDTO;
import io.dkargo.bulletinboard.dto.response.post.ResPostDTO;
import io.dkargo.bulletinboard.dto.response.post.ResPostDetailDTO;
import io.dkargo.bulletinboard.entity.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface PostService {
    List<ResPostDTO> findAllPost(Pageable pageable);
    ResPostDetailDTO findDetailPostById(Long id, Long userId, String password);
    List<ResPostDTO> findPostByMemberId(Long userId, Pageable pageable);
    List<ResPostDTO> findPostByTitle(String title, Pageable pageable);
    List<ResPostDTO> findPostByContent(String title, Pageable pageable);
    List<ResPostDTO> findPostByCategory(Long categoryId, Pageable pageable);

    void savePost(ReqSavePostDTO reqSavePostDTO, List<MultipartFile> fileList) throws IOException;
    void putPost(ReqPutPostDTO reqPutPostDTO, List<MultipartFile> fileList) throws IOException;
    void patchPost(ReqPatchPostDTO reqPatchPostDTO, List<MultipartFile> fileList) throws IOException;
    void deletePost(ReqDeletePostDTO reqDeletePostDTO);

}
