package io.dkargo.bulletinboard.service;

import io.dkargo.bulletinboard.dto.request.post.*;
import io.dkargo.bulletinboard.dto.response.post.ResFindOptionPostDTO;
import io.dkargo.bulletinboard.dto.response.post.ResFindDetailPostDTO;

import java.io.IOException;
import java.util.List;

public interface PostService {
    ResFindDetailPostDTO findDetailPostById(Long id, Long userId, String password);

    List<ResFindOptionPostDTO> findPostByReqGetDTO(ReqFindOptionPostDTO reqFindOptionPostDTO);

    void addPost(ReqAddPostDTO reqAddPostDTO) throws IOException;

    void putPost(ReqPutPostDTO reqPutPostDTO) throws IOException;

    void patchPost(ReqPatchPostDTO reqPatchPostDTO) throws IOException;

    void deletePost(ReqDeletePostDTO reqDeletePostDTO);

}
