package io.dkargo.bulletinboard.service;

import io.dkargo.bulletinboard.dto.request.post.*;
import io.dkargo.bulletinboard.dto.response.post.ResPostDTO;
import io.dkargo.bulletinboard.dto.response.post.ResPostDetailDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface PostService {
    ResPostDetailDTO findDetailPostById(Long id, Long userId, String password);

    List<ResPostDTO> findPostByReqGetDTO(ReqGetDTO reqGetDTO);

    void addPost(ReqAddPostDTO reqAddPostDTO) throws IOException;

    void putPost(ReqPutPostDTO reqPutPostDTO) throws IOException;

    void patchPost(ReqPatchPostDTO reqPatchPostDTO) throws IOException;

    void deletePost(ReqDeletePostDTO reqDeletePostDTO);

}
